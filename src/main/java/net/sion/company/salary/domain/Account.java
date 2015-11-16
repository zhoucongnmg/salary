/**
 * Account.java
 */
package net.sion.company.salary.domain;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;

/**
 * @author zhangligang
 *	薪资管理：套账
 */
public class Account {
	@Id
	String id;
	
	String name;//套帐名称
	
	boolean enableLevel;//是否启用薪资体系
	
	String danyGridBusinessId;//动态表单ID
	
	List<AccountItem> accountItems;//套帐项目
	List<Map<String,String>> persons;//套帐关联人员Id和姓名
}
