package net.sion.company.salary.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sion.boot.config.jackson.CustomJackson;
import net.sion.company.salary.domain.Account;
import net.sion.company.salary.domain.AccountItem;
import net.sion.company.salary.domain.Payroll;
import net.sion.company.salary.domain.PayrollItem;
import net.sion.company.salary.domain.PersonAccountFile;
import net.sion.company.salary.sessionrepository.AccountRepository;
import net.sion.company.salary.sessionrepository.PayrollItemRepository;
import net.sion.company.salary.sessionrepository.PersonAccountFileRepository;
import net.sion.company.salary.sessionrepository.PersonAccountRepository;
import net.sion.util.mvc.Response;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * 工资表
 * @author niex 
 */
@RestController
@RequestMapping("/salary/payroll/") 
public class PayrollController {
	@Autowired AccountRepository accountRepository;
	@Autowired PersonAccountFileRepository personAccountFileRepository;
	@Autowired PersonAccountRepository personAccountRepository;
	@Autowired CustomJackson jackson;
	@Autowired PayrollItemRepository payrollItemRepository;
	
	/**
	 * @author lil 工资条列表
	 */
	@RequestMapping(value = "memberList")
	public @ResponseBody Response memberList(HttpSession session,String accountId) {
//		List<Account> list = accountRepository.findAll();
//		Account account = list.get(0);
		Account account = accountRepository.findOne(accountId);
		List<AccountItem> items = account.getAccountItems();
		List<Map> fields = new ArrayList();
		List<Map> columns = new ArrayList();
		List<Map> data = new ArrayList();
		
		Map map = new HashMap();
		map.put("name", "id");
		map.put("type", "String");
		fields.add(map);
		map = new HashMap();
		map.put("name", "personId");
		map.put("type", "String");
		fields.add(map);
		map = new HashMap();
		map.put("name", "name");
		map.put("type", "String");
		fields.add(map);
		map = new HashMap();
		map.put("name", "duty");
		map.put("type", "String");
		fields.add(map);
		map = new HashMap();
		map.put("name", "dept");
		map.put("type", "String");
		fields.add(map);
		
		map = new HashMap();
		map.put("header", "姓名");
		map.put("dataIndex", "name");
		map.put("flex", 1);
		columns.add(map);
		map = new HashMap();
		map.put("header", "职务");
		map.put("dataIndex", "duty");
		map.put("flex", 1);
		columns.add(map);
		map = new HashMap();
		map.put("header", "部门");
		map.put("dataIndex", "dept");
		map.put("flex", 1);
		columns.add(map);
		
		List<AccountItem> inputItems = new ArrayList();
		for(AccountItem item : items){
			fields.add(getFields(item));
			columns.add(getColumns(item));
			if("输入项".equals(item.getType())){
				inputItems.add(item);
			}
		}
		List<PersonAccountFile> members = personAccountRepository.findByAccountId(account.getId());
		for(PersonAccountFile person : members){
			map = new HashMap();
//			map.put("id", new ObjectId().toString());
			map.put("personId", person.getId());
			map.put("name", person.getName());
			map.put("duty", person.getDuty());
			map.put("dept", person.getDept());
			for(AccountItem item : inputItems){
				map.put(item.getSalaryItemId(), item.getValue());
			}
			data.add(map);
		}
//		map = new HashMap();
//		map.put("name", "c");
//		map.put("type", "String");
//		fields.add(map);
		
//		map = new HashMap();
//		map.put("header", "性别");
//		map.put("dataIndex", "c");
//		columns.add(map);
		
//		map = new HashMap();
//		map.put("a", "赵四");
//		map.put("b", "1984年");
//		map.put("c", "女");
//		data.add(map);
		Map m = new HashMap();
		m.put("fields", fields);
		m.put("columns", columns);
		m.put("data", data);
		return new Response("sucess", m, true);
	}
	private Map getFields(AccountItem item){
		Map map = new HashMap();
		map.put("name", item.getFieldName());
		if("输入项".equals(item.getType())){
			map.put("type", "float");
		}else{
			map.put("type", "String");
		}
		return map;
	}
	private Map getColumns(AccountItem item){
		Map map = new HashMap();
		if("输入项".equals(item.getType())){
			Map editor = new HashMap();
			editor.put("xtype", "numberfield");
			editor.put("name", item.getFieldName());
			map.put("editor", editor);
		}
		map.put("flex", 1);
		map.put("header", item.getName());
		map.put("dataIndex", item.getFieldName());
		return map;
	}
	/**
	 * 新建工资条
	 * 
	 * @param person
	 * @return
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping(value = "addPayroll")
	public Response addPayroll(@RequestBody Map<String, Object> jsonData,HttpSession session) throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
		List<Map<String, String>> roll = jackson.readValue(jackson.writeValueAsString(jsonData.get("roll")), new TypeReference<List<Map<String, String>>>(){});
		String accountId = jackson.readValue(jackson.writeValueAsString(jsonData.get("accountId")), new TypeReference<String>(){});
		for(Map<String, String> map : roll){
			PayrollItem item = null;
			if (!StringUtils.isEmpty(map.get("id"))) {
				item = payrollItemRepository.findOne(map.get("id"));
			}else{
				item = new PayrollItem();
				item.setId(new ObjectId().toString());
			}
			List<Map<String,String>> values = new ArrayList();
			//遍历map中的键  
			for (String key : map.keySet()) {
				if("personId".equals(key)){
					item.setPersonId(map.get(key));
				}else if("name".equals(key)){
					item.setName(map.get(key));
				}else if("duty".equals(key)){
					item.setDuty(map.get(key));
				}else if("dept".equals(key)){
					item.setDept(map.get(key));
				}else if("id".equals(key)){
					
				}else {
					Map<String,String> accountItem = new HashMap();
					accountItem.put(key, map.get(key));
					values.add(accountItem);
				}
			}
			item.setValues(values);
			item.setAccountId(accountId);
			payrollItemRepository.save(item);
		}
		return new Response(true);
	}
	/**
	 * 修改工资条
	 * 
	 * @param person
	 * @return
	 */
	@RequestMapping(value = "updatePayroll")
	public Response updatePayroll(@RequestParam Map data) {
		return new Response(true);
	}
	
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
