/**
 * AccountItem.java
 */
package net.sion.company.salary.domain;

import org.springframework.data.annotation.Transient;

/**
 * @author zhangligang 薪资管理：套帐子项目
 */
public class AccountItem extends SalaryItem {

//	String id;
	String sortIndex;// 排序序号
//	String name;// 项目名称
//	AccountItemType type;// 项目类型
//	String fieldName;// 字段名c
	String value;// 值或公式
	boolean show;// 是否显示
	String formulaId; 
	@Transient
	Formula formula;
	String salaryItemId;// 薪资项目ID
	
	String taxId;//个税方案id
	String parentId;//按该方案项目计税
	
//	public enum AccountItemType {
//		Input,
//		Calculate,
//		System
//	}
	public boolean existSalaryItemId(){
		
		return false;
	}
	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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

//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}

//	public AccountItemType getType() {
//		return type;
//	}
//
//	public void setType(AccountItemType type) {
//		this.type = type;
//	}

//	public String getFieldName() {
//		return fieldName;
//	}
//
//	public void setFieldName(String fieldName) {
//		this.fieldName = fieldName;
//	}

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

//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}

	public Formula getFormula() {
		return formula;
	}

	public void setFormula(Formula formula) {
		this.formula = formula;
	}

	public String getFormulaId() {
		return formulaId;
	}

	public void setFormulaId(String formulaId) {
		this.formulaId = formulaId;
	}
}
