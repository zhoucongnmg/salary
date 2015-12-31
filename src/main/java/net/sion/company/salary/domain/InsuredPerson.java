package net.sion.company.salary.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 投保人
 * @author niex
 *
 */
@Document(collection="Company_Salary_InsuredPerson")
public class InsuredPerson {
	@Id
	String id;
	
	String personId;
	
	String insuredNo;	//社保号
	
	String insuredDate;	//参保日期
	
	String workplace;	//工作地
	
	InsuredStaus status;	//参保状态
	
	String outDate;	//退保日期
	
	String accountId;	//套账Id
	
	String socialWorkplace;	//社保代办地
	
	String accumulationFundsWorkplace;	//公积金代办地
	
	String remark;	//备注
	
	public enum InsuredStaus {
		In, //在保
		Out //退保
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getInsuredNo() {
		return insuredNo;
	}

	public void setInsuredNo(String insuredNo) {
		this.insuredNo = insuredNo;
	}

	public String getInsuredDate() {
		return insuredDate;
	}

	public void setInsuredDate(String insuredDate) {
		this.insuredDate = insuredDate;
	}

	public String getWorkplace() {
		return workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	public InsuredStaus getStatus() {
		return status;
	}

	public void setStatus(InsuredStaus status) {
		this.status = status;
	}

	public String getOutDate() {
		return outDate;
	}

	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getSocialWorkplace() {
		return socialWorkplace;
	}

	public void setSocialWorkplace(String socialWorkplace) {
		this.socialWorkplace = socialWorkplace;
	}

	public String getAccumulationFundsWorkplace() {
		return accumulationFundsWorkplace;
	}

	public void setAccumulationFundsWorkplace(String accumulationFundsWorkplace) {
		this.accumulationFundsWorkplace = accumulationFundsWorkplace;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
