/**
 * Account.java
 */
package net.sion.company.salary.domain;

import java.util.List;

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
	
	List<AccountItem> accountItems;//套帐项目
}
