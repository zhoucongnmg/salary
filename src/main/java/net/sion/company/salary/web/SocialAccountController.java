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
import net.sion.company.salary.domain.SocialAccountItem.PaymentType;
import net.sion.company.salary.domain.SocialItem;
import net.sion.company.salary.domain.SocialItem.DecimalCarryType;
import net.sion.company.salary.domain.SocialItem.SocialItemType;
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
			if(socialItem.getItemType() == SocialItemType.SocialSecurity){
				//社保
				sumItem(socialAccountItem);
				Map<String, Double> map = sumItem(socialAccountItem);
				socialCompanySum += decimal(socialItem.getCarryType(), socialItem.getPrecision(), map.get("company").floatValue());
				socialPersonSum += decimal(socialItem.getCarryType(), socialItem.getPrecision(), map.get("person").floatValue());
			}else{
				//公积金
				Map<String, Double> map = sumItem(socialAccountItem);
				accumulationCompanySum += decimal(socialItem.getCarryType(), socialItem.getPrecision(), map.get("company").floatValue());
				accumulationPersonSum += decimal(socialItem.getCarryType(), socialItem.getPrecision(), map.get("person").floatValue());
			}
		}
		account.setAccumulationCompanySum(accumulationCompanySum);
		account.setAccumulationPersonSum(accumulationPersonSum);
		account.setSocialCompanySum(socialCompanySum);
		account.setSocialPersonSum(socialPersonSum);
		return account;
	}
	private Map<String, Double> sumItem(SocialAccountItem socialAccountItem){
		Map<String, Double> map = new HashMap();
		double cardinality = socialAccountItem.getCardinality();
		//公司
		if(socialAccountItem.getCompanyPaymentType() == PaymentType.Percent){
			//百分比
			map.put("company", Double.valueOf(cardinality * socialAccountItem.getCompanyPaymentValue() * 0.01));	
		}else{
			//定额
			map.put("company", Double.valueOf(socialAccountItem.getCompanyPaymentValue()));
		}
		//个人
		if(socialAccountItem.getPersonalPaymentType() == PaymentType.Percent){
			map.put("person", Double.valueOf(cardinality * socialAccountItem.getPersonalPaymentValue() * 0.01));
		}else{
			map.put("person", Double.valueOf(socialAccountItem.getPersonalPaymentValue()));
		}
		return map;
	}
	private double decimal(DecimalCarryType type, int precision, double value){
		if(type == DecimalCarryType.Round){
			//四舍五入
			
		}else if(type == DecimalCarryType.Isopsephy){
			//数值进位
			
		}else{
			//数值舍位
			
		}
		return value;
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
