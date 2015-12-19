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
import net.sion.company.salary.domain.AccountItem.AccountItemType;
import net.sion.company.salary.domain.Payroll;
import net.sion.company.salary.domain.PayrollItem;
import net.sion.company.salary.domain.PersonAccountFile;
import net.sion.company.salary.sessionrepository.AccountRepository;
import net.sion.company.salary.sessionrepository.PayrollItemRepository;
import net.sion.company.salary.sessionrepository.PayrollRepository;
import net.sion.company.salary.sessionrepository.PersonAccountFileRepository;
import net.sion.company.salary.sessionrepository.PersonAccountRepository;
import net.sion.util.mvc.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
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
	@Autowired PayrollRepository payrollRepository;
	@Autowired PayrollItemRepository payrollItemRepository;
	
	public List<Map<String,Object>> fillSimpleFields(List<Map<String,Object>> fields) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", "id");
		map.put("type", "string");
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("name", "personId");
		map.put("type", "string");
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("name", "name");
		map.put("type", "string");
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("name", "duty");
		map.put("type", "string");
		fields.add(map);
		map = new HashMap<String,Object>();
		map.put("name", "dept");
		map.put("type", "string");
		fields.add(map);
		return fields;
	}
	
	public List<Map<String,Object>> fillSimpleColumns(List<Map<String,Object>> columns) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("header", "姓名");
		map.put("dataIndex", "name");
		map.put("flex", 1);
		map.put("coltype", "readonly");
		columns.add(map);
		map = new HashMap<String,Object>();
		map.put("header", "职务");
		map.put("dataIndex", "duty");
		map.put("flex", 1);
		map.put("coltype", "readonly");
		columns.add(map);
		map = new HashMap<String,Object>();
		map.put("header", "部门");
		map.put("dataIndex", "dept");
		map.put("flex", 1);
		map.put("coltype", "readonly");
		columns.add(map);
		return columns;
	}
	
	public List<Map<String,Object>> fillData(Map<String,String> persons, List<PayrollItem> items, List<AccountItem> accountItems) {
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		
		Map<String,PayrollItem> payrollItemMap = new HashMap<String,PayrollItem>();
		for (PayrollItem item : items) {
			payrollItemMap.put(item.getPersonId(), item);
		}
		List<String> newPersonIds = new ArrayList<String>();
		for (Map.Entry<String, String> entry : persons.entrySet()) { 
			String personId = entry.getKey();
			if (payrollItemMap.get(personId)!=null) {
				PayrollItem item = payrollItemMap.get(personId);
				Map<String,Object> itemMap = item.parseMap();
				data.add(itemMap);
			}else {
				newPersonIds.add(personId);
			}
			
		}
		if (newPersonIds.size()>0) {
			List<PersonAccountFile> personList = personAccountFileRepository.findByPersonIdIn(newPersonIds);
			for (PersonAccountFile person : personList) {
				Map<String,Object> personMap = new HashMap<String,Object>();
				personMap.put("personId", person.getId());
				personMap.put("name", person.getName());
				personMap.put("duty", person.getDuty());
				personMap.put("dept", person.getDept());
				for(AccountItem item : accountItems){
					personMap.put(item.getSalaryItemId(), item.getType()==AccountItemType.Input?item.getValue():"");
					// TODO 看其他列是否含有公式  有公式将输入项和数值传入 返回其他列和其对应的数值 
				}
				data.add(personMap);
			}
			
			
		}
		
		return data;
	}
	
	
	
	/**
	 * @author lil 工资条列表
	 */
	@RequestMapping(value = "memberList")
	public @ResponseBody Response memberList(@RequestParam String id) {
		
		Payroll payroll = payrollRepository.findOne(id);
		
		Account account = accountRepository.findOne(payroll.getAccountId());
		List<AccountItem> items = account.getAccountItems();
		List<Map<String,Object>> fields = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> columns = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		
		
		
		fillSimpleFields(fields);
		fillSimpleColumns(columns);
		
		for(AccountItem item : items){
			fields.add(getFields(item));
			columns.add(getColumns(item));
		}
		
		List<PayrollItem> payrollItemList = payrollItemRepository.findByPayrollId(id);
		
		data = fillData(payroll.getPersons(),payrollItemList,account.getAccountItems());
		
		Map<String,List<Map<String,Object>>> m = new HashMap<String,List<Map<String,Object>>>();
		m.put("fields", fields);
		m.put("columns", columns);
		m.put("data", data);
		return new Response("sucess", m, true);
	}
	private Map<String,Object> getFields(AccountItem item){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", item.getSalaryItemId());
		map.put("type", "float");
		return map;
	}
	private Map<String,Object> getColumns(AccountItem item){
		Map<String,Object> map = new HashMap<String,Object>();
		switch (item.getType()) {
			case Input :
				Map<String,String> editor = new HashMap<String,String>();
				editor.put("xtype", "numberfield");
				editor.put("name", item.getFieldName());
				map.put("editor", editor);
				map.put("coltype", "input");
				break;
			case Calculate :
				map.put("coltype", "readonly");
			case System : 
				map.put("coltype", "readonly");
		}
			
		map.put("flex", 1);
		map.put("header", item.getName());
		map.put("dataIndex", item.getSalaryItemId());
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
	public Response addPayroll(@RequestBody List<Map<String, Object>> jsonData,HttpSession session) {
		/*
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
		*/
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
