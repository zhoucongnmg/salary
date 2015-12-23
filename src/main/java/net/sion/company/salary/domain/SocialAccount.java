package net.sion.company.salary.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;

/**
 * 社保套账
 * @author niex
 *
 */

@RooJavaBean
@RooMongoEntity
@Document(collection="Company_Salary_SocialAccount")
public class SocialAccount {
	
	@Id
	String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public List<SocialAccountItem> getSocialAccountItems() {
		return socialAccountItems;
	}
	public void setSocialAccountItems(List<SocialAccountItem> socialAccountItems) {
		this.socialAccountItems = socialAccountItems;
	}
	public float getAccumulationSum() {
		return accumulationSum;
	}
	public void setAccumulationSum(float accumulationSum) {
		this.accumulationSum = accumulationSum;
	}
	public float getSocialSum() {
		return socialSum;
	}
	public void setSocialSum(float socialSum) {
		this.socialSum = socialSum;
	}
	
	String name;	//套账名称
	
	String date;	//套账创建日期
	String createUserId;//
	String createUserName;
	List<SocialAccountItem> socialAccountItems;	//社保套账明细项目
	float accumulationSum;//公积金总额
	float socialSum;//社保总额
	
}
