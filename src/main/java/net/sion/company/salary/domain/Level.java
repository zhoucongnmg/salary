/**
 * Level.java
 */
package net.sion.company.salary.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sion.company.salary.domain.SalaryItem.SalaryItemType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author zhangligang
 *	薪资管理--薪资层次
 */
@Document(collection="Company_Salary_Level")
public class Level {
	@Id
	String id;
	
	String name;//层次名称
	Map<String,String> salaryItemNames;//项目ID与项目名称
	List<LevelItem> levelItems;//层次项目
	
	public boolean existSalaryItemId(String id){
		boolean value = false;
		for (String key : salaryItemNames.keySet()) {
			if(key.equals(id)){
				value = true;
				break;
			}
		}
		return value;
	} 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<LevelItem> getLevelItems() {
		return levelItems;
	}
	public void setLevelItems(List<LevelItem> levelItems) {
		this.levelItems = levelItems;
	}
	public Map<String, String> getSalaryItemNames() {
		return salaryItemNames;
	}
	public void setSalaryItemNames(Map<String, String> salaryItemNames) {
		this.salaryItemNames = salaryItemNames;
	}
}
