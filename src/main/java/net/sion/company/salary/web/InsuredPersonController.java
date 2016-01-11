package net.sion.company.salary.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sion.boot.mongo.template.DynamicMongoTemplate;
import net.sion.company.salary.domain.Account;
import net.sion.company.salary.domain.PersonAccountFile;
import net.sion.company.salary.sessionrepository.AccountRepository;
import net.sion.company.salary.sessionrepository.PersonAccountRepository;
import net.sion.util.mvc.Response;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public Response createList(@RequestBody List<PersonAccountFile> persons) {
		System.out.println(persons.size());
//		if(persons!=null && persons.size()>0){
//			removePersons(persons.get(0).getAccountId());
//		}
		for (PersonAccountFile person : persons) {
			personAccountRepo.save(person);
		}
		return new Response("操作成功",	true);
	}
	/***
	 * add by lil 
	 * 修改薪资方案时，先将方案以前绑定的人员置空
	 */
	private void removePersons(String accountId){
		List<PersonAccountFile> persons = personAccountRepo.findByAccountId(accountId);
		for(PersonAccountFile person : persons){
			person.setAccountId("");
			personAccountRepo.save(person);
		}
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

	@RequestMapping(value="load")
	public Map<String, Object> load(@RequestParam Map<String,String> queryBy,@RequestParam int  limit,@RequestParam int  start,@RequestParam int  page, HttpSession session) {
//		List<PersonAccountFile> personAccountFiles=personAccountRepo.findAll();
//		return new Response("操作成功", personAccountFiles, true);
		
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
		if (andCriteria.size() > 0) {
			Criteria[] cs = new Criteria[andCriteria.size()];
			criteria.andOperator(andCriteria.toArray(cs));
		}
		query.addCriteria(criteria);
		
		count = dmt.count(query, PersonAccountFile.class);
		query.skip(pageable.getOffset());// skip相当于从那条记录开始
		query.limit(pageable.getPageSize());// 从skip开始,取多少条记录
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
}
