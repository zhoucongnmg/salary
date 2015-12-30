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
	String name;	//套账名称
	String date;	//套账创建日期
	String createUserId;//
	String createUserName;
	List<SocialAccountItem> socialAccountItems;	//社保套账明细项目
	double accumulationCompanySum;//单位缴费公积金总额
	double accumulationPersonSum;//个人缴费公积金总额
	double socialCompanySum;//单位缴费社保总额
	double socialPersonSum;//个人缴费社保总额
	
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
	public double getAccumulationCompanySum() {
		return accumulationCompanySum;
	}
	public void setAccumulationCompanySum(double accumulationCompanySum) {
		this.accumulationCompanySum = accumulationCompanySum;
	}
	public double getAccumulationPersonSum() {
		return accumulationPersonSum;
	}
	public void setAccumulationPersonSum(double accumulationPersonSum) {
		this.accumulationPersonSum = accumulationPersonSum;
	}
	public double getSocialCompanySum() {
		return socialCompanySum;
	}
	public void setSocialCompanySum(double socialCompanySum) {
		this.socialCompanySum = socialCompanySum;
	}
	public double getSocialPersonSum() {
		return socialPersonSum;
	}
	public void setSocialPersonSum(double socialPersonSum) {
		this.socialPersonSum = socialPersonSum;
	}
}
