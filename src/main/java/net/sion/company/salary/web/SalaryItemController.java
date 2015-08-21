/**
 * SalaryItemController.java
 */
package net.sion.company.salary.web;

import net.sion.company.salary.domain.SalaryItem;
import net.sion.util.mvc.Response;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangligang
 *	薪资管理：薪资项管理
 */
@RestController
@RequestMapping("/salary/salaryitem/") 
public class SalaryItemController {
	@RequestMapping(value="create")
	public Response create(@RequestBody SalaryItem salaryItem) {
		return new Response(true);
	}
	
	@RequestMapping(value="read")
	public Response read(@RequestParam String id) {
		return new Response(true);
	}
	
	@RequestMapping(value = "update")
	public Response update(@RequestBody SalaryItem salaryItem) {
		return new Response(true);
	}
	
	@RequestMapping(value="remove")
	public Response remove(@RequestParam String id) {
		return new Response(true);
	}
	
	@RequestMapping(value="load")
	public Response load() {
		return new Response(true);
	}
}
