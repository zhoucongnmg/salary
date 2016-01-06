/**
 * SalaryItem.java
 */
package net.sion.company.salary.domain;


/**
 * @author zhangligang 薪资管理：薪资项目
 */
//@Document(collection="Company_Salary_SalaryItem")
public class SalaryItem extends Item {
//	@Id
//	String id;
//	String name;// 项目名称
//	String field;// 字段
	SalaryItemType type;// 项目类型
	boolean taxItem;// 是否所得税项目
//	int decimalScale;// 小数位数
//	boolean show;// 是否显示
//	boolean system;// 是否系统项
	String note;// 备注
	
	public enum SalaryItemType {
		Input,
		Calculate,
		System
	}
	
	public SalaryItem(String id, String name, SalaryItemType type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}
	
	public SalaryItem(String id, String name,  SalaryItemType type,
			boolean taxItem, 
			String note) {
		this.id = id;
		this.name = name;
//		this.field = field;
		this.type = type;
		this.taxItem = taxItem;
//		this.decimalScale = decimalScale;
//		this.show = show;
//		this.system = system;
		this.note = note;
	}

	public SalaryItem() {
	}

//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}

//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}

//	public String getField() {
//		return field;
//	}
//
//	public void setField(String field) {
//		this.field = field;
//	}

	public SalaryItemType getType() {
		return type;
	}

	public void setType(SalaryItemType type) {
		this.type = type;
	}

	public boolean isTaxItem() {
		return taxItem;
	}

	public void setTaxItem(boolean taxItem) {
		this.taxItem = taxItem;
	}

//	public int getDecimalScale() {
//		return decimalScale;
//	}
//
//	public void setDecimalScale(int decimalScale) {
//		this.decimalScale = decimalScale;
//	}

//	public boolean isShow() {
//		return show;
//	}
//
//	public void setShow(boolean show) {
//		this.show = show;
//	}

//	public boolean isSystem() {
//		return system;
//	}
//
//	public void setSystem(boolean system) {
//		this.system = system;
//	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
