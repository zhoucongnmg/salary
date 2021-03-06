package net.sion.company.salary.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sion.company.salary.domain.Account;
import net.sion.company.salary.domain.AccountItem;
import net.sion.company.salary.domain.Formula;
import net.sion.company.salary.domain.FormulaItem;
import net.sion.company.salary.domain.Item.ItemType;
import net.sion.company.salary.domain.Payroll;
import net.sion.company.salary.domain.PayrollItem;
import net.sion.company.salary.domain.PayrollSub;
import net.sion.company.salary.domain.PersonAccountFile;
import net.sion.company.salary.domain.PersonExtension;
import net.sion.company.salary.domain.SalaryItem.SalaryItemType;
import net.sion.company.salary.domain.SocialAccountItem;
import net.sion.company.salary.domain.SocialAccountItem.PaymentType;
import net.sion.company.salary.domain.SocialItem;
import net.sion.company.salary.domain.SystemSalaryItem;
import net.sion.company.salary.event.SystemSalaryItemPublisher;
import net.sion.company.salary.sessionrepository.AccountRepository;
import net.sion.company.salary.sessionrepository.PayrollItemRepository;
import net.sion.company.salary.sessionrepository.PersonAccountFileRepository;
import net.sion.company.salary.sessionrepository.SocialItemRepository;
import net.sion.company.salary.sessionrepository.SystemSalaryItemRepository;

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
	
	@Autowired
	PayrollItemRepository payrollItemRepository;
	
	
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
				simplePersonMap.put("level", person.getLevel());
				
				Map<String,AccountItem> personAccountItemMap = new LinkedHashMap<String,AccountItem>();
				Map<String,AccountItem> personSalaryItemMap = new LinkedHashMap<String,AccountItem>();
				Set<String> formulaIds = new HashSet<String>();
				for (AccountItem item : items) {
					personAccountItemMap.put(item.getId(), item);
					personSalaryItemMap.put(item.getSalaryItemId(), item);
				}
				
				
				Iterable<AccountItem> sortItems = sortAccountItem(items);
				for (AccountItem item : sortItems) {
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
						AccountItem parent = personAccountItemMap.get(parentId);
						String parentSalaryItemId = parent.getSalaryItemId();
						Double value = dataPersonMap.get(parentSalaryItemId);
						Double taxValue = taxService.getFastNumber(item.getTaxId(),value);
						dataPersonMap.put(item.getSalaryItemId(), item.decimal(item.getCarryType(), item.getPrecision(), taxValue));
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
	
	
	private String parsePrecision(double value, int precision) {
		String formatStr = "0";
		
		for (int i=0;i<precision;i++) {
			if (i==0) formatStr = formatStr+".";
			formatStr = formatStr+"0";
		}
		DecimalFormat df = new DecimalFormat(formatStr);
		return df.format(value);
	}
	
	public List<AccountItem> sortAccountItem(List<AccountItem> items) {
		Map<String,AccountItem> personAccountItemMap = new LinkedHashMap<String,AccountItem>();
		Set<String> knownItemIds = new HashSet<String>();
		Set<String> unknownItemIds = new HashSet<String>();
		List<AccountItem> taxCalculateItem = new ArrayList<AccountItem>();
		Map<String,AccountItem> sortItemsMap = new LinkedHashMap<String,AccountItem>();
		List<AccountItem> sortItems = new ArrayList<AccountItem>();
		Set<String> formulaIds = new HashSet<String>();
				
		for (AccountItem item : items) {
			personAccountItemMap.put(item.getId(), item);
		}
		
		for (AccountItem item : items) {
			if (item.getType() == SalaryItemType.Input||item.getType() == SalaryItemType.System) {
				knownItemIds.add(item.getSalaryItemId());
				personAccountItemMap.put(item.getId(), item);
				sortItemsMap.put(item.getId(),item);
			}else if (item.getType() == SalaryItemType.Tax) {
				String parentId = item.getParentId();
				AccountItem parentItem = personAccountItemMap.get(parentId);
				if (parentItem.getType() == SalaryItemType.Tax || parentItem.getType() == SalaryItemType.Calculate) {
					unknownItemIds.add(parentItem.getSalaryItemId());
					unknownItemIds.add(item.getSalaryItemId());
					taxCalculateItem.add(item);
				}else {
					knownItemIds.add(item.getSalaryItemId());
					knownItemIds.add(parentItem.getSalaryItemId());
					sortItemsMap.put(item.getId(),item);
				}
			}else if (item.getType() == SalaryItemType.Calculate) {
				formulaIds.add(item.getFormulaId());
				taxCalculateItem.add(item);
			}
		}
		
		unknownItemIds.addAll(formulaService.getFormulaItemsAndResult(formulaIds));
		Map<String,Formula> formulaMap = formulaService.getFormulasMap(formulaIds);
		while (unknownItemIds.size()>0) {
			for (AccountItem item: taxCalculateItem) {
				if (sortItemsMap.get(item.getId())==null) {
					if (item.getType() == SalaryItemType.Tax) {
						String parentId = item.getParentId();
						AccountItem parentItem = personAccountItemMap.get(parentId);
						if (knownItemIds.contains(parentItem.getSalaryItemId())) {
							knownItemIds.add(item.getSalaryItemId());
							unknownItemIds.remove(item.getSalaryItemId());
							unknownItemIds.remove(parentItem.getSalaryItemId());
							sortItemsMap.put(parentItem.getId(),parentItem);
							sortItemsMap.put(item.getId(),item);
						}
					}else if (item.getType() == SalaryItemType.Calculate) {
						Formula formula = formulaMap.get(item.getFormulaId());
						Boolean allContains = true;
						Set<String> formulaItemIds = new HashSet<String>();
						for (FormulaItem formulaItem : formula.getItems()) {
							if (!knownItemIds.contains(formulaItem.getFieldId())) {
								allContains = false;
								break;
							}else {
								formulaItemIds.add(formulaItem.getFieldId());
							}
						}
						if (allContains) {
							knownItemIds.addAll(formulaItemIds);
							knownItemIds.add(formula.getResultFieldId());
							unknownItemIds.removeAll(formulaItemIds);
							unknownItemIds.remove(formula.getResultFieldId());
							sortItemsMap.put(item.getId(),item);
						}
					}
				}
			}
		}
		
		
		
		for (Map.Entry<String, AccountItem> entry : sortItemsMap.entrySet()) {  
			sortItems.add(entry.getValue());  
		}  
		
		List<AccountItem> different = new ArrayList<AccountItem>();
		for (AccountItem item : items) {
			if (sortItemsMap.get(item.getId())==null) {
				different.add(item);
			}
		}
		
		return sortItems;
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
					/*
					map = new HashMap<String, Object>();
					map.put("name", item.getId() + "-cardinality");
					map.put("type", "double");
					fields.add(map);
					*/
					if ("on".equals(opts.get("showCompanySocial"))) {
						map = new HashMap<String, Object>();
						map.put("name", item.getId() + "-companyCardinality");
						map.put("type", "float");
						fields.add(map);
						map = new HashMap<String, Object>();
						map.put("name", item.getId() + "-companyPaymentValue");
						map.put("type", "String");
						fields.add(map);
						map = new HashMap<String, Object>();
						map.put("name", item.getId() + "-companyPaymentFinalValue");
						map.put("type", "float");
						fields.add(map);
					}
					
					if ("on".equals(opts.get("showPersonalSocial"))) {
						map = new HashMap<String, Object>();
						map.put("name", item.getId() + "-personalCardinality");
						map.put("type", "float");
						fields.add(map);
						map = new HashMap<String, Object>();
						map.put("name", item.getId() + "-personalPaymentValue");
						map.put("type", "String");
						fields.add(map);
						map = new HashMap<String, Object>();
						map.put("name", item.getId() + "-personalPaymentFinalValue");
						map.put("type", "float");
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
		map.put("text", "员工编号");
		map.put("dataIndex", "personCode");
		map.put("coltype", "readonly");
		columns.add(map);
		map = new HashMap<String, Object>();
		map.put("text", "姓名");
		map.put("dataIndex", "name");
		map.put("coltype", "readonly");
		columns.add(map);
		
		if (opts!=null) {
			if ("on".equals(opts.get("showCompanySocial"))||"on".equals(opts.get("showPersonalSocial"))) {
				List<SocialItem> socialItems = socialItemRepository.findByItem(ItemType.SocialItem);
				for (SocialItem item : socialItems) {
					map = new HashMap<String, Object>();
					map.put("text", item.getName());
					map.put("dataIndex", "string");
					
					List<Map<String,Object>> socialColumns = new ArrayList<Map<String,Object>>();
					
					Map<String,Object> socialColumn = new HashMap<String, Object>();
//					socialColumn.put("text", "基数");
//					socialColumn.put("dataIndex", item.getId() +"-cardinality");
//					socialColumns.add(socialColumn);
					if ("on".equals(opts.get("showCompanySocial"))) {
						socialColumn = new HashMap<String, Object>();
						socialColumn.put("text", "单位");
						socialColumn.put("dataIndex", "string");
						
						List<Map<String,Object>> companySocialColumns = new ArrayList<Map<String,Object>>();
						
						Map<String,Object> companySocialColumn = new HashMap<String, Object>();
						companySocialColumn.put("text", "基数");
						companySocialColumn.put("dataIndex", item.getId() +"-companyCardinality");
						companySocialColumn.put("flex", 1);
						companySocialColumn.put("coltype", "readonly");
						companySocialColumns.add(companySocialColumn);
						companySocialColumn = new HashMap<String, Object>();
						companySocialColumn.put("text", "比例");
						companySocialColumn.put("dataIndex", item.getId() +"-companyPaymentValue");
						companySocialColumn.put("flex", 1);
						companySocialColumn.put("coltype", "readonly");
						companySocialColumns.add(companySocialColumn);
						companySocialColumn = new HashMap<String, Object>();
						companySocialColumn.put("text", "金额");
						companySocialColumn.put("dataIndex", item.getId() +"-companyPaymentFinalValue");
						companySocialColumn.put("flex", 1);
						companySocialColumn.put("coltype", "readonly");
						companySocialColumns.add(companySocialColumn);
						
						socialColumn.put("columns", companySocialColumns);
						socialColumns.add(socialColumn);
					}
					
					if ("on".equals(opts.get("showPersonalSocial"))) {
						socialColumn = new HashMap<String, Object>();
						socialColumn.put("text", "个人");
						socialColumn.put("dataIndex", "string");
						
						List<Map<String,Object>> personalSocialColumns = new ArrayList<Map<String,Object>>();
						
						Map<String,Object> personalSocialColumn = new HashMap<String, Object>();
						personalSocialColumn.put("text", "基数");
						personalSocialColumn.put("dataIndex", item.getId() +"-personalCardinality");
						personalSocialColumn.put("flex", 1);
						personalSocialColumn.put("coltype", "readonly");
						personalSocialColumns.add(personalSocialColumn);
						personalSocialColumn = new HashMap<String, Object>();
						personalSocialColumn.put("text", "比例");
						personalSocialColumn.put("dataIndex", item.getId() +"-personalPaymentValue");
						personalSocialColumn.put("flex", 1);
						personalSocialColumn.put("coltype", "readonly");
						personalSocialColumns.add(personalSocialColumn);
						personalSocialColumn = new HashMap<String, Object>();
						personalSocialColumn.put("text", "金额");
						personalSocialColumn.put("dataIndex", item.getId() +"-personalPaymentFinalValue");
						personalSocialColumn.put("flex", 1);
						personalSocialColumn.put("coltype", "readonly");
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
				map.put("text", "职务");
				map.put("dataIndex", "duty");
				map.put("coltype", "readonly");
				columns.add(map);
				map = new HashMap<String, Object>();
				map.put("name", "部门");
				map.put("dataIndex", "dept");
				map.put("coltype", "readonly");
				columns.add(map);
			}
			
			if ("on".equals(opts.get("showIdCard"))) {
				map = new HashMap<String, Object>();
				map.put("text", "身份证号");
				map.put("dataIndex", "idCard");
				map.put("coltype", "readonly");
				columns.add(map);
			}
			
			if ("on".equals(opts.get("showBank"))) {
				
				map = new HashMap<String, Object>();
				map.put("text", "银行");
				map.put("dataIndex", "bank");
				map.put("coltype", "readonly");
				columns.add(map);
				
				map = new HashMap<String, Object>();
				map.put("text", "开户行");
				map.put("dataIndex", "bankOfDeposit");
				map.put("coltype", "readonly");
				columns.add(map);
				
				map = new HashMap<String, Object>();
				map.put("text", "银行账号");
				map.put("dataIndex", "bankAccount");
				map.put("coltype", "readonly");
				columns.add(map);
			}
		}
		
		return columns;
	}

	public List<Map<String, Object>> fillData(Payroll payroll, List<PayrollItem> items, Account account) {
		
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		Map<String,AccountItem> accountItemsMap = new HashMap<String,AccountItem>();
		List<AccountItem> accountItems = account.getAccountItems();
		for (AccountItem item : accountItems) {
			accountItemsMap.put(item.getSalaryItemId(), item);
		}
		
		
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
						if(socialItem.getCompanyPaymentType().equals(PaymentType.Percent)){
							itemMap.put(socialItem.getSocialItemId()+"-companyPaymentValue", socialItem.getCompanyPaymentValue() * 100 +"%");
						}else{
							itemMap.put(socialItem.getSocialItemId()+"-companyPaymentValue", socialItem.getCompanyPaymentValue());
						}
						itemMap.put(socialItem.getSocialItemId()+"-companyCardinality", socialItem.getCompanyCardinality());
						itemMap.put(socialItem.getSocialItemId()+"-companyPaymentFinalValue", socialItem.getCompanyPaymentFinalValue());
						
						if(socialItem.getPersonalPaymentType().equals(PaymentType.Percent)){
							itemMap.put(socialItem.getSocialItemId()+"-personalPaymentValue", socialItem.getPersonalPaymentValue() * 100 +"%");
						}else{
							itemMap.put(socialItem.getSocialItemId()+"-personalPaymentValue", socialItem.getPersonalPaymentValue());
						}
						itemMap.put(socialItem.getSocialItemId()+"-personalCardinality", socialItem.getPersonalCardinality());
						itemMap.put(socialItem.getSocialItemId()+"-personalPaymentFinalValue", socialItem.getPersonalPaymentFinalValue());
					}
				}
				
				for (Map.Entry<String, Double> entry : item.getValues().entrySet()) {  
					String itemId = entry.getKey();
					AccountItem accountitem = accountItemsMap.get(itemId);
					if (accountitem!=null) {
						itemMap.put(itemId, parsePrecision(entry.getValue(),accountitem.getPrecision()));
					}
				}
				
				
				data.add(itemMap);
			}
		}
		

		return data;
	}
	
	public List<Map<String, Object>> fillData(Payroll payroll, List<PayrollItem> items, Account account, List<PayrollSub> subs) {
		
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		
		Set<String> personIds = payroll.getPersons().keySet();
		if (personIds.size()>0) {
			Map<String, PersonExtension<SocialAccountItem>> personSocialMap = socialService.getSocialAccountByPersons(personIds);
			
			Map<String,List<PayrollItem>> personSubItemsMap = new HashMap<String,List<PayrollItem>>();
			if (subs!=null) {
				for (PayrollSub sub : subs) {
					List<PayrollItem> subItems = payrollItemRepository.findByPayrollId(sub.getId());
					for (PayrollItem item :  subItems) {
						List<PayrollItem> personSubItems = personSubItemsMap.get(item.getPersonId());
						if (personSubItems==null) {
							personSubItems = new ArrayList<PayrollItem>(); 
						}
						personSubItems.add(item);
						personSubItemsMap.put(item.getPersonId(),personSubItems);
					}
				}
			}	
			
			
			for (PayrollItem item : items) {
				Map<String,Object> itemMap = item.parseMap();
				PersonExtension<SocialAccountItem> personExtension = personSocialMap.get(item.getPersonId());
				if (personExtension!=null) {
					Map<String,SocialAccountItem> socialAccountItemMap = personExtension.getItems();
					for (Map.Entry<String, SocialAccountItem> entry : socialAccountItemMap.entrySet()) {
						SocialAccountItem socialItem = entry.getValue();
						itemMap.put(socialItem.getSocialItemId()+"-companyCardinality", socialItem.getCompanyCardinality());
						itemMap.put(socialItem.getSocialItemId()+"-companyPaymentValue", socialItem.getCompanyPaymentValue());
						itemMap.put(socialItem.getSocialItemId()+"-companyPaymentFinalValue", socialItem.getCompanyPaymentFinalValue());
						itemMap.put(socialItem.getSocialItemId()+"-personalCardinality", socialItem.getPersonalCardinality());
						itemMap.put(socialItem.getSocialItemId()+"-personalPaymentValue", socialItem.getPersonalPaymentValue());
						itemMap.put(socialItem.getSocialItemId()+"-personalPaymentFinalValue", socialItem.getPersonalPaymentFinalValue());
					}
				}
				
				List<PayrollItem> personSubItems = personSubItemsMap.get(item.getPersonId());
				if (personSubItems!=null) {
					for (PayrollItem subItem : personSubItems) {
						Map<String,Double> values = subItem.getValues();
						for (Map.Entry<String, Double> entry : values.entrySet()) {
							String key = entry.getKey();
							Double value = entry.getValue();
							itemMap.put(subItem.getPayrollId() + "-" + key, value);
						}
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
		return map;
	}
	
	public Map<String, Object> getColumns(AccountItem item) {
		Map<String, Object> map = new HashMap<String, Object>();
		switch (item.getType()) {
		case Input:
			Map<String, String> editor = new HashMap<String, String>();
			editor.put("xtype", "numberfield");
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

		map.put("text", item.getName());
		map.put("dataIndex", item.getSalaryItemId());
		return map;
	}
	
	public Map<String, Object> getSubColumns(String payrollSubId, AccountItem item) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("text", item.getName());
		map.put("dataIndex", payrollSubId + "-" + item.getSalaryItemId());
		return map;
	}
	
}
