package net.sion.company.salary.domain;


import java.math.BigDecimal;
import java.text.DecimalFormat;

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
		Isopsephy,	//直接进位
		Truncation	//直接舍去
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
//			value = Math.ceil(value);
			BigDecimal b = new BigDecimal(value);  
			value = b.setScale(precision, BigDecimal.ROUND_UP).doubleValue();  
		}else{
			//数值舍位
//			value = Math.floor(value);
			BigDecimal b = new BigDecimal(value);  
			value = b.setScale(precision, BigDecimal.ROUND_DOWN).doubleValue();  
		}
		return value;
	}	
	
	public static void main(String[] args) {
		String formatStr = "0";
		
		int scale = 2;
		for (int i=0;i<scale;i++) {
			if (i==0) formatStr = formatStr+".";
			formatStr = formatStr+"0";
		}
		DecimalFormat    df   = new DecimalFormat(formatStr);
		
		System.out.println(df.format(1374.0));
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
