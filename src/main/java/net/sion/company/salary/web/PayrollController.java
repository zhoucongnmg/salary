package net.sion.company.salary.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import net.sion.boot.config.jackson.CustomJackson;
import net.sion.boot.mongo.template.SessionMongoTemplate;
import net.sion.company.salary.domain.Account;
import net.sion.company.salary.domain.AccountItem;
import net.sion.company.salary.domain.AccountItem.AccountItemType;
import net.sion.company.salary.domain.Payroll;
import net.sion.company.salary.domain.Payroll.PayrollStatus;
import net.sion.company.salary.domain.PayrollItem;
import net.sion.company.salary.domain.PersonAccountFile;
import net.sion.company.salary.domain.PersonExtension;
import net.sion.company.salary.domain.SocialItem;
import net.sion.company.salary.domain.SocialItem.SocialItemType;
import net.sion.company.salary.service.FormulaService;
import net.sion.company.salary.service.PersonLevelService;
import net.sion.company.salary.sessionrepository.AccountRepository;
import net.sion.company.salary.sessionrepository.PayrollItemRepository;
import net.sion.company.salary.sessionrepository.PayrollRepository;
import net.sion.company.salary.sessionrepository.PersonAccountFileRepository;
import net.sion.company.salary.sessionrepository.PersonAccountRepository;
import net.sion.company.salary.sessionrepository.SocialItemRepository;
import net.sion.core.admin.domain.User;
import net.sion.core.admin.service.AdminService;
import net.sion.util.mvc.Response;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 工资表
 * 
 * @author niex
 */
@RestController
@RequestMapping("/salary/payroll/")
public class PayrollController {
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	PersonAccountFileRepository personAccountFileRepository;
	@Autowired
	PersonAccountRepository personAccountRepository;
	@Autowired
	CustomJackson jackson;
	@Autowired
	PayrollRepository payrollRepository;
	@Autowired
	PayrollItemRepository payrollItemRepository;
	@Autowired
	SessionMongoTemplate mongoTemplate;
	@Autowired
	PersonAccountFileRepository personAcountFileRepsitory;
	@Autowired
	AdminService adminService;
	@Autowired
	FormulaService formulaService;
	@Autowired
	SocialItemRepository socialItemRepository;
	@Autowired
	PersonLevelService personLevelService;
	
	
	public List<Map<String, Object>> fillSimpleFields(List<Map<String, Object>> fields, Map<String,String> opts) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "id");
		map.put("type", "string");
		fields.add(map);
		map = new HashMap<String, Object>();
		map.put("name", "personId");
		map.put("type", "string");
		fields.add(map);
		map = new HashMap<String, Object>();
		map.put("name", "name");
		map.put("type", "string");
		fields.add(map);
		map = new HashMap<String, Object>();
		map.put("name", "duty");
		map.put("type", "string");
		fields.add(map);
		map = new HashMap<String, Object>();
		map.put("name", "dept");
		map.put("type", "string");
		fields.add(map);
		if (opts!=null) {
			if ("on".equals(opts.get("showCompanySocial"))) {
				map = new HashMap<String, Object>();
				map.put("name", "companyPaymentValue");
				map.put("type", "string");
				fields.add(map);
				map = new HashMap<String, Object>();
				map.put("name", "companyPaymentFinalValue");
				map.put("type", "double");
				fields.add(map);
			}
			
			if ("on".equals(opts.get("showPersonalSocial"))) {
				map = new HashMap<String, Object>();
				map.put("name", "personalPaymentValue");
				map.put("type", "string");
				fields.add(map);
				map = new HashMap<String, Object>();
				map.put("name", "personalPaymentFinalValue");
				map.put("type", "double");
				fields.add(map);
			}
		}
		return fields;
	}

	public List<Map<String, Object>> fillSimpleColumns(List<Map<String, Object>> columns,Map<String,String> opts) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("header", "姓名");
		map.put("dataIndex", "name");
		map.put("flex", 1);
		map.put("coltype", "readonly");
		map.put("dataType", "simple");
		columns.add(map);
		map = new HashMap<String, Object>();
		map.put("header", "职务");
		map.put("dataIndex", "duty");
		map.put("flex", 1);
		map.put("coltype", "readonly");
		map.put("dataType", "simple");
		columns.add(map);
		map = new HashMap<String, Object>();
		map.put("header", "部门");
		map.put("dataIndex", "dept");
		map.put("flex", 1);
		map.put("coltype", "readonly");
		map.put("dataType", "simple");
		columns.add(map);
		if (opts!=null) {
			if ("on".equals(opts.get("showCompanySocial"))||"on".equals(opts.get("showPersonalSocial"))) {
				List<SocialItem> socialItems = socialItemRepository.findByItemType(SocialItemType.SocialSecurity);
				for (SocialItem item : socialItems) {
					map = new HashMap<String, Object>();
					map.put("header", item.getName());
					map.put("dataIndex", "string");
					map.put("flex", 1);
					
					List<Map<String,Object>> socialColumns = new ArrayList<Map<String,Object>>();
					
					Map<String,Object> socialColumn = new HashMap<String, Object>();
					socialColumn.put("header", "基数");
					socialColumn.put("dataIndex", "string");
					socialColumns.add(socialColumn);
					if ("on".equals(opts.get("showCompanySocial"))) {
						socialColumn = new HashMap<String, Object>();
						socialColumn.put("header", "单位");
						socialColumn.put("dataIndex", "string");
						
						List<Map<String,Object>> companySocialColumns = new ArrayList<Map<String,Object>>();
						
						Map<String,Object> companySocialColumn = new HashMap<String, Object>();
						companySocialColumn.put("header", "比例");
						companySocialColumn.put("dataIndex", "companyPaymentValue");
						companySocialColumn.put("flex", 1);
						companySocialColumn.put("coltype", "readonly");
						companySocialColumn.put("dataType", "simple");
						companySocialColumns.add(companySocialColumn);
						companySocialColumn = new HashMap<String, Object>();
						companySocialColumn.put("header", "金额");
						companySocialColumn.put("dataIndex", "companyPaymentFinalValue");
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
						personalSocialColumn.put("dataIndex", "personalPaymentValue");
						personalSocialColumn.put("flex", 1);
						personalSocialColumn.put("coltype", "readonly");
						personalSocialColumn.put("dataType", "simple");
						personalSocialColumns.add(personalSocialColumn);
						personalSocialColumn = new HashMap<String, Object>();
						personalSocialColumn.put("header", "金额");
						personalSocialColumn.put("dataIndex", "personalPaymentFinalValue");
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
		}
		
		return columns;
	}

	public List<Map<String,Object>> fillData(Map<String,String> persons, List<PayrollItem> items, Account account) {
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		
		Map<String,PayrollItem> payrollItemMap = new HashMap<String,PayrollItem>();
		for (PayrollItem item : items) {
			payrollItemMap.put(item.getPersonId(), item);
		}
		Set<String> newPersonIds = new HashSet<String>();
		for (Map.Entry<String, String> entry : persons.entrySet()) { 
			String personId = entry.getKey();
			if (payrollItemMap.get(personId)!=null) {
				PayrollItem item = payrollItemMap.get(personId);
				Map<String,Object> itemMap = item.parseMap();
				data.add(itemMap);
			}else {
				newPersonIds.add(personId);
			}
			
		}
		if (newPersonIds.size()>0) {
			Set<String> formulaIds = account.getFormulaIds();
			Map<String,Double> salaryItemValues = account.getSalaryItemValues();
			boolean isEnableLevel = account.isEnableLevel();
			Map<String,PersonExtension<Double>> personLevelItemMap = null;
			if (isEnableLevel) { 
				personLevelItemMap = personLevelService.findAllPersonLevelItems(newPersonIds);
			}
			Map<String, String> result;
			try {
				result = formulaService.caculateFormulas(formulaIds, salaryItemValues);
				Iterable<PersonAccountFile> personList = personAccountFileRepository.findAll(newPersonIds);
				for (PersonAccountFile person : personList) {
					Map<String,Object> personMap = new HashMap<String,Object>();
					personMap.put("personId", person.getId());
					personMap.put("name", person.getName());
					personMap.put("duty", person.getDuty());
					personMap.put("dept", person.getDept());
					for(AccountItem item : account.getAccountItems()){
						if (item.getType()==AccountItemType.Input) {
							String itemValue = "";
							// TODO 查找薪资层级对应该项的工资
							if (isEnableLevel&&personLevelItemMap.get(person.getId())!=null) {
								PersonExtension<Double> personExt = personLevelItemMap.get(person.getId());
								//itemValue = personExt.getItemValue(item.getSalaryItemId());
							}
							
							if ("".equals(itemValue)) {
								
							}
							
							
							personMap.put(item.getSalaryItemId(), item.getValue());
							
						}
					}
					personMap.putAll(result);
					data.add(personMap);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		return data;
	}

	/**
	 * @author lil 工资条列表
	 */
	@RequestMapping(value = "findItemList")
	public @ResponseBody Response findItemList(@RequestBody Map<String,Object> param) {
		String id = (String)param.get("id");
		Map<String,String> opts = (Map<String,String>)param.get("opts");
		Payroll payroll = payrollRepository.findOne(id);

		Account account = accountRepository.findOne(payroll.getAccountId());
		List<AccountItem> items = account.getAccountItems();
		List<Map<String, Object>> fields = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

		fillSimpleFields(fields,opts);
		fillSimpleColumns(columns,opts);

		for (AccountItem item : items) {
			fields.add(getFields(item));
			columns.add(getColumns(item));
		}

		List<PayrollItem> payrollItemList = payrollItemRepository.findByPayrollId(id);

		data = fillData(payroll.getPersons(), payrollItemList, account);

		Map<String, List<Map<String, Object>>> m = new HashMap<String, List<Map<String, Object>>>();
		m.put("fields", fields);
		m.put("columns", columns);
		m.put("data", data);
		return new Response(m);
	}

	private Map<String, Object> getFields(AccountItem item) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", item.getSalaryItemId());
		map.put("type", "float");
		return map;
	}

	private Map<String, Object> getColumns(AccountItem item) {
		Map<String, Object> map = new HashMap<String, Object>();
		switch (item.getType()) {
		case Input:
			Map<String, String> editor = new HashMap<String, String>();
			editor.put("xtype", "textfield");
			editor.put("name", item.getSalaryItemId());
			editor.put("allowBlank", "false");
			map.put("editor", editor);
			map.put("coltype", "input");
			map.put("dataType", "accountItem");
			map.put("dataIndex", item.getSalaryItemId());
			break;
		case Calculate:
			map.put("coltype", "readonly");
			map.put("dataType", "accountItem");
			map.put("dataIndex", item.getSalaryItemId());
		case System:
			map.put("coltype", "readonly");
		}

		map.put("flex", 1);
		map.put("header", item.getName());
		map.put("dataIndex", item.getSalaryItemId());
		return map;
	}


	/**
	 * 读取套帐列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "getAcountList")
	public Response getAccountList() {
		List<Account> accountList = mongoTemplate.findAll(Account.class);
		return new Response(accountList);
	}

	/**
	 * 读取套帐下的人员信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "getAccountPersons")
	public Response getAccountPersons(@RequestParam String accountId) {
		List<PersonAccountFile> accountPersonList = personAcountFileRepsitory.findByAccountId(accountId);
		return new Response(accountPersonList);
	}

	/**
	 * 修改工资条
	 * 
	 * @param person
	 * @return
	 */
	@RequestMapping(value = "updatePayroll")
	public Response updatePayroll(@RequestParam Map data) {
		return new Response(true);
	}

	/**
	 * 创建工资表
	 * 
	 * @param person
	 * @return
	 */
	@RequestMapping(value = "create")
	public Response create(HttpSession session, @RequestBody Payroll payroll) {

		User user = adminService.getUser(session);
		
		payroll.setCreatePersonId(user.getId());
		payroll.setCreatePersonName(user.getName());
		payroll.setCreateDate(getCurrentDate());
		payroll.setState(PayrollStatus.Unpublish.toString());
		payroll.setId(new ObjectId().toString());
		payroll.setMonth(payroll.getMonth().substring(0, 7));
		payroll.setSocialCostMonth(payroll.getSocialCostMonth().substring(0, 7));

		mongoTemplate.save(payroll);

		return new Response(true);
	}

	private String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return sdf.format(date);
	}

	/**
	 * 读取工资表
	 * 
	 * @param parameterObject
	 *            TODO
	 * @param id
	 * 
	 * @return
	 */
	@RequestMapping(value = "read")
	public Map<String, Object> read(Integer start, Integer limit, Integer page, String state, String subject,
			String month, String socialCostMonth) {

		Query q = getPageQuery(start, limit, page, state, subject, month, socialCostMonth);
		List<Payroll> payrolls = mongoTemplate.find(q, Payroll.class);
		Long total = mongoTemplate.count(q, Payroll.class);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("data", payrolls);
		map.put("success", true);
		return map;
	}

	private Query getPageQuery(Integer start, Integer limit, Integer page, String state, String subject, String month,
			String socialCostMonth) {

		Map<String, Object> mapFilter = new HashMap<String, Object>();

		mapFilter.put("state", state);
		if (notNull(subject))
			mapFilter.put("subject", subject);
		if (notNull(month))
			mapFilter.put("month", month.substring(0, 7));
		if (notNull(socialCostMonth))
			mapFilter.put("socialCostMonth", socialCostMonth.substring(0, 7));

		Query q = getQueryWithFilter(mapFilter);

		q.with(new Sort(Sort.Direction.ASC, "_id")).skip(start).limit(limit);
		return q;
	}
	
	private Query getQueryWithFilter(Map<String, Object> mapFilter){
		DBObject dbobject = new BasicDBObject();
		dbobject.putAll(mapFilter);
		return new BasicQuery(dbobject);
	}

	private Boolean notNull(String string) {
		return string != null && string.length() != 0;
	}

	/**
	 * 更新工资表状态为Paid
	 * 
	 * @param person
	 * @return
	 */
	@RequestMapping(value = "batchUpdate")
	public Response batchUpdate(@RequestParam(value = "arrId")List<String> arrId) {
		updateState(arrId,PayrollStatus.Paid.toString());
		return new Response(true);
	}
	/**
	 * 更新工资条状态为Unpublish
	 * @param arrId
	 * @return
	 */
	@RequestMapping(value = "batchWithdraw")
	public Response batchWithdraw(@RequestParam(value = "arrId")List<String> arrId) {
		updateState(arrId,PayrollStatus.Unpublish.toString());
		return new Response(true);
	}
	
	private void updateState(List<String> arrId,String state){
		for (String id : arrId) {
			Payroll payroll = mongoTemplate.findById(id,Payroll.class);
			payroll.setState(state);
			mongoTemplate.save(payroll);
		}
	}

	@RequestMapping(value = "update")
	public Response update(@RequestBody Payroll payroll) {

		mongoTemplate.save(payroll);

		return new Response(true);
	}


	/**
	 * 删除工资表
	 * 
	 * @param 社保套账id
	 * @return
	 */
	@RequestMapping(value = "remove")
	public Response remove(@RequestParam String id) {
		return new Response(true);
	}

	/**
	 * 查询工资表条目
	 * 
	 * @param 工资表id
	 * @return 工资表条目明细
	 */
	@RequestMapping(value = "loadPayrollItemById")
	public Response loadPayrollItemById(@RequestParam String id) {
		// TODO 通过工资表id查找工资表单元格项

		// TODO 将工资表单元格项拼成PayrollItem

		// TODO 调用SalaryService.computeSalary查询其他初始化值并计算出各列结果

		return new Response(true);
	}

	/**
	 * 保存工资表条目
	 * 
	 * @param 工资表id
	 * @return
	 */
	@RequestMapping(value = "savePayrollItem")
	public Response savePayrollItem(@RequestBody List<Map<String,Object>> items) {
		// TODO 调用SalaryService.computeSalary更新与之关联的其他工资表项目
		for (Map<String,Object> item : items) {
			PayrollItem payrollItem = new PayrollItem();
			payrollItem.convertDomain(item);
			payrollItemRepository.save(payrollItem);
		}
		
		return new Response(true);
	}
	
	/**
	 * 查找条目Field中与改工资条对应的公式的其他关联Field，然后传入field参与计算
	 * @param accountId	//方案id
	 * @param fieldId	//薪资项目id
	 * @return
	 */
	@RequestMapping("calculate")
	public Response calculate(@RequestBody Map<String,Object> map) {
		String accountId = (String) map.get("accountId");
		Map<String,Object> recordMap = (Map<String, Object>) map.get("record"); 
		
		Account account = accountRepository.findOne(accountId);
		Set<String> formulaIds = account.getFormulaIds();
		
		/**
		
		formulaService.caculateFormulas(formulaIds, recordMap);
		Set<String> fieldIds = formulaService.getInfluencedField(formulaIds, fieldId);
		
		Map<String,String> values = new HashMap<String,String>();
		for (String id : fieldIds) {
			String value = recordMap.get(id);
			values.put(id, value);
		}
		**/
		Map<String, String> changeFields = new HashMap<String,String>();
		try {
			PayrollItem payrollItem = new PayrollItem();
			payrollItem.convertDomain(recordMap);
			changeFields = formulaService.caculateFormulas(formulaIds,payrollItem.getValues());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new Response(changeFields);
	}
	
	
	/**
	 * 查找条目Field中与改工资条对应的公式的其他关联Field
	 * @param accountId	//方案id
	 * @param fieldId	//薪资项目id
	 * @return
	 */
	public Response getInfluencedField(@RequestParam String accountId,@RequestParam String fieldId) {
		Account account = accountRepository.findOne(accountId);
		Set<String> formulaIds = account.getFormulaIds();
		Set<String> fields = formulaService.getInfluencedField(formulaIds, fieldId);
		
		return new Response(fields);
	}
	
	

	/**
	 * 工作流相关-保存草稿
	 * 
	 * @param 工资表id
	 * @return
	 */
	@RequestMapping(value = "saveDraft")
	public Response saveDraft(@RequestParam Payroll pay) {
		return new Response(true);
	}

	/**
	 * 工作流相关-流转
	 * 
	 * @param 工资表id
	 * @return
	 */
	@RequestMapping(value = "next")
	public Response next(@RequestParam Payroll pay) {
		return new Response(true);
	}

	/**
	 * 工作流相关-撤回
	 * 
	 * @param 工资表id
	 * @return
	 */
	@RequestMapping(value = "withdraw")
	public Response withdraw(@RequestParam Payroll pay) {
		return new Response(true);
	}

}
