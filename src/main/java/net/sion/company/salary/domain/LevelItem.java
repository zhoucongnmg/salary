/**
 * LevelItem.java
 */
package net.sion.company.salary.domain;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;

/**
 * @author zhangligang
 *薪资管理--薪资层次--项目与值设置
 */
public class LevelItem {
	@Id
	String id;
	String rank;//级别
	List<Map<String,String>> salaryItemNames;//项目ID与项目名称
	List<Map<String, Double>> salaryItemValues;//项目ID与值
	List<Map<String,String>> employees;//人员ID集
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public List<Map<String, String>> getSalaryItemNames() {
		return salaryItemNames;
	}
	public void setSalaryItemNames(List<Map<String, String>> salaryItemNames) {
		this.salaryItemNames = salaryItemNames;
	}
	public List<Map<String, Double>> getSalaryItemValues() {
		return salaryItemValues;
	}
	public void setSalaryItemValues(List<Map<String, Double>> salaryItemValues) {
		this.salaryItemValues = salaryItemValues;
	}
	public List<Map<String, String>> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Map<String, String>> employees) {
		this.employees = employees;
	}
	
	
}
