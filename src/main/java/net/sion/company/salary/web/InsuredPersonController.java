package net.sion.company.salary.web;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.List;
import java.util.Map;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestController;

import net.sion.boot.mongo.template.DynamicMongoTemplate;
import net.sion.boot.mongo.template.DynamicMongoTemplate;
import net.sion.company.salary.domain.Account;
import net.sion.company.salary.domain.Account;
import net.sion.company.salary.domain.InsuredPerson;
import net.sion.company.salary.domain.Level;
import net.sion.company.salary.domain.PersonAccountFile;
import net.sion.company.salary.domain.PersonAccountFile;
import net.sion.company.salary.domain.SocialAccount;
import net.sion.company.salary.sessionrepository.AccountRepository;
import net.sion.company.salary.sessionrepository.AccountRepository;
import net.sion.company.salary.sessionrepository.PersonAccountRepository;
import net.sion.company.salary.sessionrepository.PersonAccountRepository;
import net.sion.core.admin.domain.User;
import net.sion.core.admin.domain.User;
import net.sion.core.admin.service.AdminService;
import net.sion.core.admin.service.AdminService;
import net.sion.core.filedump.util.AsposeUtil;
import net.sion.util.file.FileUtil;
import net.sion.util.mvc.Response;
import net.sion.util.mvc.Response;
/**
 * 社保投保
 * @author niex 
 */
@RestController
@RequestMapping("/salary/person/") 
public class InsuredPersonController {
	@Autowired
	private PersonAccountRepository personAccountRepo;
	@Autowired
	private AccountRepository accountRepo;
	@Autowired
	DynamicMongoTemplate dmt;
	@Autowired
	AdminService adminService;
	@Autowired
	ApplicationContext ctx;
	/**
	 * 创建投保人
	 * 
	 * @param person
	 * @return
	 */
	@RequestMapping(value = "create")
	public Response create(@RequestBody PersonAccountFile person) {
		personAccountRepo.save(person);
		return new Response("操作成功",	true);
	}
	/**
	 * 批量创建投保人
	 * 
	 * @param person
	 * @return
	 */
	@RequestMapping(value = "saveList")
	public Response saveList(@RequestBody List<PersonAccountFile> persons) {
		for (PersonAccountFile person : persons) {
			personAccountRepo.save(person);
		}
		return new Response("操作成功",	true);
	}

	@RequestMapping(value = "getPersonByAccountId")
	public Response getPersonByAccountId(@RequestParam String id) {
		List<PersonAccountFile> list = personAccountRepo.findByAccountId(id);
		return new Response(list);
	}
	@RequestMapping(value = "getInsuredPersonByAccountId")
	public Response getInsuredPersonByAccountId(@RequestParam String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("insuredPerson.accountId").is(id));
		List<PersonAccountFile> list = dmt.find(query, PersonAccountFile.class);
		return new Response(list);
	}
	
	/**
	 * 读取投保人信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "read")
	public PersonAccountFile read(@RequestParam String id) {
		return personAccountRepo.findOne(id);
	}
	
	/**
	 * 读取HR人员档案信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "readPerson")
	public Response readPerson(@RequestParam String id) {
		return new Response(true);
	}

	/**
	 * 更新投保人
	 * 
	 * @param person
	 * @return
	 */
	@RequestMapping(value = "update")
	public Response update(@RequestBody PersonAccountFile person) {
		return new Response(true);
	}


	/**
	 * 查询未投保人员(查询HR人员档案的信息)
	 * @param 搜索条件、分页信息
	 * @return
	 */
	@RequestMapping(value = "loadUninsuredPerson")
	public Response loadUninsuredPerson(@RequestBody Map<String,String> paramMap) {
		return new Response(true);
	}
	
	/**
	 * 查询已投保人员
	 * @param 搜索条件、分页信息
	 * @return
	 */
	@RequestMapping(value = "loadInsuredPerson")
	public Response loadInsuredPerson(@RequestBody Map<String,String> paramMap) {
		return new Response(true);
	}
//	@RequestMapping(value="load")
//	public Map<String, Object> load(@RequestParam Map<String,String> queryBy,@RequestParam int  limit,@RequestParam int  start,@RequestParam int  page, HttpSession session) {
//		return loadSocial(queryBy, limit, start, page, session, false);
//	}
	@RequestMapping(value="load")
	public Map<String, Object> load(@RequestParam Map<String,String> queryBy,@RequestParam int  limit,@RequestParam int  start,@RequestParam int  page, HttpSession session) {
//		List<PersonAccountFile> personAccountFiles=personAccountRepo.findAll();
//		return new Response("操作成功", personAccountFiles, true);
		boolean loadAll = false;
		Map<String, Object> mapResult = new HashMap<String, Object>();
		long count=0;
		page = page - 1;
		if (page != -1) {
			if (page == 0 && limit == 0) {
				limit = 1;
			}
		}

		Sort sort = new Sort(Direction.DESC, "appealTime");
		Pageable pageable = new PageRequest(page, limit ,sort);
		
		Query query = new Query();
		Criteria criteria = Criteria.where("name").exists(true);
		List<Criteria> andCriteria = new ArrayList<Criteria>();
		if (StringUtils.isNotEmpty(queryBy.get("salaryAccount"))) {
			andCriteria.add(Criteria.where("accountId").is(queryBy.get("salaryAccount")));
		}
		if (StringUtils.isNotEmpty(queryBy.get("socialAccount"))) {
			andCriteria.add(Criteria.where("insuredPerson.accountId").is(queryBy.get("socialAccount")));
		}
		if (StringUtils.isNotEmpty(queryBy.get("status"))) {
			andCriteria.add(Criteria.where("insuredPerson.status").is(queryBy.get("status")));
		}
		if (StringUtils.isNotEmpty(queryBy.get("from"))) {
			andCriteria.add(Criteria.where("insuredPerson.insuredDate").gte(queryBy.get("from")));
		}
		if (StringUtils.isNotEmpty(queryBy.get("to"))) {
			andCriteria.add(Criteria.where("insuredPerson.insuredDate").lte(queryBy.get("to")));
		}
		if (StringUtils.isNotEmpty(queryBy.get("insuredPersonExists"))) {
			andCriteria.add(Criteria.where("insuredPerson").exists(true));
		}
		if (StringUtils.isNotEmpty(queryBy.get("loadAll"))) {
			loadAll = true;
		}
		
		if (andCriteria.size() > 0) {
			Criteria[] cs = new Criteria[andCriteria.size()];
			criteria.andOperator(andCriteria.toArray(cs));
		}
		query.addCriteria(criteria);
		
		count = dmt.count(query, PersonAccountFile.class);
		if(!loadAll){
			query.skip(pageable.getOffset());// skip相当于从那条记录开始
			query.limit(pageable.getPageSize());// 从skip开始,取多少条记录
		}
		query.with(sort);
		
		List<PersonAccountFile> list = dmt.find(query, PersonAccountFile.class, "Company_Salary_PersonAccountFile");
		mapResult.put("total", count);
		mapResult.put("data", list);
		return mapResult;
	}
	
	@RequestMapping(value="remove")
	public Response remove(@RequestParam String id) {
		personAccountRepo.delete(id);
		return new Response("操作成功",	true);
	}
	
	@RequestMapping(value="loadSalaryAccount")
	public Response loadSalaryAccount() {
		List<Account> accounts=accountRepo.findAll();
		return new Response("", accounts, true);
	}
	
	@RequestMapping(value="checkExistByPersonCode")
	public boolean checkExistByPersonCode(@RequestParam String personCode) {
		List<PersonAccountFile> list=personAccountRepo.findByPersonCode(personCode);
		if(list!=null && list.size()>0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 导出
	 * 
	 * @return
	 */
	@RequestMapping(value = "/export")
	public @ResponseBody Response export(HttpSession session, HttpServletResponse response,
			HttpServletRequest request) {
		String serverPath;
		try {
			User user = adminService.getUser(session);
			if (user == null) {
				return new Response("用户未登录或已超时", false);
			}

			serverPath = ctx.getResource("/").getFile().getPath();
			String templeteName = "/sion/salary/social/export/personListTemplate.xlsx";
			/////////////////////////////////////////
			Map<String, String> queryBy = new HashMap<String, String>();
			Enumeration<String> paramKeys = request.getParameterNames();
			while (paramKeys.hasMoreElements()) {
				String key = paramKeys.nextElement();
				queryBy.put(key, request.getParameter(key));
			}
			Query query = new Query();
			Criteria criteria = Criteria.where("name").exists(true);
			List<Criteria> andCriteria = new ArrayList<Criteria>();
			if (StringUtils.isNotEmpty(queryBy.get("salaryAccount"))) {
				andCriteria.add(Criteria.where("accountId").is(queryBy.get("salaryAccount")));
			}
			if (StringUtils.isNotEmpty(queryBy.get("socialAccount"))) {
				andCriteria.add(Criteria.where("insuredPerson.accountId").is(queryBy.get("socialAccount")));
			}
			if (StringUtils.isNotEmpty(queryBy.get("status"))) {
				andCriteria.add(Criteria.where("insuredPerson.status").is(queryBy.get("status")));
			}
			if (StringUtils.isNotEmpty(queryBy.get("from"))) {
				andCriteria.add(Criteria.where("insuredPerson.insuredDate").gte(queryBy.get("from")));
			}
			if (StringUtils.isNotEmpty(queryBy.get("to"))) {
				andCriteria.add(Criteria.where("insuredPerson.insuredDate").lte(queryBy.get("to")));
			}
			if (andCriteria.size() > 0) {
				Criteria[] cs = new Criteria[andCriteria.size()];
				criteria.andOperator(andCriteria.toArray(cs));
			}
			query.addCriteria(criteria);

			List<PersonAccountFile> list = dmt.find(query, PersonAccountFile.class, "Company_Salary_PersonAccountFile");
			///////////////////////////////////////

			List<Account> salaryAccounts = dmt.findAll(Account.class, "Company_Salary_Account");
			Map<String, String> salaryMap = new HashMap<>();
			for (Account account : salaryAccounts) {
				salaryMap.put(account.getId(), account.getName());
			}

			List<SocialAccount> socialAccounts = dmt.findAll(SocialAccount.class, "Company_Salary_SocialAccount");
			Map<String, String> socialMap = new HashMap<>();
			for (SocialAccount account : socialAccounts) {
				socialMap.put(account.getId(), account.getName());
			}

			List<Level> levels = dmt.findAll(Level.class, "Company_Salary_Level");
			Map<String, String> levelMap = new HashMap<>();
			for (Level level : levels) {
				levelMap.put(level.getId(), level.getName());
			}

			List<Object> dataList = new ArrayList<Object>();
			Map<String, Object> meta = new HashMap<String, Object>();
			try {
				meta.put("personCode", Class.forName("java.lang.String"));
				meta.put("name", Class.forName("java.lang.String"));
				meta.put("dept", Class.forName("java.lang.String"));
				meta.put("duty", Class.forName("java.lang.String"));
				meta.put("idCard", Class.forName("java.lang.String"));
				meta.put("accountId", Class.forName("java.lang.String"));
				meta.put("level", Class.forName("java.lang.String"));
				meta.put("rank", Class.forName("java.lang.String"));
				meta.put("socialAccountId", Class.forName("java.lang.String"));
				meta.put("status", Class.forName("java.lang.String"));
				meta.put("socialWorkplace", Class.forName("java.lang.String"));
				meta.put("accumulationFundsWorkplace", Class.forName("java.lang.String"));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			for (PersonAccountFile personAccountFile : list) {
				ExportBean export = new ExportBean(meta);
				export.setValue("personCode", personAccountFile.getPersonCode());
				export.setValue("name", personAccountFile.getName());
				export.setValue("dept", personAccountFile.getDept());
				export.setValue("duty", personAccountFile.getDuty());
				export.setValue("idCard", personAccountFile.getIdCard());
				export.setValue("accountId", salaryMap.get(personAccountFile.getAccountId()));
				export.setValue("level", levelMap.get(personAccountFile.getLevel()));
				export.setValue("rank", personAccountFile.getRank());
				export.setValue("socialAccountId", socialMap.get(personAccountFile.getInsuredPerson().getAccountId()));
				export.setValue("status",
						personAccountFile.getInsuredPerson().getStatus().equals(InsuredPerson.InsuredStaus.In) ? "在保"
								: "退保");
				export.setValue("socialWorkplace", personAccountFile.getInsuredPerson().getSocialWorkplace());
				export.setValue("accumulationFundsWorkplace",
						personAccountFile.getInsuredPerson().getAccumulationFundsWorkplace());
				dataList.add(export.getObject());
			}
			if (dataList.size() == 0) {
				dataList.add(new ExportBean());
			}
			String filepath = this.exportExcel(serverPath, templeteName, "员工薪资档案", dataList, response);
			return new Response("操作成功", filepath, true);
		} catch (IOException e) {
			e.printStackTrace();
			return new Response("操作失败", false);
		}
	}

	private String exportExcel(String serverPath, String templeteFile, String exportTitle, List<?> dataList,
			HttpServletResponse response) {
		String templateFile = serverPath + templeteFile;
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateStr = format.format(date);
		String newFilePath = serverPath + "/temp/attend/export";
		File fileFordel = new File(newFilePath);
		if (!fileFordel.exists()) {
			fileFordel.mkdirs();
		}
		newFilePath = newFilePath + "/" + exportTitle + "_" + dateStr + ".xls";
		File eFile = new File(newFilePath);
		if (eFile.exists()) {
			boolean boo = eFile.delete();
		}
		Map<String, List> dataSourceMap = new HashMap<String, List>();
		dataSourceMap.put("data", dataList);
		String filePath = AsposeUtil.createExcelByTemplate(templateFile, newFilePath, dataSourceMap);
		File file = new File(filePath);
		FileUtil.download(file, false, response);
		// AsposeUtil.deleteFile(filePath);
		return filePath;
	}

	public class ExportBean {
		/**
		 * 实体Object
		 */
		public Object object = null;

		/**
		 * 属性map
		 */
		public BeanMap beanMap = null;

		public ExportBean() {
			super();
		}

		@SuppressWarnings("unchecked")
		public ExportBean(Map propertyMap) {
			this.object = generateBean(propertyMap);
			this.beanMap = BeanMap.create(this.object);
		}

		/**
		 * 给bean属性赋值
		 * 
		 * @param property
		 *            属性名
		 * @param value
		 *            值
		 */
		public void setValue(String property, Object value) {
			beanMap.put(property, value);
		}

		/**
		 * 通过属性名得到属性值
		 * 
		 * @param property
		 *            属性名
		 * @return 值
		 */
		public Object getValue(String property) {
			return beanMap.get(property);
		}

		/**
		 * 得到该实体bean对象
		 * 
		 * @return
		 */
		public Object getObject() {
			return this.object;
		}

		@SuppressWarnings("unchecked")
		private Object generateBean(Map propertyMap) {
			BeanGenerator generator = new BeanGenerator();
			Set keySet = propertyMap.keySet();
			for (Iterator i = keySet.iterator(); i.hasNext();) {
				String key = (String) i.next();
				generator.addProperty(key, (Class) propertyMap.get(key));
			}
			return generator.create();
		}
	}
}
