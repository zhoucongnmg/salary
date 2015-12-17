package net.sion.company.salary.domain;



import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;

/**
 * 工资表条目(用于前台显示)
 * @author niex
 *
 */

@RooJavaBean
@RooMongoEntity
public class PayrollItem {
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public List<Map<String, String>> getValues() {
		return values;
	}

	public void setValues(List<Map<String, String>> values) {
		this.values = values;
	}
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	@Id
	String id;
	String accountId;//方案id
	String personId;	//人事档案id
	
	String name;	//姓名
	
	String duty;	//职务
	
	String dept;	//部门
	
	List<Map<String,String>> values;	//薪资明细项
	
	
}
