/**
 * SalaryItem.java
 */
package net.sion.company.salary.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author niex
 *	公式项
 *	做为内嵌文档来存储
 */
public class FormulaItem {
	private String value;//公式项数值
	private String fieldId;//字段
	private String text;//字段的中文文本
	private FormulaType type;//项目类型
	private String formulaId;//结果项,此项目是另一个计算公式的Id
	
	public enum FormulaType {
//		Operation, //运算符(加减乘除)
//		Symbols, //符号
//		Numeric,	//数字(0-9)
		Calculate,	//计算项,
		Result	//结果项,此项目是另一个计算公式的结果
//		Logical //逻辑运算符（与、或、如果、则、>=、<=、>、<）
		
	}
	
	
	public FormulaItem() {
		
	}
	
	public FormulaItem(String fieldId,String text,FormulaType type) {
		this.fieldId = fieldId;
		this.text = text;
		this.type = type;
	}
	
	public FormulaItem(String fieldId,String text,FormulaType type,String value) {
		this(fieldId,text,type);
		this.value = value;
	}
	
	
	

	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public String getFieldId() {
		return fieldId;
	}


	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}


	public FormulaType getType() {
		return type;
	}


	public void setType(FormulaType type) {
		this.type = type;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}

	public String getFormulaId() {
		return formulaId;
	}

	public void setFormulaId(String formulaId) {
		this.formulaId = formulaId;
	}
	
	
	
}
