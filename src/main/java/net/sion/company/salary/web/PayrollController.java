package net.sion.company.salary.web;

import net.sion.company.salary.domain.Payroll;
import net.sion.company.salary.domain.PayrollItem;
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
@RequestMapping("/salary/payroll/") 
public class PayrollController {
	/**
	 * 创建工资表
	 * 
	 * @param person
	 * @return
	 */
	@RequestMapping(value = "create")
	public Response create(@RequestBody Payroll pay) {
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
	public Response update(@RequestBody Payroll pay) {
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
	@RequestMapping(value = "loadPayrollItemById")
	public Response loadPayrollItemById(@RequestParam String id) {
		// TODO 通过工资表id查找工资表单元格项
		
		// TODO 将工资表单元格项拼成PayrollItem
		
		// TODO 调用SalaryService.computeSalary查询其他初始化值并计算出各列结果
		
		
		
		return new Response(true);
	}
	
	/**
	 * 更新工资表条目
	 * @param 工资表id
	 * @return 
	 */
	@RequestMapping(value = "updatePayrollCell")
	public Response updatePayrollItem(@RequestBody PayrollItem item, String activityItemId) {
		// TODO 调用SalaryService.computeSalary更新与之关联的其他工资表项目
		
		
		return new Response(true);
	}
	
	/**
	 * 工作流相关-保存草稿
	 * @param 工资表id
	 * @return 
	 */
	@RequestMapping(value = "saveDraft")
	public Response saveDraft(@RequestParam Payroll pay) {
		return new Response(true);
	}
	
	/**
	 * 工作流相关-流转
	 * @param 工资表id
	 * @return 
	 */
	@RequestMapping(value = "next")
	public Response next(@RequestParam Payroll pay) {
		return new Response(true);
	}
	
	/**
	 * 工作流相关-撤回
	 * @param 工资表id
	 * @return 
	 */
	@RequestMapping(value = "withdraw")
	public Response withdraw(@RequestParam Payroll pay) {
		return new Response(true);
	}
	
	
}
