/**
 * SalaryItem.java
 */
package net.sion.company.salary.domain;

import net.sion.company.salary.domain.Item.DecimalCarryType;

import org.bson.types.ObjectId;


/**
 * @author niex 系统提取项
 */
public class SystemSalaryItem extends SalaryItem {
	
	String itemId;	//系统提取项对应的itemId
	
	SystemSalaryItemEnum category;
	
	SystemSalaryItemType systemType;
	
	public enum SystemSalaryItemType {
		Personal,
		Dept,
		Company,
		Group
	}
	
	public SystemSalaryItem() {
		super.id = new ObjectId().toString();
		
	}
	
	public SystemSalaryItem(SystemSalaryItemEnum category,String name,SystemSalaryItemType systemType) {
		super.id = new ObjectId().toString();
		super.name = name;
		super.type = SalaryItemType.System;
		this.category = category;
		this.systemType = systemType;
		this.item = ItemType.SalaryItem;
		
		super.carryType = DecimalCarryType.Round;//暂时将系统提取项的小数保留方式设为四舍五入
	}
	
	public SystemSalaryItem(SystemSalaryItemEnum category, String name,String itemId,SystemSalaryItemType systemType) {
		this(category,name,systemType);
		this.itemId = itemId;
	}
	
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public SystemSalaryItemEnum getCategory() {
		return category;
	}

	public void setCategory(SystemSalaryItemEnum category) {
		this.category = category;
	}

	public SystemSalaryItemType getSystemType() {
		return systemType;
	}

	public void setSystemType(SystemSalaryItemType systemType) {
		this.systemType = systemType;
	}
	
	
	
	
}
