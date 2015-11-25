/**
 * SalaryItem.java
 */
package net.sion.company.salary.domain;


/**
 * @author niex
 *	公式项
 */
public class FormulaItem {
	
	String value;//公式项数值
	
	String fieldId;//字段
	
	FormulaType type;//项目类型
	
	
	
	
	public enum FormulaType {
		Operation, //运算符(加减乘除)
		Symbols, //符号
		Numberic,	//数字(0-9)
		CalculateItem,	//计算项,
		ResultItem,	//结果项,
		Logical //逻辑运算符（与、或、如果、则、>=、<=、>、<）
		
		
	}
	
	
	public FormulaItem(){}


	public FormulaItem(String value, String fieldId, FormulaType type) {
		super();
		this.value = value;
		this.fieldId = fieldId;
		this.type = type;
	}
	
	
	
}
