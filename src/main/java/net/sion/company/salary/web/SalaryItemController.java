/**
 * SalaryItemController.java
 */
package net.sion.company.salary.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.sion.boot.mongo.template.SessionMongoTemplate;
import net.sion.company.salary.domain.Account;
import net.sion.company.salary.domain.Item.ItemType;
import net.sion.company.salary.domain.Level;
import net.sion.company.salary.domain.SalaryItem;
import net.sion.company.salary.sessionrepository.LevelRepository;
import net.sion.company.salary.sessionrepository.SalaryItemRepository;
import net.sion.util.mvc.Response;

import org.apache.commons.lang3.StringUtils;
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
	@Autowired LevelRepository levelRepository;
	/*
	private List<SalaryItem> getSalaryItemSystem(){
		List<SalaryItem> list = new ArrayList();
		for (SalaryItemSystem s : SalaryItemSystem.values()) {  
			list.add(s.getSalaryItem());
        }  
		return list;
	}
*/
	//判断薪资方案中是否有该薪资项目
	public boolean existSalaryItemAccount(@RequestBody String id){
		Query query = new Query(Criteria.where("accountItems.salaryItemId").is(id));
		List <Account> pagelist = mongoTemplate.find(query, Account.class);
		return pagelist.size() > 0 ? true : false;
	}
	//判断薪资层次中是否有该薪资项目
	public boolean existSalaryItemLevel(@RequestBody String id){
		boolean value = false;
		List<Level> list = levelRepository.findAll();
		for(Level level : list){
			if(level.existSalaryItemId(id)){
				value = true;
				break;
			}
		}
		return value;
	}
	@RequestMapping(value = "create")
	public @ResponseBody Response create(@RequestBody SalaryItem item, HttpSession session) {
//		item.setItem(ItemType.SalaryItem);
		if(item.getId() == null || "".equals(item.getId())){
			item.setId(new ObjectId().toString());
		}
		salaryItemRepository.save(item);
		return new Response(true);
	}
	
	@RequestMapping(value = "update")
	public @ResponseBody  Response update(@RequestBody SalaryItem salaryItem) {
		salaryItem.setItem(ItemType.SalaryItem);
		salaryItemRepository.save(salaryItem);
		return new Response(true);
	}
	
	@RequestMapping(value="remove")
	public @ResponseBody  Response remove(@RequestParam String id) {
		boolean existAccount = existSalaryItemAccount(id);
		if(existAccount){
			return new Response("薪资方案中使用了该项目，不可删除", false);
		}
		boolean existLevel = existSalaryItemLevel(id);
		if(existLevel){
			return new Response("薪资层次中使用了该项目，不可删除", false);
		}
		salaryItemRepository.delete(id);
		return new Response(true);
	}
	
	@RequestMapping(value="load")
	public @ResponseBody Response load(@RequestParam String type) throws UnsupportedEncodingException {
		Query query = new Query();
		if(StringUtils.isNotEmpty(type)){
			query.addCriteria(Criteria.where("type").is(type));
		}
		query.addCriteria(Criteria.where("item").is(ItemType.SalaryItem));
		List<SalaryItem> list = mongoTemplate.find(query, SalaryItem.class);
		return new Response(list);
	}
}
