/**
 * AccountController.java
 */
package net.sion.company.salary.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sion.boot.config.jackson.CustomJackson;
import net.sion.boot.mongo.template.SessionMongoTemplate;
import net.sion.company.salary.domain.Account;
import net.sion.company.salary.domain.PersonAccountFile;
import net.sion.company.salary.domain.PersonAccountItem;
import net.sion.company.salary.sessionrepository.AccountRepository;
import net.sion.company.salary.sessionrepository.PersonAccountFileRepository;
import net.sion.core.admin.domain.User;
import net.sion.core.admin.service.AdminService;
import net.sion.util.mvc.Response;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * @author zhangligang 薪资管理：套帐设置
 */
@Controller
@RequestMapping("/salary/account/") 
public class AccountController {
	@Autowired AccountRepository accountRepository;
	@Autowired AdminService adminService;
	@Autowired SessionMongoTemplate mongoTemplate; 
	@Autowired CustomJackson jackson;
	@Autowired PersonAccountFileRepository personAccountFileRepository;
	/**
	 * 创建套帐
	 * 
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "create")
	public @ResponseBody Response create(HttpSession session, @RequestBody Account account) {
		// TODO 调用动态表单的保存方法，保存一条动态表单设置记录用于发放工资时的界面显示
		// TODO 保存套帐信息
		if(account.getId() == null || "".equals(account.getId())){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			account.setDate(sdf.format(date));
			account.setId(new ObjectId().toString());
			User user = adminService.getUser(session);
			account.setCreateUserId(user.getId());
			account.setCreateUserName(user.getName());
		}
		accountRepository.save(account);
		return new Response(true);
	}
	

	/**
	 * 删除套帐
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "remove")
	public @ResponseBody Response remove(@RequestParam String id) {
		accountRepository.delete(id);
		return new Response(true);
	}

	/**
	 * 加载套帐列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "load")
	public @ResponseBody Map<String, Object> load(HttpSession session, int page, int start, int limit) {
//		List<Account> list = accountRepository.findAll();
//		return new Response(list);
		Map filter = new HashMap();
		filter.put("page", page);
		filter.put("start", start);
		filter.put("limit", limit);
		DBObject dbobject = new  BasicDBObject();
		Query q = new BasicQuery(dbobject);
		Long total = mongoTemplate.count(q, Account.class);
		Pageable pageable = getPageable(filter, new ArrayList<Order>(), total);
		List<Account> pagelist = accountRepository.getPageData(new HashMap(), pageable); 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("data", pagelist);
		map.put("success", true);
		return map;
	}
	/**
	 * 返回查询分页等信息
	 * @param filter 筛选对象信息
	 * @param listOrder 排序
	 * @return
	 */
	private Pageable getPageable(Map filter,List<Order> listOrder,Long total){
      	int page = Integer.parseInt(filter.get("page").toString()) - 1;
        int limit = Integer.parseInt(filter.get("limit").toString());

        Pageable pageable = null;
        if(page!=-1){
            if(page==0 && limit==0){
            	limit = 1;
            }
        }
        
		listOrder = setListOrder(listOrder, filter);

		if(listOrder.isEmpty()){
			pageable = new PageRequest(page, limit);
		} else {
			Sort sort = new Sort(listOrder);
			pageable = new PageRequest(page, limit,sort);
		}
		return pageable;
	}
	/**
	 * 返回排序集
	 * @param listOrder 排序集
	 * @param filter 筛选对象信息
	 * @return
	 */
	private List<Order> setListOrder(List<Order> listOrder,Map filter){
		
		if(filter.get("sort")!=null){
			List<Map<String, String>> sort;
			try {
				sort = jackson.readValue(filter.get("sort").toString(), new TypeReference<List<Map<String,String>>>(){});
				String property ="";
				String direction ="";
				for(Map<String, String> map:sort){
					if(null!= map.get("property").toString()){
						property = map.get("property");
						direction = map.get("direction");
						if("DESC".equals(direction)){
							listOrder.add(new Order(Direction.DESC,property));
						} else {
							listOrder.add(new Order(Direction.ASC,property));
						}
					}
				}
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return listOrder;
	}

	/**
	 * 保存套帐成员
	 * 
	 * @param personIds
	 * @return
	 */
	@RequestMapping(value = "savePerson")
	public Response saveAccountPerson(@RequestBody List<String> personIds) {
		// TODO 保存套帐关联的人员ID
		return new Response(true);
	}
	
	/**
	 * 查找套帐成员
	 * 
	 * @param personIds
	 * @return
	 */
	@RequestMapping(value = "findAccountPerson")
	public @ResponseBody Map<String, Object> findAccountPerson(@RequestParam String id, int page, int start, int limit) {
		// TODO 保存套帐关联的人员ID
		Map filter = new HashMap();
		filter.put("page", page);
		filter.put("start", start);
		filter.put("limit", limit);
		Map<String, Object> filterMap = new HashMap();
		if(!id.equals("")){
//			Map map = new HashMap();
//			map.put("$regex", name);
//			filterMap.put("accountId", map);
			filterMap.put("accountId", id);
		}
		DBObject dbobject = new  BasicDBObject();
		dbobject.putAll(filterMap);
		Query q = new BasicQuery(dbobject);
		Long total = mongoTemplate.count(q, PersonAccountFile.class);
		Pageable pageable = getPageable(filter, new ArrayList<Order>(), total);
		List<PersonAccountFile> pagelist = personAccountFileRepository.getPageData(filterMap, pageable); 
		Map<String, Object> map = new HashMap<String, Object>();
//		PersonAccountFile person = new PersonAccountFile();
//		person.setId("id001");
//		person.setPersonCode("p_001");
//		person.setName("张三");
//		person.setDuty("经理");
//		person.setAccountId(id);
//		pagelist.add(person);
//		total++;
		
		map.put("total", total);
		map.put("data", pagelist);
		map.put("success", true);
		return map;
	}
	
	@RequestMapping(value = "findAccountItem")
	public Response findAccountItem(@RequestParam String id) {
		// TODO 保存套帐关联的人员ID
		return new Response(true);
	}

	/**
	 * 加载套帐成员的项目设置
	 * @param personIds
	 * @return
	 */
	@RequestMapping(value = "loadPersonConfig")
	public Response loadPersonConfig(@RequestBody List<String> personIds) {
		// TODO 读取人员的薪资项目设置
		return new Response(true);
	}

	/**
	 * 保存套帐成员的项目设置
	 * @param paItem
	 * @return
	 */
	@RequestMapping(value = "createPersonAccountItem")
	public Response createPersonAccountItem(
			@RequestBody PersonAccountItem paItem) {
		return new Response(true);
	}

	/**
	 * 更新套帐成员的项目设置
	 * @param paItem
	 * @return
	 */
	@RequestMapping(value = "updatePersonAccountItem")
	public Response updatePersonAccountItem(
			@RequestBody PersonAccountItem paItem) {
		return new Response(true);
	}

	/**
	 * 删除套帐成员的项目设置
	 * @param paId
	 * @return
	 */
	@RequestMapping(value = "removePersonAccountItem")
	public Response removePersonAccountItem(@RequestBody String paId) {
		return new Response(true);
	}
}
