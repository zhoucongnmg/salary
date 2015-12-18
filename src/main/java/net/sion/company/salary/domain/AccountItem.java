/**
 * AccountItem.java
 */
package net.sion.company.salary.domain;

import org.springframework.data.annotation.Transient;

/**
 * @author zhangligang 薪资管理：套帐子项目
 */
public class AccountItem {

	String id;
	
	String sortIndex;// 排序序号
	
	String name;// 项目名称
	
	AccountItemType type;// 项目类型
	
	String fieldName;// 字段名c
	
	String value;// 值或公式
	
	boolean show;// 是否显示
	
	String formulaId; 
	
	@Transient
	Formula formula;
	
	String salaryItemId;// 薪资项目ID
	
	public enum AccountItemType {
		Input,
		Calculate,
		System
	}

	public String getSalaryItemId() {
		return salaryItemId;
	}

	public void setSalaryItemId(String salaryItemId) {
		this.salaryItemId = salaryItemId;
	}

	public String getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(String sortIndex) {
		this.sortIndex = sortIndex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AccountItemType getType() {
		return type;
	}

	public void setType(AccountItemType type) {
		this.type = type;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Formula getFormula() {
		return formula;
	}

	public void setFormula(Formula formula) {
		this.formula = formula;
	}
}
