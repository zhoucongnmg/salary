package net.sion.company.salary.domain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import net.sion.company.salary.domain.SocialItem.DecimalCarryType;

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
	String socialItemId;	//社保项目
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
	public void generateDecimalFinalValue(DecimalCarryType type, int precision){
		switch(this.companyPaymentType) {
			case Percent:
//				this.companyPaymentValue = companyPaymentValue * 0.01;
				this.companyPaymentFinalValue = decimal(type, precision, Double.valueOf(cardinality * companyPaymentValue));
				break;
			case Quota:
				this.companyPaymentFinalValue = decimal(type, precision, companyPaymentValue);
				break;
		}
		switch(this.personalPaymentType) {
			case Percent: 
//				this.personalPaymentValue = personalPaymentValue * 0.01;
				this.personalPaymentFinalValue = decimal(type, precision, Double.valueOf(cardinality * personalPaymentValue));
				break;
			case Quota:
				this.personalPaymentFinalValue = decimal(type, precision, personalPaymentValue);
				break;
		}
	}
	private double decimal(DecimalCarryType type, int precision, double value){
		if(type == DecimalCarryType.Round){
			//四舍五入
			BigDecimal b = new BigDecimal(value);  
			value = b.setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue();  
		}else if(type == DecimalCarryType.Isopsephy){
			//数值进位
			value = Math.ceil(value);
		}else{
			//数值舍位
			value = Math.floor(value);
		}
		return value;
	}
	private SocialAccountItem socialAccountItem;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
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
}
