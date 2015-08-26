package net.sion.company.salary.web;

import net.sion.company.salary.domain.InsuredPerson;
import net.sion.company.salary.domain.SocialAccount;
import net.sion.util.mvc.Response;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 社保方案
 * @author niex 
 */
@RestController
@RequestMapping("/salary/socialaccount/") 
public class SocialAccountController {
	/**
	 * 创建社保方案
	 * 
	 * @param person
	 * @return
	 */
	@RequestMapping(value = "create")
	public Response create(@RequestBody SocialAccount account) {
		// TODO 保存投保人信息
		return new Response(true);
	}

	/**
	 * 读取社保方案
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "read")
	public Response read(@RequestParam String id) {
		return new Response(true);
	}
	

	/**
	 * 更新社保方案
	 * 
	 * @param person
	 * @return
	 */
	@RequestMapping(value = "update")
	public Response update(@RequestBody SocialAccount account) {
		// TODO 保存投保人信息
		return new Response(true);
	}


	/**
	 * 查询社保方案
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "load")
	public Response load() {
		// TODO 读取未投保人员
		return new Response(true);
	}
	
	/**
	 * 删除社保方案
	 * @param 社保方案id
	 * @return
	 */
	@RequestMapping(value = "remove")
	public Response remove(@RequestParam String id) {
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
