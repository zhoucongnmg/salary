/**
 * SalaryItem.java
 */
package net.sion.company.salary.domain;


/**
 * @author zhangligang 薪资管理：薪资项目
 */
public class SalaryItem extends Item {
	
	SalaryItemType type;// 项目类型
	String note;// 备注
	
	public enum SalaryItemType {
		Input,
		Calculate,
		System,
		Tax
	}
	
	public SalaryItem(String id, String name, SalaryItemType type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}
	
	public SalaryItem(String id, String name,  SalaryItemType type,
//			boolean taxItem, 
			String note) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.note = note;
	}

	public SalaryItem() {
	}


	public SalaryItemType getType() {
		return type;
	}

	public void setType(SalaryItemType type) {
		this.type = type;
	}


	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
