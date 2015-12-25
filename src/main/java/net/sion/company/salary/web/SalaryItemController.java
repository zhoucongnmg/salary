/**
 * SalaryItemController.java
 */
package net.sion.company.salary.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sion.boot.mongo.template.SessionMongoTemplate;
import net.sion.company.salary.domain.SalaryItem;
import net.sion.company.salary.sessionrepository.SalaryItemRepository;
import net.sion.util.mvc.Response;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhangligang
 *	薪资管理：薪资项管理
 */
@Controller
@RequestMapping("/salary/salaryitem/") 
public class SalaryItemController {
	@Autowired SalaryItemRepository salaryItemRepository;
	@Autowired SessionMongoTemplate mongoTemplate; 
	/*
	private List<SalaryItem> getSalaryItemSystem(){
		List<SalaryItem> list = new ArrayList();
		for (SalaryItemSystem s : SalaryItemSystem.values()) {  
			list.add(s.getSalaryItem());
        }  
		return list;
	}
*/
	@RequestMapping(value = "create")
	public @ResponseBody Response create(@RequestBody SalaryItem item, HttpSession session) {
		if(item.getId() == null || "".equals(item.getId())){
			item.setId(new ObjectId().toString());
		}
		salaryItemRepository.save(item);
		return new Response(true);
	}
	
	@RequestMapping(value = "update")
	public @ResponseBody  Response update(@RequestBody SalaryItem salaryItem) {
		salaryItemRepository.save(salaryItem);
		return new Response(true);
	}
	
	@RequestMapping(value="remove")
	public @ResponseBody  Response remove(@RequestParam String id) {
		salaryItemRepository.delete(id);
		return new Response(true);
	}
	
	@RequestMapping(value="load")
	public @ResponseBody Response load(@RequestParam String system, @RequestParam String type) throws UnsupportedEncodingException {
		List<SalaryItem> list = new ArrayList();
		Query query = new Query();
		if("false".equals(system)){
			query.addCriteria(Criteria.where("system").is(false));
			type = new String(type.getBytes("ISO-8859-1"),"utf-8");
			if(!"".equals(type)){
				query.addCriteria(Criteria.where("type").is(type));
			}
//			list = mongoTemplate.find(query, SalaryItem.class);
		}else if("true".equals(system)){
			query.addCriteria(Criteria.where("system").is(true));
//			list = mongoTemplate.find(query, SalaryItem.class);
		}
//		else{
//			list = salaryItemRepository.findAll();
//			list.addAll(getSalaryItemSystem());
//		}
		list = mongoTemplate.find(query, SalaryItem.class);
		return new Response(list);
	}
}
