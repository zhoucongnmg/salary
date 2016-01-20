package net.sion.company.salary.domain;


import java.math.BigDecimal;

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
	public Double decimal(DecimalCarryType type, int precision, Double value){
		if(type == DecimalCarryType.Round){
			//四舍五入
			BigDecimal b = new BigDecimal(value);  
			value = b.setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue();  
		}else if(type == DecimalCarryType.Isopsephy){
			//数值进位
			value = Math.ceil(value);
		}else{
			//数值舍位
			value = Math.floor(value);
		}
		return value;
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
