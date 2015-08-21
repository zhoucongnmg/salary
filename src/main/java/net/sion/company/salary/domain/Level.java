/**
 * Level.java
 */
package net.sion.company.salary.domain;

import java.util.List;

import org.springframework.data.annotation.Id;

/**
 * @author zhangligang
 *	薪资管理--薪资层次
 */
public class Level {
	@Id
	String id;
	
	String name;//层次名称
	List<LevelItem> levelItems;//层次项目
}
