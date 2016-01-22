package net.sion.company.salary.web;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sion.boot.config.jackson.CustomJackson;
import net.sion.boot.mongo.template.SessionMongoTemplate;
import net.sion.company.salary.domain.Account;
import net.sion.company.salary.domain.AccountItem;
//import net.sion.company.salary.domain.AccountItem.AccountItemType;
import net.sion.company.salary.domain.Payroll;
import net.sion.company.salary.domain.Payroll.PayrollStatus;
import net.sion.company.salary.domain.PayrollItem;
import net.sion.company.salary.domain.PersonAccountFile;
import net.sion.company.salary.domain.PersonExtension;
import net.sion.company.salary.domain.SalaryItem.SalaryItemType;
import net.sion.company.salary.domain.SocialAccountItem;
import net.sion.company.salary.domain.SocialItem;
import net.sion.company.salary.domain.SocialItem.SocialItemType;
import net.sion.company.salary.domain.SystemSalaryItem;
import net.sion.company.salary.event.SystemSalaryItemPublisher;
import net.sion.company.salary.service.FormulaService;
import net.sion.company.salary.service.PayrollService;
import net.sion.company.salary.service.PersonAccountFileService;
import net.sion.company.salary.service.SocialService;
import net.sion.company.salary.service.TaxService;
import net.sion.company.salary.sessionrepository.AccountRepository;
import net.sion.company.salary.sessionrepository.PayrollItemRepository;
import net.sion.company.salary.sessionrepository.PayrollRepository;
import net.sion.company.salary.sessionrepository.PersonAccountFileRepository;
import net.sion.company.salary.sessionrepository.PersonAccountRepository;
import net.sion.company.salary.sessionrepository.SocialItemRepository;
import net.sion.company.salary.sessionrepository.SystemSalaryItemRepository;
import net.sion.core.admin.domain.Dept;
import net.sion.core.admin.domain.User;
import net.sion.core.admin.service.AdminService;
import net.sion.core.admin.sessionrepository.DeptRepository;
import net.sion.core.admin.sessionrepository.UserRepository;
import net.sion.util.mvc.Response;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.util.FileUtil;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
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
	SocialService socialService;
	@Autowired
	SystemSalaryItemRepository systemSalaryItemRepository;
	@Autowired
	SocialItemRepository socialItemRepository;
	@Autowired
	SystemSalaryItemPublisher publisher;
	@Autowired
	PayrollService payrollService;

	@Autowired
	UserRepository userRepository;
	@Autowired
	DeptRepository deptRepository;
	@Autowired
	ApplicationContext ctx;
	
	@Autowired
	TaxService taxService;
	
	@Autowired
	PersonAccountFileService personService;
	
	
	public List<PayrollItem> generatePayrollItem(Payroll payroll,Set<String> personIds) {
		List<PayrollItem> datas = new ArrayList<PayrollItem>();
		try {
			Account account = accountRepository.findOne(payroll.getAccountId()); 
			Iterable<PersonAccountFile> personList = personAccountFileRepository.findAll(personIds);
			
			for (PersonAccountFile person : personList) {
				
				Map<String, Object> personMap = new HashMap<String, Object>();
				Map<String, String> simplePersonMap = new HashMap<String, String>();
				Map<String, Double> dataPersonMap = new HashMap<String, Double>();
				simplePersonMap.put("personId", person.getId());
				simplePersonMap.put("payrollId", payroll.getId());
				simplePersonMap.put("name", person.getName());
				simplePersonMap.put("duty", person.getDuty());
				simplePersonMap.put("dept", person.getDept());
				simplePersonMap.put("personCode", person.getPersonCode());
				simplePersonMap.put("idCard", person.getIdCard());
				simplePersonMap.put("bankAccount", person.getBankAccount());
				
				Map<String,AccountItem> personAccountItemMap = new HashMap<String,AccountItem>();
				Set<String> formulaIds = new HashSet<String>();
				for (AccountItem item : account.getAccountItems()) {
					personAccountItemMap.put(item.getId(), item);
					
					if (item.getType() == SalaryItemType.Input) {
						personMap.put(item.getSalaryItemId(), item.getValue());
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
							dataPersonMap.put(item.getSalaryItemId(), taxService.getFastNumber(item.getTaxId(),value));
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
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datas;
	}

	public List<Map<String, Object>> fillSimpleFields(List<Map<String, Object>> fields, Map<String,String> opts) {
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
		map = new HashMap<String, Object>();
		map.put("name", "personCode");
		map.put("type", "string");
		fields.add(map);
		
		if (opts!=null) {
			if ("on".equals(opts.get("showCompanySocial"))||"on".equals(opts.get("showPersonalSocial"))) {
				List<SocialItem> socialItems = socialItemRepository.findByItemType(SocialItemType.SocialSecurity);
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
		if (opts!=null) {
			if ("on".equals(opts.get("showCompanySocial"))||"on".equals(opts.get("showPersonalSocial"))) {
				List<SocialItem> socialItems = socialItemRepository.findByItemType(SocialItemType.SocialSecurity);
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

	/**
	 * @author lil 工资条列表
	 */
	@RequestMapping(value = "findItemList")
	public @ResponseBody Response findItemList(@RequestBody Map<String,Object> param) {
		String id = (String)param.get("id");
		Map<String,String> opts = (Map<String,String>)param.get("opts");
		Payroll payroll = payrollRepository.findOne(id);
		
		//1.20周聪添加 工资条明细中按姓名筛选
		filterByName(payroll,opts);

		Account account = accountRepository.findOne(payroll.getAccountId());
		List<AccountItem> items = account.getAccountItems();
		List<Map<String, Object>> fields = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

		fillSimpleFields(fields,opts);
		fillSimpleColumns(columns,opts);
		for (AccountItem item : items) {
			if (item.isShow()) {
				fields.add(getFields(item));
				columns.add(getColumns(item));
			}
		}
		List<PayrollItem> payrollItemList = payrollItemRepository.findByPayrollId(id);

		data = fillData(payroll, payrollItemList, account);

		Map<String, List<Map<String, Object>>> m = new HashMap<String, List<Map<String, Object>>>();
		m.put("fields", fields);
		m.put("columns", columns);
		m.put("data", data);
		return new Response(m);
	}
	
	private void filterByName(Payroll payroll,Map<String,String> opts){
		if(opts ==null||opts.get("name")==null)
			return;
		else{
			Map<String,String> person = payroll.getPersons();
	/*		for (Map.Entry<String, String> entry : person.entrySet()) {
				if(!entry.getValue().contains(opts.get("name")))
					person.remove(entry.getKey());
			}*/
		/*	for (String key : person.keySet()) {
				if(!person.get(key).contains(opts.get("name")))
					person.remove(key);
			}*/
			Iterator<Map.Entry<String, String>> it = person.entrySet().iterator();
			while(it.hasNext()){
				if(!it.next().getValue().contains(opts.get("name")))
					it.remove();
			}
		}
	}

	/**
	 * @author lil 导出工资条
	 * @throws IOException
	 */
	@RequestMapping(value = "exportItemList")
	public void exportItemList(HttpServletResponse response, @RequestParam String id, @RequestParam String optsId)
			throws IOException {
		createExcel("export", id, optsId, response);
	}

	/**
	 * @author lil 生成工资条
	 * @throws IOException
	 */
	@RequestMapping(value = "createPayrollExcel")
	public void createPayrollExcel(HttpServletResponse response, @RequestParam String id, @RequestParam String optsId)
			throws IOException {
		createExcel("create", id, optsId, response);
	}

	// 保存opts临时文件
	@RequestMapping(value = "saveExcelTemp")
	public Response saveExcelTemp(@RequestBody Map<String, Object> param) throws IOException {
		Map<String, String> opts = (Map<String, String>) param.get("opts");
		Date date = new Date();
		String serverPath = ctx.getResource("/").getFile().getPath();
		String folderPath = serverPath + "/temp/salary/export";
		File fileFordel = new File(folderPath);
		if (!fileFordel.exists()) {
			fileFordel.mkdirs();
		}
		String fileName = date.getTime() + "";
		String filePath = folderPath + "/" + fileName + ".json";
		File file = new File(filePath);
		if (file.exists()) {
			boolean boo = file.delete();
		}
		String josnStr = JSONObject.valueToString(opts);
		FileUtil.writeAsString(file, josnStr);
		return new Response(fileName, true);
	}

	private void createExcel(String method, String id, String optsId, HttpServletResponse response) throws IOException {
		String serverPath = ctx.getResource("/").getFile().getPath();
		String folderPath = serverPath + "/temp/salary/export";
		String filePath = folderPath + "/" + optsId + ".json";
		File file = new File(filePath);
		String josnStr = FileUtil.readAsString(file);
		Map<String, String> opts = jackson.readValue(josnStr, new TypeReference<Map<String, String>>() {
		});
		file.delete();

		Payroll payroll = payrollRepository.findOne(id);
		Account account = accountRepository.findOne(payroll.getAccountId());
		List<AccountItem> items = account.getAccountItems();
		List<Map<String, Object>> fields = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		fillSimpleFields(fields, opts);
		fillSimpleColumns(columns, opts);
		for (AccountItem item : items) {
			fields.add(getFields(item));
			columns.add(getColumns(item));
		}
		List<PayrollItem> payrollItemList = payrollItemRepository.findByPayrollId(id);
		data = fillData(payroll, payrollItemList, account);
		if ("create".equals(method)) {
			payrollService.createExcel(columns, data, response);
		} else if ("export".equals(method)) {
			payrollService.exportExcel(columns, data, response);
		}
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

	/**
	 * 读取套帐列表 zhoucong
	 * 
	 * @return
	 */
	@RequestMapping(value = "getAcountList")
	public Response getAccountList() {
		List<Account> accountList = mongoTemplate.findAll(Account.class);
		return new Response(accountList);
	}

	/**
	 * 读取套帐下的人员信息 zhoucong
	 * 
	 * @return
	 */
	@RequestMapping(value = "getAccountPersons")
	public List<Object> getAccountPersons(HttpSession session, @RequestParam(value = "accountId") String accountId,
			@RequestParam(value = "persons") JSONObject persons) {

		Map<String, Object> allParentDeptMap = new HashMap<String, Object>();
		Map<String, List<Object>> deptUserMap = new HashMap<String, List<Object>>();
		List<Object> personWithOutDept = new ArrayList<Object>();
		String companyId = adminService.getCompany(session).getId();

		List<PersonAccountFile> personAccountFiles = personAcountFileRepsitory.findByAccountIdAndInId(accountId, persons.keySet());

		for (PersonAccountFile personAccountFile : personAccountFiles) {
			if (notNull(personAccountFile.getDeptId())) {
				Map<String, Dept> deptMap = findAllParentIntoMap(personAccountFile.getDeptId(), companyId);
				allParentDeptMap.putAll(deptMap);
				List<Object> personHasDept = deptUserMap.get(personAccountFile.getDeptId());
				if (personHasDept == null) {
					personHasDept = new ArrayList<Object>();
				}
				personHasDept.add(newPersonNode(personAccountFile,persons));
				deptUserMap.put(personAccountFile.getDeptId(), personHasDept);
			} else {
				personWithOutDept.add(newPersonNode(personAccountFile,persons));
			}
		}

		List<Object> deptUserList = new ArrayList<Object>();

		List deptList = adminService.traverseDepts(companyId);

		if (notNull(deptList)) {
			deptUserList = this.filterTraverseDeptsByMap(deptList, allParentDeptMap, deptUserMap);
		}

		if (personWithOutDept.size() > 0) {
			Dept dept = new Dept();
			dept.setName("其他部门");
			dept.setChildren(personWithOutDept);
			deptUserList.add(dept);
		}

		return deptUserList;
	}
	
	private Map<String,Object> newPersonNode(PersonAccountFile personAccountFile,JSONObject persons){
		Map<String, Object> node = new HashMap<String, Object>();
		node.put("name", personAccountFile.getName());
		node.put("leaf", true);
		node.put("id", personAccountFile.getId());
		if (!mapContains(persons,personAccountFile.getId()))
			node.put("checked", false);
		return node;
	}
	
	private Boolean mapContains(JSONObject map,String key){
		if(map.length()!=0)
			return map.keySet().contains(key);
		else
			return true;
	}

	private Map<String, Dept> findAllParentIntoMap(String deptId, String companyId) {

		Map<String, Dept> deptMap = new HashMap<String, Dept>();

		Dept dept = deptRepository.findOne(deptId);
		deptMap.put(dept.getId(), dept);

		if (companyId.equals(dept.getParentId())) {
			return deptMap;
		} else {
			return this.findAllParentIntoMap(dept.getParentId(), companyId);
		}
	}

	private List<Object> filterTraverseDeptsByMap(List deptList, Map<String, Object> allParentDeptMap,
			Map<String, List<Object>> deptUserMap) {
		Iterator<Object> it = deptList.iterator();
		while (it.hasNext()) {
			Dept dept = (Dept) it.next();
			Dept t_dept = (Dept) allParentDeptMap.get(dept.getId());
			if (t_dept == null) {
				it.remove();
			} else {
				List<Object> userList = deptUserMap.get(dept.getId());
				if (!notNull(dept.getChildren())) {
					if (notNull(userList)) {
						dept.setChildren(userList);
					}
				} else {
					List<Object> childrenDeptList = this.filterTraverseDeptsByMap(dept.getChildren(), allParentDeptMap,
							deptUserMap);
					if (notNull(userList)) {
						childrenDeptList.addAll(userList);
					}
					dept.setChildren(childrenDeptList);
				}
			}
		}
		return deptList;
	}

	/**
	 * 创建工资条 zhoucong
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

		Payroll roll = payrollRepository.save(payroll);
		
		
		List<PayrollItem> items = generatePayrollItem(roll,roll.getPersons().keySet());
		payrollItemRepository.save(items);
		return new Response("sucess", roll, true);
	}

	private String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return sdf.format(date);
	}

	/**
	 * 读取工资条 zhoucong
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
		if (notNull(subject)) {
			mapFilter.put("subject", getPattern(subject.trim()));
		}
		if (notNull(month)) {
			mapFilter.put("month", getPattern(month.substring(0, 7)));
		}
		if (notNull(socialCostMonth)) {
			mapFilter.put("socialCostMonth", getPattern(socialCostMonth.substring(0, 7)));
		}

		Query q = getQueryWithFilter(mapFilter);
		q.with(new Sort(Sort.Direction.ASC, "_id")).skip(start).limit(limit);
		return q;
	}
	
	private Pattern getPattern(String str){
		return Pattern.compile("^.*" + str + ".*$", Pattern.CASE_INSENSITIVE);
	}

	private Query getQueryWithFilter(Map<String, Object> mapFilter) {
		DBObject dbobject = new BasicDBObject();
		dbobject.putAll(mapFilter);
		return new BasicQuery(dbobject);
	}

	private Boolean notNull(String string) {
		return string != null && string.length() != 0;
	}
	
	private Boolean notNull(List<?> list){
		return list !=null && list.size() > 0;
	}

	/**
	 * 更新工资表状态为Paid zhoucong
	 * 
	 * @param person
	 * @return
	 */
	@RequestMapping(value = "batchUpdate")
	public Response batchUpdate(@RequestParam(value = "arrId") List<String> arrId) {
		updatePayrollState(arrId, PayrollStatus.Paid.toString());
		return new Response(true);
	}

	/**
	 * 更新工资条状态为Unpublish zhoucong
	 * 
	 * @param arrId
	 * @return
	 */
	@RequestMapping(value = "batchWithdraw")
	public Response batchWithdraw(@RequestParam(value = "arrId") List<String> arrId) {
		updatePayrollState(arrId, PayrollStatus.Unpublish.toString());
		return new Response(true);
	}

	private void updatePayrollState(List<String> arrId, String state) {
		for (String id : arrId) {
			Payroll payroll = mongoTemplate.findById(id, Payroll.class);
			payroll.setState(state);
			mongoTemplate.save(payroll);
		}
	}

	/**
	 * 更新工资条 zhoucong
	 * 
	 * @param payroll
	 * @return
	 */
	@RequestMapping(value = "update")
	public Response update(@RequestBody Payroll payroll) {
		
		
		Payroll old = payrollRepository.findOne(payroll.getId());
		Set<String> oldPersonIds =  old.getPersons().keySet();
		
		Set<String> newPersonIds = payroll.getPersons().keySet();
		for (String oldPersonId : oldPersonIds) {
			newPersonIds.remove(oldPersonId);
		}
		payrollRepository.save(payroll);
		if (newPersonIds.size()>0) {
			List<PayrollItem> items = generatePayrollItem(payroll, newPersonIds);
			payrollItemRepository.save(items);
		}
		return new Response(true);
	}

	/**
	 * 删除工资条 zhoucong
	 * 
	 * @param 社保套账id
	 * @return
	 */
	@RequestMapping(value = "remove")
	public Response remove(@RequestBody Payroll payroll) {

		mongoTemplate.remove(payroll);

		return new Response(true);
	}

	/**
	 * 保存工资表条目
	 * 
	 * @param 工资表id
	 * @return
	 */
	@RequestMapping(value = "savePayrollItem")
	public Response savePayrollItem(@RequestBody List<Map<String, Object>> items) {
		List<PayrollItem> saveItems = new ArrayList<PayrollItem>();
		for (Map<String, Object> item : items) {
			PayrollItem payrollItem = new PayrollItem();
			payrollItem.convertDomain(item);
			saveItems.add(payrollItem);
		}
		payrollItemRepository.save(saveItems);
		return new Response(saveItems);
	}

	/**
	 * 查找条目Field中与改工资条对应的公式的其他关联Field，然后传入field参与计算
	 * 
	 * @param accountId
	 *            方案id
	 * @param fieldId
	 *            薪资项目id
	 * @return
	 */
	@RequestMapping("calculate")
	public Response calculate(@RequestBody Map<String, Object> map) {

		String accountId = (String) map.get("accountId");
		Map<String, String> recordMap = (Map<String, String>) map.get("record");

		Account account = accountRepository.findOne(accountId);
		Set<String> formulaIds = account.getFormulaIds();

		/**
		 * 
		 * formulaService.caculateFormulas(formulaIds, recordMap); Set
		 * <String> fieldIds = formulaService.getInfluencedField(formulaIds,
		 * fieldId);
		 * 
		 * Map<String,String> values = new HashMap<String,String>(); for (String
		 * id : fieldIds) { String value = recordMap.get(id); values.put(id,
		 * value); }
		 **/
		Map<String, Double> changeFields = new HashMap<String, Double>();
		try {
			PayrollItem payrollItem = new PayrollItem();
			payrollItem.convertDomain(recordMap);
			changeFields = formulaService.caculateFormulas(formulaIds, payrollItem.getValues());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new Response(changeFields);
	}

	/**
	 * 查找条目Field中与改工资条对应的公式的其他关联Field
	 * 
	 * @param accountId
	 *            //方案id
	 * @param fieldId
	 *            //薪资项目id
	 * @return
	 */
	public Response getInfluencedField(@RequestParam String accountId, @RequestParam String fieldId) {
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
	
	
	public static void main(String[] args) {
		Set<String> old = new HashSet<String>();
		Set<String> new1 = new HashSet<String>();
		old.add("1");
		old.add("2");
		old.add("3");
		
		new1.add("1");
		new1.add("2");
		new1.add("3");
		new1.add("4");
		new1.removeAll(old);
		System.out.println(new1);
		
	}
	
}
