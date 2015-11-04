/**
 * Level.java
 */
package net.sion.company.salary.domain;

import java.util.List;

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
	List<LevelItem> levelItems;//层次项目
	
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
}
