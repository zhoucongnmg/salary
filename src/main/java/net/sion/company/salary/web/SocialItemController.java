package net.sion.company.salary.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sion.boot.mongo.template.SessionMongoTemplate;
import net.sion.company.salary.domain.InsuredPerson;
import net.sion.company.salary.domain.SocialAccount;
import net.sion.company.salary.domain.SocialItem;
import net.sion.company.salary.sessionrepository.SocialItemRepository;
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
import org.springframework.web.bind.annotation.RestController;

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
	/**
	 * 创建社保项目
	 * 
	 * @param person
	 * @return
	 */
	@RequestMapping(value = "/create")
	public @ResponseBody Response create(@RequestBody SocialItem item, HttpSession session) {
		// TODO 保存投保人信息
		if(item.getId() == null || "".equals(item.getId())){
			item.setId(new ObjectId().toString());
		}
		socialItemRepository.save(item);
		return new Response(true);
	}

	/**
	 * 读取社保项目
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "read")
	public Response read(@RequestParam String id) {
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
		List<SocialItem> list = socialItemRepository.findAll();
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
