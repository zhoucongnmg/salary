package net.sion.company.salary.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection="Company_Salary_Item")
public class Item {
	@Id
	String id;
	String name;
	DecimalCarryType carryType;	//小数保留方式
	int precision;	//小数精度
	ItemType item;
	public enum DecimalCarryType {
		Round, //四舍五入
		Isopsephy,	//数值进位
		Truncation	//数值舍位
	}
	public enum ItemType {
		SalaryItem, //薪资项目
		SocialItem	//社保项目
	}
	public ItemType getItem() {
		return item;
	}

	public void setItem(ItemType item) {
		this.item = item;
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
	public DecimalCarryType getCarryType() {
		return carryType;
	}
	
	public void setCarryType(DecimalCarryType carryType) {
		this.carryType = carryType;
	}
	
	public int getPrecision() {
		return precision;
	}
	
	public void setPrecision(int precision) {
		this.precision = precision;
	}
}
