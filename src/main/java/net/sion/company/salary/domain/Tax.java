/**
 * Tax.java
 */
package net.sion.company.salary.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;

/**
 * @author zhangligang
 *薪资管理--个税设置
 */
@RooJavaBean
@RooMongoEntity
@Document(collection="Company_Salary_Tax")
public class Tax {
	@Id
	String id;
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
	public double getThreshold() {
		return threshold;
	}
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	public List<TaxItem> getTaxItems() {
		return taxItems;
	}
	public void setTaxItems(List<TaxItem> taxItems) {
		this.taxItems = taxItems;
	}
	
	String name;//名称
	double threshold;//起征点
	List<TaxItem> taxItems;//税率设置
}
