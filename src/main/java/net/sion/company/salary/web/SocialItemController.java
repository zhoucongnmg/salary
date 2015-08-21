package net.sion.company.salary.web;

import java.util.Map;

import net.sion.company.salary.domain.InsuredPerson;
import net.sion.company.salary.domain.SocialItem;
import net.sion.util.mvc.Response;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 社保项目
 * @author niex 
 */
@RestController
public class SocialItemController {
	/**
	 * 创建社保项目
	 * 
	 * @param person
	 * @return
	 */
	@RequestMapping(value = "create")
	public Response create(@RequestBody SocialItem item) {
		// TODO 保存投保人信息
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
	public Response load() {
		// TODO 读取未投保人员
		return new Response(true);
	}
	
	/**
	 * 删除社保项目
	 * @param 社保项id
	 * @return
	 */
	@RequestMapping(value = "remove")
	public Response remove(@RequestParam String id) {
		// TODO 读取已投保人员
		return new Response(true);
	}

}
