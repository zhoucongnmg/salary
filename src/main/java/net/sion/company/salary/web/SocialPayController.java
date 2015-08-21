package net.sion.company.salary.web;

import net.sion.company.salary.domain.SalaryPay;
import net.sion.util.mvc.Response;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 工资表
 * @author niex 
 */
@RestController
public class SocialPayController {
	/**
	 * 创建工资表
	 * 
	 * @param person
	 * @return
	 */
	@RequestMapping(value = "create")
	public Response create(@RequestBody SalaryPay pay) {
		// TODO 保存工资表信息
		return new Response(true);
	}

	/**
	 * 读取工资表
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "read")
	public Response read(@RequestParam String id) {
		return new Response(true);
	}
	

	/**
	 * 更新工资表
	 * 
	 * @param person
	 * @return
	 */
	@RequestMapping(value = "update")
	public Response update(@RequestBody SalaryPay pay) {
		// TODO 保存工资表信息
		return new Response(true);
	}


	/**
	 * 查询工资表
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "load")
	public Response load() {
		// TODO 读取工资表
		return new Response(true);
	}
	
	/**
	 * 删除工资表
	 * @param 社保套账id
	 * @return
	 */
	@RequestMapping(value = "remove")
	public Response remove(@RequestParam String id) {
		return new Response(true);
	}
	
	
	/**
	 * 查询工资表条目
	 * @param 工资表id
	 * @return 工资表条目明细
	 */
	@RequestMapping(value = "loadSocialAccountItemById")
	public Response loadSocialAccountItemById(@RequestParam String id) {
		// TODO 通过工资表id查找工资表单元格
		return new Response(true);
	}
	
}
