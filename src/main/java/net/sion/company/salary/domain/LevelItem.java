/**
 * LevelItem.java
 */
package net.sion.company.salary.domain;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;

/**
 * @author zhangligang
 *薪资管理--薪资层次--项目与值设置
 */
public class LevelItem {
	@Id
	String id;
	String rank;//级别
	List<Map<String,String>> salaryItemNames;//项目ID与项目名称
	List<Map<String, Double>> salaryItemValues;//项目ID与值
	List<Map<String,String>> employees;//人员ID集
}
