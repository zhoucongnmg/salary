package net.sion.company.salary.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sion.boot.mongo.template.SessionMongoTemplate;
import net.sion.company.salary.domain.InsuredPerson;
import net.sion.company.salary.domain.Item.ItemType;
import net.sion.company.salary.domain.SocialAccount;
import net.sion.company.salary.domain.SocialItem;
import net.sion.company.salary.domain.SystemSalaryItem;
import net.sion.company.salary.domain.SystemSalaryItem.SystemSalaryItemType;
import net.sion.company.salary.domain.SystemSalaryItemEnum;
import net.sion.company.salary.sessionrepository.SocialItemRepository;
import net.sion.company.salary.sessionrepository.SystemSalaryItemRepository;
import net.sion.util.mvc.Response;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 社保项目
 * @author niex 
 */
@Controller
@RequestMapping("/salary/socialitem/") 
public class SocialItemController {
	@Autowired SocialItemRepository socialItemRepository;
	@Autowired SessionMongoTemplate mongoTemplate; 
	@Autowired SystemSalaryItemRepository systemSalaryItemRepository;
	/**
	 * 创建社保项目
	 * 
	 * @param person
	 * @return
	 */
	@RequestMapping(value = "/create")
	public @ResponseBody Response create(@RequestBody SocialItem item, HttpSession session) {
//		item.setItem(ItemType.SocialItem);
		// TODO 保存投保人信息
		if(item.getId() == null || "".equals(item.getId())){
			item.setId(new ObjectId().toString());
			//将项目添加到系统提取项中
			SystemSalaryItem itemp = new SystemSalaryItem(SystemSalaryItemEnum.AccountItem, item.getName() + "个人部分", item.getId(), SystemSalaryItemType.Personal, item.getCarryType(), item.getPrecision());
			SystemSalaryItem itemc = new SystemSalaryItem(SystemSalaryItemEnum.AccountItem, item.getName() + "单位部分", item.getId(), SystemSalaryItemType.Company, item.getCarryType(), item.getPrecision());
			SystemSalaryItem itempc = new SystemSalaryItem(SystemSalaryItemEnum.AccountItem, item.getName() + "个人基数", item.getId(), SystemSalaryItemType.PersonalCardinality, item.getCarryType(), item.getPrecision());
			SystemSalaryItem itemcc = new SystemSalaryItem(SystemSalaryItemEnum.AccountItem, item.getName() + "单位基数", item.getId(), SystemSalaryItemType.CompanyCardinality, item.getCarryType(), item.getPrecision());
			systemSalaryItemRepository.save(itemp);
			systemSalaryItemRepository.save(itemc);
			systemSalaryItemRepository.save(itempc);
			systemSalaryItemRepository.save(itemcc);
		}else{
			//修改社保项目时，修改系统提取项的名字
			List<SystemSalaryItem> list = systemSalaryItemRepository.findByItemId(item.getId());
			for(SystemSalaryItem systemSalaryItem : list){
				if(SystemSalaryItemType.Personal == systemSalaryItem.getSystemType()){
					systemSalaryItem.setName(item.getName() + "个人部分");
				}else if(SystemSalaryItemType.Company == systemSalaryItem.getSystemType()){
					systemSalaryItem.setName(item.getName() + "单位部分");
				}else if(SystemSalaryItemType.PersonalCardinality == systemSalaryItem.getSystemType()){
					systemSalaryItem.setName(item.getName() + "个人基数");
				}else if(SystemSalaryItemType.CompanyCardinality == systemSalaryItem.getSystemType()){
					systemSalaryItem.setName(item.getName() + "单位基数");
				}
				systemSalaryItem.setCarryType(item.getCarryType());
				systemSalaryItem.setPrecision(item.getPrecision());
				systemSalaryItemRepository.save(systemSalaryItem);
			}
		}
		socialItemRepository.save(item);
		return new Response(true);
	}

	/**
	 * 更新社保项目
	 * 
	 * @param person
	 * @return
	 */
	@RequestMapping(value = "update")
	public Response update(@RequestBody InsuredPerson person) {
		// TODO 保存投保人信息
		return new Response(true);
	}


	/**
	 * 
	 * @param 查询社保项目
	 * @return
	 */
	@RequestMapping(value = "load")
	public @ResponseBody Response load() {
		// TODO 读取未投保人员
//		List<SocialItem> list = socialItemRepository.findAll();
		List<SocialItem> list = socialItemRepository.findByItem(ItemType.SocialItem);
		return new Response(list);
	}
	
	/**
	 * 删除社保项目
	 * @param 社保项id
	 * @return
	 */
	@RequestMapping(value = "remove")
	public @ResponseBody Response remove(@RequestParam String id) {
		// TODO 读取已投保人员
		if(getFromSocialAccount(id) > 0){
			return new Response(false);
		}else{
			socialItemRepository.delete(id);
			return new Response(true);
		}
	}
	private Long getFromSocialAccount(String socialItemId){
		DBObject dbobject = new  BasicDBObject();
		Map<String, Object> filterMap = new HashMap();
		Map map = new HashMap();
		map.put("$regex", socialItemId);
		filterMap.put("socialAccountItems.socialItemId", map);
		dbobject.putAll(filterMap);
		Query q = new BasicQuery(dbobject);
		Long total = mongoTemplate.count(q, SocialAccount.class);
		return total;
	}

}
