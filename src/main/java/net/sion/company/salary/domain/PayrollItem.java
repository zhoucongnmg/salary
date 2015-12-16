package net.sion.company.salary.domain;



import java.util.List;
import java.util.Map;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;

/**
 * 工资表条目(用于前台显示)
 * @author niex
 *
 */

@RooJavaBean
@RooMongoEntity
public class PayrollItem {
	
	
	String personId;	//人事档案id
	
	String name;	//姓名
	
	String duty;	//职务
	
	String dept;	//部门
	
	List<Map<String,String>> values;	//薪资明细项
	
	
}
