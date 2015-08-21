package net.sion.company.salary.web;

import net.sion.company.salary.domain.InsuredPerson;
import net.sion.company.salary.domain.SocialAccount;
import net.sion.util.mvc.Response;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 社保套账
 * @author niex 
 */
@RestController
public class SocialAccountController {
	/**
	 * 创建社保套账
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
	 * 读取社保套账
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "read")
	public Response read(@RequestParam String id) {
		return new Response(true);
	}
	

	/**
	 * 更新社保套账
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
	 * 查询社保套账
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "load")
	public Response load() {
		// TODO 读取未投保人员
		return new Response(true);
	}
	
	/**
	 * 删除社保套账
	 * @param 社保套账id
	 * @return
	 */
	@RequestMapping(value = "remove")
	public Response remove(@RequestParam String id) {
		return new Response(true);
	}
	
	
	/**
	 * 查询社保套账明细
	 * @param 套账id
	 * @return 套账明细
	 */
	@RequestMapping(value = "loadSocialAccountItemById")
	public Response loadSocialAccountItemById(@RequestParam String id) {
		// TODO 通过套账id查询套账明细
		return new Response(true);
	}
	
}
