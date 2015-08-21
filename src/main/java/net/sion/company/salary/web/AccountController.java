/**
 * AccountController.java
 */
package net.sion.company.salary.web;

import java.util.List;

import net.sion.company.salary.domain.Account;
import net.sion.company.salary.domain.PersonAccountItem;
import net.sion.util.mvc.Response;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangligang 薪资管理：套帐设置
 */
@RestController
@RequestMapping("/salary/account/") 
public class AccountController {
	/**
	 * 创建套帐
	 * 
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "create")
	public Response create(@RequestBody Account account) {
		// TODO 调用动态表单的保存方法，保存一条动态表单设置记录用于发放工资时的界面显示
		// TODO 保存套帐信息
		return new Response(true);
	}

	/**
	 * 读取套帐详细信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "read")
	public Response read(@RequestParam String id) {
		return new Response(true);
	}

	/**
	 * 更新套帐
	 * 
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "update")
	public Response update(@RequestBody Account account) {
		// TODO 调用动态表单的保存方法，更新动态表单设置记录
		// TODO 保存套帐信息
		return new Response(true);
	}

	/**
	 * 删除套帐
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "remove")
	public Response remove(@RequestParam String id) {
		return new Response(true);
	}

	/**
	 * 加载套帐列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "load")
	public Response load() {
		return new Response(true);
	}

	/**
	 * 保存套帐成员
	 * 
	 * @param personIds
	 * @return
	 */
	@RequestMapping(value = "savePerson")
	public Response saveAccountPerson(@RequestBody List<String> personIds) {
		// TODO 保存套帐关联的人员ID
		return new Response(true);
	}

	/**
	 * 加载套帐成员的项目设置
	 * @param personIds
	 * @return
	 */
	@RequestMapping(value = "loadPersonConfig")
	public Response loadPersonConfig(@RequestBody List<String> personIds) {
		// TODO 读取人员的薪资项目设置
		return new Response(true);
	}

	/**
	 * 保存套帐成员的项目设置
	 * @param paItem
	 * @return
	 */
	@RequestMapping(value = "createPersonAccountItem")
	public Response createPersonAccountItem(
			@RequestBody PersonAccountItem paItem) {
		return new Response(true);
	}

	/**
	 * 更新套帐成员的项目设置
	 * @param paItem
	 * @return
	 */
	@RequestMapping(value = "updatePersonAccountItem")
	public Response updatePersonAccountItem(
			@RequestBody PersonAccountItem paItem) {
		return new Response(true);
	}

	/**
	 * 删除套帐成员的项目设置
	 * @param paId
	 * @return
	 */
	@RequestMapping(value = "removePersonAccountItem")
	public Response removePersonAccountItem(@RequestBody String paId) {
		return new Response(true);
	}
}
