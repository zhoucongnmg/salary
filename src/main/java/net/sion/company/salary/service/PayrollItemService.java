package net.sion.company.salary.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sion.company.salary.domain.Account;
import net.sion.company.salary.domain.AccountItem;
import net.sion.company.salary.domain.Item.ItemType;
import net.sion.company.salary.domain.Payroll;
import net.sion.company.salary.domain.PayrollItem;
import net.sion.company.salary.domain.PayrollSub;
import net.sion.company.salary.domain.PersonAccountFile;
import net.sion.company.salary.domain.PersonExtension;
import net.sion.company.salary.domain.SalaryItem.SalaryItemType;
import net.sion.company.salary.domain.SocialAccountItem;
import net.sion.company.salary.domain.SocialItem;
import net.sion.company.salary.domain.SocialItem.SocialItemType;
import net.sion.company.salary.domain.SystemSalaryItem;
import net.sion.company.salary.event.SystemSalaryItemPublisher;
import net.sion.company.salary.sessionrepository.AccountRepository;
import net.sion.company.salary.sessionrepository.PersonAccountFileRepository;
import net.sion.company.salary.sessionrepository.SocialItemRepository;
import net.sion.company.salary.sessionrepository.SystemSalaryItemRepository;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayrollItemService {
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	SocialItemRepository socialItemRepository;
	
	@Autowired 
	SocialService socialService;
	
	@Autowired 
	SystemSalaryItemRepository systemSalaryItemRepository;
	
	@Autowired
	PersonAccountFileRepository personAccountFileRepository;
	
	@Autowired
	FormulaService formulaService;
	
	@Autowired
	PersonAccountFileService personService;
	
	@Autowired
	TaxService taxService;
	
	@Autowired
	SystemSalaryItemPublisher publisher;
	
	
	public List<PayrollItem> generatePayrollItem(Payroll payroll,Set<String> personIds) {
		
		Account account = accountRepository.findOne(payroll.getAccountId()); 
			
		return this.generatePayrollItem(account.getAccountItems(), personIds, payroll.getId());
	}
	
	public List<PayrollItem> generatePayrollItem(Payroll payroll,PayrollSub payrollSub, Set<String> personIds) {
		Account account = accountRepository.findOne(payroll.getAccountId()); 
		Set<String> items = payrollSub.getItems();
		List<AccountItem> containItems = new ArrayList<AccountItem>();
		for (AccountItem item : account.getAccountItems()) {
			if (items.contains(item.getId())) {
				containItems.add(item);
			}
		}
		return this.generatePayrollItem(containItems, personIds, payrollSub.getId());
	}
	
	public List<PayrollItem> generatePayrollItem(List<AccountItem> items, Set<String> personIds, String payrollId) {
		List<PayrollItem> datas = new ArrayList<PayrollItem>();
		
		Iterable<PersonAccountFile> personList = personAccountFileRepository.findAll(personIds);
		try {
			for (PersonAccountFile person : personList) {
				
				Map<String, Object> personMap = new HashMap<String, Object>();
				Map<String, String> simplePersonMap = new HashMap<String, String>();
				Map<String, Double> dataPersonMap = new HashMap<String, Double>();
				simplePersonMap.put("personId", person.getId());
				simplePersonMap.put("payrollId", payrollId);
				simplePersonMap.put("name", person.getName());
				simplePersonMap.put("duty", person.getDuty());
				simplePersonMap.put("dept", person.getDept());
				simplePersonMap.put("personCode", person.getPersonCode());
				simplePersonMap.put("idCard", person.getIdCard());
				simplePersonMap.put("bank", person.getBank());
				simplePersonMap.put("bankOfDeposit", person.getBankOfDeposit());
				simplePersonMap.put("bankAccount", person.getBankAccount());
				
				Map<String,AccountItem> personAccountItemMap = new LinkedHashMap<String,AccountItem>();
				Set<String> formulaIds = new HashSet<String>();
				for (AccountItem item : items) {
					personAccountItemMap.put(item.getId(), item);
				}
				
				for (AccountItem item : items) {
					if (item.getType() == SalaryItemType.Input) {
						//TODO 通过personId查找该人在薪资档案中该项设置的值
						Double value = personService.getOneItemValue(person.getId(), item.getSalaryItemId());
						if (value!=null) {
							dataPersonMap.put(item.getSalaryItemId(), value);
						}else {
							dataPersonMap.put(item.getSalaryItemId(), Double.valueOf(item.getValue()));
						}
					}else if (item.getType() == SalaryItemType.System) {
						SystemSalaryItem systemItem = systemSalaryItemRepository.findOne(item.getSalaryItemId());
						Double value = publisher.getValue(systemItem,person.getId(),person.getDept());
						dataPersonMap.put(item.getSalaryItemId(), value);
					}else if (item.getType() == SalaryItemType.Calculate) {
						formulaIds.add(item.getFormulaId());
						Map<String,Double> result = formulaService.caculateFormulas(formulaIds, dataPersonMap);
						//TODO 将返回的数值putAll dataPersonMap
						dataPersonMap.putAll(result);
					}else if (item.getType() == SalaryItemType.Tax) {
						String parentId = item.getParentId();
						if(StringUtils.isNotBlank(parentId)&&personAccountItemMap.get(parentId)!=null) {
							AccountItem parent = personAccountItemMap.get(parentId);
							String parentSalaryItemId = parent.getSalaryItemId();
							Double value = dataPersonMap.get(parentSalaryItemId);
							Double taxValue = taxService.getFastNumber(item.getTaxId(),value);
							dataPersonMap.put(item.getSalaryItemId(), item.decimal(item.getCarryType(), item.getPrecision(), taxValue));
						}
					}
					
				}
			
				personMap.putAll(simplePersonMap);
				personMap.putAll(dataPersonMap);
				
				//TODO convert to PayrollItem
				PayrollItem item = new PayrollItem();
				item.convertDomain(personMap);
				datas.add(item);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return datas;
		
	}
	
	public List<Map<String, Object>> fillBaseFields(List<Map<String, Object>> fields) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "id");
		map.put("type", "string");
		fields.add(map);
		map = new HashMap<String, Object>();
		map.put("name", "payrollId");
		map.put("type", "string");
		fields.add(map);
		map = new HashMap<String, Object>();
		map.put("name", "personId");
		map.put("type", "string");
		fields.add(map);
		
		return fields;
	}

	public List<Map<String, Object>> fillSimpleFields(List<Map<String, Object>> fields, Map<String,String> opts) {
		Map<String, Object> map = new HashMap<String, Object>();
		fillBaseFields(fields);
		
		map = new HashMap<String, Object>();
		map.put("name", "name");
		map.put("type", "string");
		fields.add(map);
		
		map = new HashMap<String, Object>();
		map.put("name", "personCode");
		map.put("type", "string");
		fields.add(map);
		
		if (opts!=null) {
			if ("on".equals(opts.get("showCompanySocial"))||"on".equals(opts.get("showPersonalSocial"))) {
				List<SocialItem> socialItems = socialItemRepository.findByItem(ItemType.SocialItem);
				for (SocialItem item : socialItems) {
					map = new HashMap<String, Object>();
					map.put("name", item.getId() + "-cardinality");
					map.put("type", "double");
					fields.add(map);
					
					if ("on".equals(opts.get("showCompanySocial"))) {
						map = new HashMap<String, Object>();
						map.put("name", item.getId() + "-companyPaymentValue");
						map.put("type", "double");
						fields.add(map);
						map = new HashMap<String, Object>();
						map.put("name", item.getId() + "-companyPaymentFinalValue");
						map.put("type", "double");
						fields.add(map);
					}
					
					if ("on".equals(opts.get("showPersonalSocial"))) {
						map = new HashMap<String, Object>();
						map.put("name", item.getId() + "-personalPaymentValue");
						map.put("type", "double");
						fields.add(map);
						map = new HashMap<String, Object>();
						map.put("name", item.getId() + "-personalPaymentFinalValue");
						map.put("type", "double");
						fields.add(map);
					}
				}
			}
			
			if ("on".equals(opts.get("showDept"))) {
				map = new HashMap<String, Object>();
				map.put("name", "duty");
				map.put("type", "string");
				fields.add(map);
				map = new HashMap<String, Object>();
				map.put("name", "dept");
				map.put("type", "string");
				fields.add(map);
			}
			
			if ("on".equals(opts.get("showIdCard"))) {
				map = new HashMap<String, Object>();
				map.put("name", "idCard");
				map.put("type", "string");
				fields.add(map);		
			}
			
			if ("on".equals(opts.get("showBank"))) {
				map = new HashMap<String, Object>();
				map.put("name", "bank");
				map.put("type", "string");
				fields.add(map);
				map = new HashMap<String, Object>();
				map.put("name", "bankOfDeposit");
				map.put("type", "string");
				fields.add(map);
				map = new HashMap<String, Object>();
				map.put("name", "bankAccount");
				map.put("type", "string");
				fields.add(map);
			}
		}
		
		
		return fields;
	}

	public List<Map<String, Object>> fillSimpleColumns(List<Map<String, Object>> columns,Map<String,String> opts) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("header", "员工编号");
		map.put("dataIndex", "personCode");
		map.put("coltype", "readonly");
		map.put("dataType", "simple");
		columns.add(map);
		map = new HashMap<String, Object>();
		map.put("header", "姓名");
		map.put("dataIndex", "name");
		map.put("coltype", "readonly");
		map.put("dataType", "simple");
		columns.add(map);
		
		if (opts!=null) {
			if ("on".equals(opts.get("showCompanySocial"))||"on".equals(opts.get("showPersonalSocial"))) {
				List<SocialItem> socialItems = socialItemRepository.findByItem(ItemType.SocialItem);
				for (SocialItem item : socialItems) {
					map = new HashMap<String, Object>();
					map.put("header", item.getName());
					map.put("dataIndex", "string");
					
					List<Map<String,Object>> socialColumns = new ArrayList<Map<String,Object>>();
					
					Map<String,Object> socialColumn = new HashMap<String, Object>();
					socialColumn.put("header", "基数");
					socialColumn.put("dataIndex", item.getId() +"-cardinality");
					socialColumns.add(socialColumn);
					if ("on".equals(opts.get("showCompanySocial"))) {
						socialColumn = new HashMap<String, Object>();
						socialColumn.put("header", "单位");
						socialColumn.put("dataIndex", "string");
						
						List<Map<String,Object>> companySocialColumns = new ArrayList<Map<String,Object>>();
						
						Map<String,Object> companySocialColumn = new HashMap<String, Object>();
						companySocialColumn.put("header", "比例");
						companySocialColumn.put("dataIndex", item.getId() +"-companyPaymentValue");
						companySocialColumn.put("flex", 1);
						companySocialColumn.put("coltype", "readonly");
						companySocialColumn.put("dataType", "simple");
						companySocialColumns.add(companySocialColumn);
						companySocialColumn = new HashMap<String, Object>();
						companySocialColumn.put("header", "金额");
						companySocialColumn.put("dataIndex", item.getId() +"-companyPaymentFinalValue");
						companySocialColumn.put("flex", 1);
						companySocialColumn.put("coltype", "readonly");
						companySocialColumn.put("dataType", "simple");
						companySocialColumns.add(companySocialColumn);
						
						socialColumn.put("columns", companySocialColumns);
						socialColumns.add(socialColumn);
					}
					
					if ("on".equals(opts.get("showPersonalSocial"))) {
						socialColumn = new HashMap<String, Object>();
						socialColumn.put("header", "个人");
						socialColumn.put("dataIndex", "string");
						
						List<Map<String,Object>> personalSocialColumns = new ArrayList<Map<String,Object>>();
						
						Map<String,Object> personalSocialColumn = new HashMap<String, Object>();
						personalSocialColumn.put("header", "比例");
						personalSocialColumn.put("dataIndex", item.getId() +"-personalPaymentValue");
						personalSocialColumn.put("flex", 1);
						personalSocialColumn.put("coltype", "readonly");
						personalSocialColumn.put("dataType", "simple");
						personalSocialColumns.add(personalSocialColumn);
						personalSocialColumn = new HashMap<String, Object>();
						personalSocialColumn.put("header", "金额");
						personalSocialColumn.put("dataIndex", item.getId() +"-personalPaymentFinalValue");
						personalSocialColumn.put("flex", 1);
						personalSocialColumn.put("coltype", "readonly");
						personalSocialColumn.put("dataType", "simple");
						personalSocialColumns.add(personalSocialColumn);
						
						socialColumn.put("columns", personalSocialColumns);
						socialColumns.add(socialColumn);
					}
					
					map.put("columns", socialColumns);
					
					columns.add(map);
				}
			}
			
			if ("on".equals(opts.get("showDept"))) {
				map = new HashMap<String, Object>();
				map.put("header", "职务");
				map.put("dataIndex", "duty");
				map.put("coltype", "readonly");
				map.put("dataType", "simple");
				columns.add(map);
				map = new HashMap<String, Object>();
				map.put("header", "部门");
				map.put("dataIndex", "dept");
				map.put("coltype", "readonly");
				map.put("dataType", "simple");
				columns.add(map);
			}
			
			if ("on".equals(opts.get("showIdCard"))) {
				map = new HashMap<String, Object>();
				map.put("header", "身份证号");
				map.put("dataIndex", "idCard");
				map.put("coltype", "readonly");
				map.put("dataType", "simple");
				columns.add(map);
			}
			
			if ("on".equals(opts.get("showBank"))) {
				
				map = new HashMap<String, Object>();
				map.put("header", "银行");
				map.put("dataIndex", "bank");
				map.put("coltype", "readonly");
				map.put("dataType", "simple");
				columns.add(map);
				
				map = new HashMap<String, Object>();
				map.put("header", "开户行");
				map.put("dataIndex", "bankOfDeposit");
				map.put("coltype", "readonly");
				map.put("dataType", "simple");
				columns.add(map);
				
				map = new HashMap<String, Object>();
				map.put("header", "银行账号");
				map.put("dataIndex", "bankAccount");
				map.put("coltype", "readonly");
				map.put("dataType", "simple");
				columns.add(map);
			}
		}
		
		return columns;
	}

	public List<Map<String, Object>> fillData(Payroll payroll, List<PayrollItem> items, Account account) {
		
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		
		Set<String> personIds = payroll.getPersons().keySet();
		if (personIds.size()>0) {
			Map<String, PersonExtension<SocialAccountItem>> personSocialMap = socialService.getSocialAccountByPersons(personIds);
			
			for (PayrollItem item : items) {
				Map<String,Object> itemMap = item.parseMap();
				PersonExtension<SocialAccountItem> personExtension = personSocialMap.get(item.getPersonId());
				if (personExtension!=null) {
					Map<String,SocialAccountItem> socialAccountItemMap = personExtension.getItems();
					for (Map.Entry<String, SocialAccountItem> entry : socialAccountItemMap.entrySet()) {
						SocialAccountItem socialItem = entry.getValue();
						itemMap.put(socialItem.getSocialItemId()+"-cardinality", socialItem.getCardinality());
						itemMap.put(socialItem.getSocialItemId()+"-companyPaymentValue", socialItem.getCompanyPaymentValue());
						itemMap.put(socialItem.getSocialItemId()+"-companyPaymentFinalValue", socialItem.getCompanyPaymentFinalValue());
						itemMap.put(socialItem.getSocialItemId()+"-personalPaymentValue", socialItem.getPersonalPaymentValue());
						itemMap.put(socialItem.getSocialItemId()+"-personalPaymentFinalValue", socialItem.getPersonalPaymentFinalValue());
					}
				}
				
				data.add(itemMap);
			}
		}
		

		return data;
	}

	public Map<String, Object> getFields(AccountItem item) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", item.getSalaryItemId());
		map.put("type", "float");
		return map;
	}

	public Map<String, Object> getColumns(AccountItem item) {
		Map<String, Object> map = new HashMap<String, Object>();
		switch (item.getType()) {
		case Input:
			Map<String, String> editor = new HashMap<String, String>();
			editor.put("xtype", "textfield");
			editor.put("name", item.getSalaryItemId());
			editor.put("allowBlank", "false");
			map.put("editor", editor);
			map.put("coltype", "input");
			break;
		case Calculate:
			map.put("coltype", "readonly");
		case System:
			map.put("coltype", "readonly");
		}

		map.put("header", item.getName());
		map.put("dataIndex", item.getSalaryItemId());
		return map;
	}
}
