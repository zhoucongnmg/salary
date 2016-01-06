package net.sion.company.salary.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sion.boot.config.jackson.CustomJackson;
import net.sion.boot.mongo.template.SessionMongoTemplate;
import net.sion.company.salary.domain.SocialAccount;
import net.sion.company.salary.domain.SocialAccountItem;
import net.sion.company.salary.domain.SocialItem;
import net.sion.company.salary.domain.SocialItem.SocialItemType;
import net.sion.company.salary.service.SocialService;
import net.sion.company.salary.sessionrepository.SocialAccountRepository;
import net.sion.company.salary.sessionrepository.SocialItemRepository;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 社保方案
 * @author niex 
 */
@RestController
@RequestMapping("/salary/socialaccount/") 
public class SocialAccountController {
	@Autowired SocialAccountRepository socialAccountRepository;
	@Autowired CustomJackson jackson;
	@Autowired AdminService adminService;
	@Autowired SessionMongoTemplate mongoTemplate; 
	@Autowired SocialItemRepository socialItemRepository;
	@Autowired SocialService socialService;
	/**
	 * 创建社保方案
	 * 
	 * @param person
	 * @return
	 */
	@RequestMapping(value = "create")
	public @ResponseBody Response create(HttpSession session, @RequestBody SocialAccount account) {
		if(account.getId() == null || "".equals(account.getId())){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			account.setDate(sdf.format(date));
			
			account.setId(new ObjectId().toString());
			User user = adminService.getUser(session);
			account.setCreateUserId(user.getId());
			account.setCreateUserName(user.getName());
		}
		account = sum(account);
		
//		Set set = new HashSet();
//		set.add("564d828a464b57c56a844c7f");
//		set.add("564ec925464b57c56a8450d1");
//		set.add("5679ffda9929e8ed6767159f");
//		set.add("564d75e7464bb8da6db35fe4");
//		set.add("567a500599291ff5ac53bdea");
//		set.add("567a500d99291ff5ac53bdeb");
//		Map<String, PersonExtension<SocialAccountItem>> map = socialService.getSocialAccountByPsersons(set);
//		double d1 = socialService.getSocialSum("5679ffda9929e8ed6767159f", true, SocialItemType.SocialSecurity);
//		double d2 = socialService.getSocialSum("5679ffda9929e8ed6767159f", true, SocialItemType.AccumulationFunds);
//		double d3 = socialService.getSocialSum("5679ffda9929e8ed6767159f", false, SocialItemType.SocialSecurity);
//		double d4 = socialService.getSocialSum("5679ffda9929e8ed6767159f", false, SocialItemType.AccumulationFunds);
		
		socialAccountRepository.save(account);
		return new Response(true);
	}
	private SocialAccount sum(SocialAccount account){
		double accumulationCompanySum = 0;//单位缴费公积金
		double accumulationPersonSum = 0;//个人缴费公积金
		double socialCompanySum = 0;//单位缴费社保
		double socialPersonSum = 0;//个人缴费社保
		
		for(SocialAccountItem socialAccountItem : account.getSocialAccountItems()){
			String socialItemId = socialAccountItem.getSocialItemId();
			SocialItem socialItem = socialItemRepository.findOne(socialItemId);
			socialAccountItem.generateDecimalFinalValue(socialItem.getCarryType(), socialItem.getPrecision());
			if(socialItem.getItemType() == SocialItemType.SocialSecurity){
				socialCompanySum += socialAccountItem.getCompanyPaymentFinalValue();
				socialPersonSum += socialAccountItem.getPersonalPaymentFinalValue();
			}else if (socialItem.getItemType() == SocialItemType.AccumulationFunds){
				//公积金
				accumulationCompanySum += socialAccountItem.getCompanyPaymentFinalValue();
				accumulationPersonSum += socialAccountItem.getPersonalPaymentFinalValue();
			}
		}
		account.setAccumulationCompanySum(accumulationCompanySum);
		account.setAccumulationPersonSum(accumulationPersonSum);
		account.setSocialCompanySum(socialCompanySum);
		account.setSocialPersonSum(socialPersonSum);
		return account;
	}
	
	@RequestMapping(value = "loadAll")
	public @ResponseBody Response loadAll(){
		List<SocialAccount> list = socialAccountRepository.findAll();
		return new Response(list);
	}
	/**
	 * 查询社保方案
	 * @param 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "load")
	public @ResponseBody Map<String, Object> load(HttpSession session, int page, int start, int limit, String name, String creater, String startDate, String endDate) throws UnsupportedEncodingException{
//		socialAccountRepository.findAll();
		Map filter = new HashMap();
		filter.put("page", page);
		filter.put("start", start);
		filter.put("limit", limit);
		filter.put("name", name);
		filter.put("creater", creater);
		filter.put("startDate", startDate);
		filter.put("endDate", endDate);
		User user = adminService.getUser(session);
		return find(filter, user, true);
	}
	@SuppressWarnings("unchecked")
	private Map<String, Object> find(Map filter, User user, boolean self) throws UnsupportedEncodingException {
		String name = filter.get("name")==null ? "" : new String(filter.get("name").toString().getBytes("ISO-8859-1"),"UTF-8");
		String creater = filter.get("creater")==null ? "" : new String(filter.get("creater").toString().getBytes("ISO-8859-1"),"UTF-8");
		String startDate = filter.get("startDate") == null ? "" : filter.get("startDate").toString();
		String endDate = filter.get("endDate") == null ? "" : filter.get("endDate").toString();
		
		Map<String, Object> filterMap = new HashMap();
		if(!name.equals("")){
			Map map = new HashMap();
			map.put("$regex", name);
			filterMap.put("name", map);
		}
		if(!creater.equals("")){
			Map map = new HashMap();
			map.put("$regex", creater);
			filterMap.put("createUserName", map);
		}
		Map dateMap = new HashMap();
		if(!startDate.equals("")){
			dateMap.put("$gte", startDate.substring(0, 10));
		}
		if(!endDate.equals("")){
			dateMap.put("$lte", endDate.substring(0, 10));
		}
		if(dateMap.size() > 0){
			filterMap.put("date", dateMap);
		}
		DBObject dbobject = new  BasicDBObject();
		dbobject.putAll(filterMap);
		Query q = new BasicQuery(dbobject);
		Long total = mongoTemplate.count(q, SocialAccount.class);
		Pageable pageable = getPageable(filter,new ArrayList<Order>(),total);
		List<SocialAccount> pagelist = socialAccountRepository.getPageData(filterMap,pageable); 
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
	public Pageable getPageable(Map filter,List<Order> listOrder,Long total){
		
      	int page = Integer.parseInt(filter.get("page").toString())-1;
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
	public List<Order> setListOrder(List<Order> listOrder,Map filter){
		
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
	 * 删除社保方案
	 * @param 社保方案id
	 * @return
	 */
	@RequestMapping(value = "remove")
	public @ResponseBody Response remove(@RequestParam String id) {
		socialAccountRepository.delete(id);
		return new Response(true);
	}
	
	
	/**
	 * 查询社保方案明细
	 * @param 方案id
	 * @return 方案明细
	 */
	@RequestMapping(value = "loadSocialAccountItemById")
	public Response loadSocialAccountItemById(@RequestParam String id) {
		// TODO 通过方案id查询方案明细
		return new Response(true);
	}
	
}
