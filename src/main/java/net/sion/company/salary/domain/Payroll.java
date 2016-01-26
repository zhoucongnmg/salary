package net.sion.company.salary.domain;

import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;

/**
 * 薪资发放
 * @author niex
 *
 */

@RooJavaBean
@RooMongoEntity
@Document(collection="Company_Salary_Payroll")
public class Payroll {
	
	@Id
	String id;
	
	String subject;	//薪资主题
	
	String month; //薪资月份
	
	String socialCostMonth;	//社保扣费月份
	
	String accountId;	//套账Id
	
	String accountName;  //套帐Name
	
	Map<String,String> persons;	//发放人员 
	
	String createDate;	//套账创建日期
	
//	String processId;	//流程实例id
	
	String createPersonName;//创建人name
	
	String createPersonId;  //创建人id

	String state;//状态
		
	@Transient
	Double sum;
	
	public enum PayrollStatus{
		Unpublish,
		Pass,
		Paid
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getMonth() {
		return month;
	}


	public void setMonth(String month) {
		this.month = month;
	}


	public String getSocialCostMonth() {
		return socialCostMonth;
	}


	public void setSocialCostMonth(String socialCostMonth) {
		this.socialCostMonth = socialCostMonth;
	}


	public String getAccountId() {
		return accountId;
	}


	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}


	public Map<String, String> getPersons() {
		return persons;
	}


	public void setPersons(Map<String, String> persons) {
		this.persons = persons;
	}


	public String getAccountName() {
		return accountName;
	}


	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}


	public String getCreateDate() {
		return createDate;
	}


	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}


	public String getCreatePersonName() {
		return createPersonName;
	}


	public void setCreatePersonName(String createPersonName) {
		this.createPersonName = createPersonName;
	}


	public String getCreatePersonId() {
		return createPersonId;
	}


	public void setCreatePersonId(String createPersonId) {
		this.createPersonId = createPersonId;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public Double getSum() {
		return sum;
	}


	public void setSum(Double sum) {
		this.sum = sum;
	}

	
}
