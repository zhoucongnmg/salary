package net.sion.company.salary.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * 社保项目
 * 小数保留方式默认为四舍五入方式；如需见分进角，则小数位数选1，保留方式选择直接进位即可
 * @author niex
 *
 */

@RooJavaBean
@RooToString
@RooMongoEntity
//@Document(collection="Company_Salary_SocialItem")
public class SocialItem extends Item{
//	@Id
//	String id;
//	String name;	//项目名称
	SocialItemType itemType;	//社保项目类型
//	DecimalCarryType carryType;	//小数保留方式
//	int precision;	//小数精度
	
	public enum SocialItemType {
		SocialSecurity,		//社保
		AccumulationFunds	//公积金
	}
	
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}

	public SocialItemType getItemType() {
		return itemType;
	}

	public void setItemType(SocialItemType itemType) {
		this.itemType = itemType;
	}

//	public DecimalCarryType getCarryType() {
//		return carryType;
//	}
//
//	public void setCarryType(DecimalCarryType carryType) {
//		this.carryType = carryType;
//	}
//
//	public int getPrecision() {
//		return precision;
//	}
//
//	public void setPrecision(int precision) {
//		this.precision = precision;
//	}
}
