package net.sion.company.salary.domain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


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
public class SocialAccountItem extends SocialItem{
//	String id;
	String socialItemId;	//社保项目
//	String socialItemName;	//社保项目名称
	Double cardinality;	//基数
	Double companyPaymentValue;	//单位缴费
	Double personalPaymentValue;	//个人缴费
	Double companyPaymentFinalValue;	//单位缴费(用于工资条)
	Double personalPaymentFinalValue;	//个人缴费(用于工资条)
	PaymentType companyPaymentType;	//单位缴费类型
	PaymentType personalPaymentType;	//个人缴费类型
//	private SocialAccountItem socialAccountItem;
	
	public enum PaymentType {
		Percent,	//百分比
		Quota		//定额
	}
	public void generateDecimalFinalValue(DecimalCarryType type, int precision){
		switch(this.companyPaymentType) {
			case Percent:
				this.companyPaymentFinalValue = decimal(type, precision, Double.valueOf(cardinality * companyPaymentValue));
				break;
			case Quota:
				this.companyPaymentFinalValue = decimal(type, precision, companyPaymentValue);
				break;
		}
		switch(this.personalPaymentType) {
			case Percent: 
				this.personalPaymentFinalValue = decimal(type, precision, Double.valueOf(cardinality * personalPaymentValue));
				break;
			case Quota:
				this.personalPaymentFinalValue = decimal(type, precision, personalPaymentValue);
				break;
		}
	}
	
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
	public String getSocialItemId() {
		return socialItemId;
	}

	public void setSocialItemId(String socialItemId) {
		this.socialItemId = socialItemId;
	}

//	public String getSocialItemName() {
//		return socialItemName;
//	}
//
//	public void setSocialItemName(String socialItemName) {
//		this.socialItemName = socialItemName;
//	}

	public Double getCardinality() {
		return cardinality;
	}

	public void setCardinality(Double cardinality) {
		this.cardinality = cardinality;
	}

	public Double getCompanyPaymentValue() {
		return companyPaymentValue;
	}

	public void setCompanyPaymentValue(Double companyPaymentValue) {
		this.companyPaymentValue = companyPaymentValue;
	}

	public Double getPersonalPaymentValue() {
		return personalPaymentValue;
	}

	public void setPersonalPaymentValue(Double personalPaymentValue) {
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
	public Double getCompanyPaymentFinalValue() {
		return companyPaymentFinalValue;
	}

	public void setCompanyPaymentFinalValue(Double companyPaymentFinalValue) {
		this.companyPaymentFinalValue = companyPaymentFinalValue;
	}

	public Double getPersonalPaymentFinalValue() {
		return personalPaymentFinalValue;
	}

	public void setPersonalPaymentFinalValue(Double personalPaymentFinalValue) {
		this.personalPaymentFinalValue = personalPaymentFinalValue;
	}
}
