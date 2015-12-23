package net.sion.company.salary.domain;

import org.springframework.data.annotation.Id;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;

/**
 * 社保套账项目（存于社保套账内嵌文档）
 * @author niex
 *
 */

@RooJavaBean
@RooMongoEntity
public class SocialAccountItem {
	String id;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	String socialItemId;	//社保项目
	
	public String getSocialItemId() {
		return socialItemId;
	}

	public void setSocialItemId(String socialItemId) {
		this.socialItemId = socialItemId;
	}

	public String getSocialItemName() {
		return socialItemName;
	}

	public void setSocialItemName(String socialItemName) {
		this.socialItemName = socialItemName;
	}

	public double getCardinality() {
		return cardinality;
	}

	public void setCardinality(double cardinality) {
		this.cardinality = cardinality;
	}

	public double getCompanyPaymentValue() {
		return companyPaymentValue;
	}

	public void setCompanyPaymentValue(double companyPaymentValue) {
		this.companyPaymentValue = companyPaymentValue;
	}

	public double getPersonalPaymentValue() {
		return personalPaymentValue;
	}

	public void setPersonalPaymentValue(double personalPaymentValue) {
		this.personalPaymentValue = personalPaymentValue;
	}

	public PaymentType getCompanyPaymentType() {
		return companyPaymentType;
	}

	public void setCompanyPaymentType(PaymentType companyPaymentType) {
		this.companyPaymentType = companyPaymentType;
	}

	public PaymentType getPersonalPaymentType() {
		return personalPaymentType;
	}

	public void setPersonalPaymentType(PaymentType personalPaymentType) {
		this.personalPaymentType = personalPaymentType;
	}
	public double getCompanyPaymentFinalValue() {
		return companyPaymentFinalValue;
	}

	public void setCompanyPaymentFinalValue(double companyPaymentFinalValue) {
		this.companyPaymentFinalValue = companyPaymentFinalValue;
	}

	public double getPersonalPaymentFinalValue() {
		return personalPaymentFinalValue;
	}

	public void setPersonalPaymentFinalValue(double personalPaymentFinalValue) {
		this.personalPaymentFinalValue = personalPaymentFinalValue;
	}
	String socialItemName;	//社保项目名称
	
	double cardinality;	//基数
	
	double companyPaymentValue;	//单位缴费
	
	double personalPaymentValue;	//个人缴费
	
	double companyPaymentFinalValue;	//单位缴费(用于工资条)
	double personalPaymentFinalValue;	//个人缴费(用于工资条)
	PaymentType companyPaymentType;	//单位缴费类型
	
	PaymentType personalPaymentType;	//个人缴费类型
	
	public enum PaymentType {
		Percent,	//百分比
		Quota		//定额
	}
	
	
	
	
	
}
