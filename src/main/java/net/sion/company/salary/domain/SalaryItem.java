/**
 * SalaryItem.java
 */
package net.sion.company.salary.domain;

import org.springframework.data.annotation.Id;

/**
 * @author zhangligang
 *	薪资管理：薪资项目
 */
public class SalaryItem {
	@Id
	String id;
	
	String name;//项目名称
	String type;//项目类型
	boolean taxItem;//是否所得税项目
	int decimalScale;//小数位数
	boolean show;//是否显示
	boolean system;//是否系统项
	String note;//备注
}
