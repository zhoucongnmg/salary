/**
 * Tax.java
 */
package net.sion.company.salary.domain;

import java.util.List;

import org.springframework.data.annotation.Id;

/**
 * @author zhangligang
 *薪资管理--个税设置
 */
public class Tax {
	@Id
	String id;
	String name;//名称
	double threshold;//起征点
	List<TaxItem> taxItems;//税率设置
}
