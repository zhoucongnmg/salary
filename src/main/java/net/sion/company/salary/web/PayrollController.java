package net.sion.company.salary.web;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import net.sion.company.salary.domain.SalaryItem.SalaryItemType;
import net.sion.company.salary.domain.PayrollItem;
import net.sion.company.salary.domain.PayrollSub;
import net.sion.company.salary.domain.PersonAccountFile;
import net.sion.company.salary.event.SystemSalaryItemPublisher;
import net.sion.company.salary.service.FormulaService;
import net.sion.company.salary.service.PayrollItemService;
import net.sion.company.salary.service.ExportService;
import net.sion.company.salary.service.PersonAccountFileService;
import net.sion.company.salary.service.SocialService;
import net.sion.company.salary.service.TaxService;
import net.sion.company.salary.sessionrepository.AccountRepository;
import net.sion.company.salary.sessionrepository.PayrollItemRepository;
import net.sion.company.salary.sessionrepository.PayrollRepository;
import net.sion.company.salary.sessionrepository.PayrollSubRepository;
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
	ExportService exportService;

	@Autowired
	UserRepository userRepository;
	@Autowired
	DeptRepository deptRepository;
	@Autowired
	ApplicationContext ctx;
	
	@Autowired
	PayrollItemService payrollItemService;
	
	@Autowired
	TaxService taxService;
	
	@Autowired
	PayrollSubRepository payrollSubRepository;
	
	@Autowired
	PersonAccountFileService personService;
	
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
		
		
		List<PayrollItem> items = payrollItemService.generatePayrollItem(roll,roll.getPersons().keySet());
		payrollItemRepository.save(items);
		return new Response("sucess", roll, true);
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
		
		/*for (Payroll payroll : payrolls) {
			String salaryItemId = getSalaryItemId(payroll);
			Double sum = getPayrollSum(payroll,salaryItemId);		
			payroll.setSum(sum);
		}*/

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("data", payrolls);
		map.put("success", true);
		return map;
	}
	
	/**
	 * @author lil 工资条列表
	 */
	@RequestMapping(value = "findItemList")
	public @ResponseBody Response findItemList(@RequestBody Map<String,Object> param) {
		String id = (String)param.get("id");
		Map<String,String> opts = (Map<String,String>)param.get("opts");
		String type = opts.get("type");
		String query = opts.get("query");
		Payroll payroll = new Payroll();
		PayrollSub payrollsub = new PayrollSub();
		if ("Payroll".equals(type)) {
			payroll = payrollRepository.findOne(id);
		}else if ("PayrollSub".equals(type)) {
			String payrollSubId = opts.get("payrollSubId");
			payrollsub = payrollSubRepository.findOne(payrollSubId);
			payroll = payrollRepository.findOne(payrollsub.getPayrollId());
		}
		//1.20周聪添加 工资条明细中按姓名筛选
		filterByName(payroll,opts);

		Account account = accountRepository.findOne(payroll.getAccountId());
		List<AccountItem> items = account.getAccountItems();;
		if ("PayrollSub".equals(type)) {
			items = filterAccountItems(items, payrollsub.getItems());
		}
		
		List<Map<String, Object>> fields = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		if ("Simple".equals(query)) {
			payrollItemService.fillSimpleFields(fields,opts);
			payrollItemService.fillSimpleColumns(columns,opts);
		}else {
			payrollItemService.fillBaseFields(fields);
			for (AccountItem item : items) {
				if (item.isShow()) {
					columns.add(payrollItemService.getColumns(item));
				}
				fields.add(payrollItemService.getFields(item));
			}
		}	
		String name = getRegexName(opts);
		List<PayrollItem> payrollItemList = new ArrayList<PayrollItem>();
		if ("Payroll".equals(type)) {
			payrollItemList = payrollItemRepository.findByPayrollIdAndNameRegexOrderByPersonIdAsc(id,name);
		}else if ("PayrollSub".equals(type)) {
			payrollItemList = payrollItemRepository.findByPayrollIdAndNameRegexOrderByPersonIdAsc(opts.get("payrollSubId"),name);
		}
		data = payrollItemService.fillData(payroll, payrollItemList, account);

		Map<String, List<Map<String, Object>>> m = new HashMap<String, List<Map<String, Object>>>();
		m.put("fields", fields);
		m.put("columns", columns);
		m.put("data", data);
		return new Response(m);
	}
	
	private List<AccountItem> filterAccountItems(List<AccountItem> items, Set<String> set) {
		List<AccountItem> containItems = new ArrayList<AccountItem>();
		for (AccountItem item : items) {
			if (set.contains(item.getId())) {
				containItems.add(item);
			}
		}
		
		return containItems;
	}
	
	private String getRegexName(Map<String,String> opts){
		if(opts ==null||opts.get("name")==null)
			return "";
		else
			return opts.get("name");
	}
	
	
	private String getSalaryItemId(Payroll payroll){
		
		Account account = accountRepository.findById(payroll.getAccountId());
		List<AccountItem> accountItems = account.getAccountItems();
		for (AccountItem accountItem : accountItems) {
			if("实发工资".equals(accountItem.getName()))
				return accountItem.getSalaryItemId();
		}
		return null;
	}
	
	private Double getPayrollSum(Payroll payroll,String salaryItemId){
		Double sum = 0.0;
		
		List<PayrollItem> payrollItems = payrollItemRepository.findByPayrollId(payroll.getId());
		for (PayrollItem payrollItem : payrollItems) {
			Map<String,Double> values = payrollItem.getValues();
			if(salaryItemId!=null)
				sum += values.get(salaryItemId);
		}
		return sum;
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
	
	/**
	 * @author lil 导出工资条
	 * @throws IOException
	 */
	@RequestMapping(value = "exportItemList")
	public Response exportItemList(HttpSession session, HttpServletResponse response, @RequestParam String id, @RequestParam String optsId, @RequestParam String note)
			throws IOException {
		User user = adminService.getUser(session);
		note = URLDecoder.decode(URLDecoder.decode(note, "UTF-8"));
		createExcel("export", id, optsId, response, user, note);
		return new Response(true);
	}

	/**
	 * @author lil 生成工资条
	 * @throws IOException
	 */
	@RequestMapping(value = "createPayrollExcel")
	public Response createPayrollExcel(HttpSession session, HttpServletResponse response, @RequestParam String id, @RequestParam String optsId)
			throws IOException {
		User user = adminService.getUser(session);
		createExcel("create", id, optsId, response, user, "");
		return new Response(true);
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

		List<PersonAccountFile> personAccountFiles = personAcountFileRepsitory.findByAccountIdOrIdIn(accountId, persons.keySet());

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

	private void createExcel(String method, String id, String optsId, HttpServletResponse response, User user, String note) throws IOException {
		String serverPath = ctx.getResource("/").getFile().getPath();
		String folderPath = serverPath + "/temp/salary/export";
		String filePath = folderPath + "/" + optsId + ".json";
		File file = new File(filePath);
		String jsonStr = FileUtil.readAsString(file);
		Map<String, String> opts = jackson.readValue(jsonStr, new TypeReference<Map<String, String>>() {
		});
		file.delete();

		Payroll payroll = payrollRepository.findOne(id);
		Account account = accountRepository.findOne(payroll.getAccountId());
		List<AccountItem> items = account.getAccountItems();
		List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		payrollItemService.fillSimpleColumns(columns, opts);
		
		List<PayrollSub> payrollSubs = payrollSubRepository.findByPayrollId(id);
		for (PayrollSub sub : payrollSubs) {
			List<AccountItem> subAccountItems = filterAccountItems(items, sub.getItems());
			for (AccountItem item : subAccountItems) {
				columns.add(payrollItemService.getSubColumns(sub.getId(), item));
			}
		}
		
		for (AccountItem item : items) {
			columns.add(payrollItemService.getColumns(item));
		}
		List<PayrollItem> payrollItemList = payrollItemRepository.findByPayrollId(id);
		data = payrollItemService.fillData(payroll, payrollItemList, account,payrollSubs);
		if ("create".equals(method)) {
			exportService.createExcel(payroll.getSubject(), columns, data, response);
		} else if ("export".equals(method)) {
			exportService.exportExcel(payroll.getSubject(), columns, data, response, user, note);
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
		Set<String> savePersonIds = new HashSet<String>();
		Set<String> removePersonIds = new HashSet<String>();
		
		
		Payroll old = payrollRepository.findOne(payroll.getId());
		Set<String> oldPersonIds =  new HashSet<String>(old.getPersons().keySet());
		
		Set<String> newPersonIds = new HashSet<String>(payroll.getPersons().keySet());
		
		for (String newPersonId : newPersonIds) {
			if (!oldPersonIds.contains(newPersonId)) {
				savePersonIds.add(newPersonId);
			}
		}
		
		for (String oldPersonId : oldPersonIds) {
			if (!newPersonIds.contains(oldPersonId)) {
				removePersonIds.add(oldPersonId);
			}
		}
		List<PayrollSub> payrollSubs = payrollSubRepository.findByPayrollId(payroll.getId());
		
		payrollRepository.save(payroll);
		if (savePersonIds.size()>0) {
			List<PayrollItem> items = payrollItemService.generatePayrollItem(payroll, savePersonIds);
			payrollItemRepository.save(items);
			if (payrollSubs!=null) {
				for (PayrollSub sub : payrollSubs) {
					List<PayrollItem> subItems = payrollItemService.generatePayrollItem(payroll, sub, savePersonIds);
					payrollItemRepository.save(subItems);
				}
			}
		}
		
		if (removePersonIds.size()>0) {
			Iterable<PayrollItem> removePayrollItems = payrollItemRepository.findByPayrollIdAndPersonIdIn(payroll.getId(),new ArrayList(removePersonIds));
			payrollItemRepository.delete(removePayrollItems);
			if (payrollSubs!=null) {
				for (PayrollSub sub : payrollSubs) {
					List<PayrollItem> items = payrollItemRepository.findByPayrollIdAndPersonIdIn(sub.getId(), new ArrayList(removePersonIds));
					payrollItemRepository.delete(items);
				}
			}
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
		
		List<PayrollItem> payrollItems = payrollItemRepository.findByPayrollId(payroll.getId());
		
		payrollItemRepository.delete(payrollItems);

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
			String id = (String)item.get("id");
			PayrollItem payrollItem = payrollItemRepository.findOne(id);
			payrollItem.convertDomain(item);
			saveItems.add(payrollItem);
		}
		payrollItemRepository.save(saveItems);
		return new Response(saveItems);
	}
	
	
	/**
	 * 生成工资表条目
	 * 
	 * @param 工资表id
	 * @return
	 */
	@RequestMapping(value = "generatePayrollItem")
	public Response generatePayrollItem(@RequestParam List<String> ids) {
		for (String id : ids) {
			Payroll payroll = payrollRepository.findOne(id);
			
			List<PayrollItem> oldPayrollItemList = payrollItemRepository.findByPayrollId(id);
			
			List<PayrollItem> newPayrollItemList = payrollItemService.generatePayrollItem(payroll, payroll.getPersons().keySet());
			
			payrollItemRepository.delete(oldPayrollItemList);
			payrollItemRepository.save(newPayrollItemList);
		}
		return new Response(true);
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
		Map<String, Double> changeFields = new HashMap<String,Double>();
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
		
		try {
			PayrollItem payrollItem = new PayrollItem();
			payrollItem.convertDomain(recordMap);
			changeFields.putAll(payrollItem.getValues());
			Map<String,AccountItem> personAccountItemMap = new LinkedHashMap<String,AccountItem>();
			for (AccountItem item : account.getAccountItems()) {
				personAccountItemMap.put(item.getId(), item);
			}
			
			
			Iterable<AccountItem> sortItems = payrollItemService.sortAccountItem(account.getAccountItems());
			for (AccountItem item : sortItems) {
				if (item.getType() == SalaryItemType.Calculate) {
					Map<String,Double> calculateMap = formulaService.caculateFormulas(formulaIds, changeFields);
					changeFields.putAll(calculateMap);
				}else if (item.getType() == SalaryItemType.Tax) {
					String parentId = item.getParentId();
					AccountItem parent = personAccountItemMap.get(parentId);
					String parentSalaryItemId = parent.getSalaryItemId();
					Double value = changeFields.get(parentSalaryItemId);
					Double taxValue = taxService.getFastNumber(item.getTaxId(),value);
					changeFields.put(item.getSalaryItemId(), item.decimal(item.getCarryType(), item.getPrecision(), taxValue));
				}
				
			}
			
			//payrollItem.convertDomain(changeFields);
			//payrollItemRepository.save(payrollItem);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new Response(changeFields);
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
	@RequestMapping("calculateSub")
	public Response calculateSub(@RequestBody Map<String, Object> map) {
		Map<String, Double> changeFields = new HashMap<String,Double>();
		Map<String, String> recordMap = (Map<String, String>) map.get("record");
		List<Map<String,String>> subRecordMapList =(List<Map<String,String>>) map.get("subRecords");

		
		PayrollItem payrollItem = new PayrollItem();
		payrollItem.convertDomain(recordMap);
		//payrollItemRepository.save(payrollItem);
		
		String personId = payrollItem.getPersonId();
		String payrollSubId = payrollItem.getPayrollId();
		PayrollSub payrollSub = payrollSubRepository.findOne(payrollSubId);
		String payrollId = payrollSub.getPayrollId();
		Payroll payroll = payrollRepository.findOne(payrollId);
		List<PayrollItem> parentPayrollItems = payrollItemRepository.findByPayrollIdAndPersonIdIn(payrollId, Arrays.asList(new String[]{personId}));
		PayrollItem parentPayrollItem = parentPayrollItems.get(0);
		if (parentPayrollItem!=null) {
			
			Account account = accountRepository.findOne(payroll.getAccountId());
			Set<String> formulaIds = account.getFormulaIds();
			Map<String,Double> parentValues = parentPayrollItem.getValues();
			Map<String,Double> values = payrollItem.getValues();
			
			List<AccountItem> items =  account.getAccountItems();
			Map<String,AccountItem> salaryItemsMap = new HashMap<String,AccountItem>();
			for (AccountItem item : items) {
				salaryItemsMap.put(item.getSalaryItemId(), item);
			}
			
			
			for (Map.Entry<String, Double> entry : values.entrySet()) {
				Double sum = 0.0d;
				String itemId = entry.getKey();
				AccountItem item = salaryItemsMap.get(itemId);
				Double itemValue = entry.getValue();
				sum+=itemValue;
				if (item!=null) {
					/*
					for (PayrollSub sub : payrollSubs) {
						if (!sub.getId().equals(payrollSubId)) {
							if (sub.getItems().contains(item.getId())) {
								List<PayrollItem> payrollItems = payrollItemRepository.findByPayrollIdAndPersonIdIn(payrollId, Arrays.asList(new String[]{personId}));
								PayrollItem subPayrollItem = payrollItems.get(0);
								Map<String,Double> itemValues = subPayrollItem.getValues();
								Double value = itemValues.get(itemId);
								sum+=value;
							}
						}
						
					}
					*/
					if (subRecordMapList!=null) {
						for (Map<String,String> subRecordMap : subRecordMapList) {
							PayrollItem subPayrollItem = new PayrollItem();
							subPayrollItem.convertDomain(subRecordMap);
							Map<String,Double> itemValues = subPayrollItem.getValues();
							Double value = itemValues.get(itemId);
							sum+=value;
						}
					}
					
				}
				parentValues.put(itemId, sum);
				
			}
			
			
			try {
				parentPayrollItem.convertDomain(parentValues);
				changeFields = formulaService.caculateFormulas(formulaIds, parentValues);
				parentPayrollItem.convertDomain(changeFields);
				//payrollItemRepository.save(parentPayrollItem);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		}

		return new Response(parentPayrollItem.getValues());
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

	private String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return sdf.format(date);
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
	

	private void updatePayrollState(List<String> arrId, String state) {
		for (String id : arrId) {
			Payroll payroll = mongoTemplate.findById(id, Payroll.class);
			payroll.setState(state);
			mongoTemplate.save(payroll);
		}
	}

	
	
	
}
