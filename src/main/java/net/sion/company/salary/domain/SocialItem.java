package net.sion.company.salary.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;

/**
 * 社保项目
 * 小数保留方式默认为四舍五入方式；如需见分进角，则小数位数选1，保留方式选择直接进位即可
 * @author niex
 *
 */
@RooJavaBean
@RooMongoEntity
@Document(collection="Company_Salary_SocialItem")
public class SocialItem {
	@Id
	String id;
	
	String name;	//项目名称
	
	SocialItemType itemType;	//社保项目类型
	
	DecimalCarryType carryType;	//小数保留方式
	
	int precision;	//小数精度
	
	
	public enum SocialItemType {
		SocialSecurity,		//社保
		AccumulationFunds	//公积金
	}
	
	public enum DecimalCarryType {
		Round, //四舍五入
		Isopsephy,	//数值进位
		Truncation	//数值舍位
	}
}
