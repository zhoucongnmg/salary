package net.sion.company.salary.web;

import java.util.Map;

import net.sion.company.salary.domain.InsuredPerson;
import net.sion.company.salary.domain.PersonAccountItem;
import net.sion.util.mvc.Response;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 社保投保
 * @author niex 
 */
@RestController
public class InsuredPersonController {
	/**
	 * 创建投保人
	 * 
	 * @param person
	 * @return
	 */
	@RequestMapping(value = "create")
	public Response create(@RequestBody InsuredPerson person) {
		// TODO 保存投保人信息
		return new Response(true);
	}

	/**
	 * 读取投保人信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "read")
	public Response read(@RequestParam String id) {
		return new Response(true);
	}
	
	/**
	 * 读取HR人员档案信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "readPerson")
	public Response readPerson(@RequestParam String id) {
		return new Response(true);
	}

	/**
	 * 更新投保人
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
	 * 查询未投保人员(查询HR人员档案的信息)
	 * @param 搜索条件、分页信息
	 * @return
	 */
	@RequestMapping(value = "loadUninsuredPerson")
	public Response loadUninsuredPerson(@RequestBody Map<String,String> paramMap) {
		// TODO 读取未投保人员
		return new Response(true);
	}
	
	/**
	 * 查询已投保人员
	 * @param 搜索条件、分页信息
	 * @return
	 */
	@RequestMapping(value = "loadInsuredPerson")
	public Response loadInsuredPerson(@RequestBody Map<String,String> paramMap) {
		// TODO 读取已投保人员
		return new Response(true);
	}

}
