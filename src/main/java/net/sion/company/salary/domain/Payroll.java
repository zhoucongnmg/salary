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
	
	Map<String,String> persons;	//发放人员 
	
	String date;	//套账创建日期
	
	String processId;	//流程实例id
	
	List<SocialAccountItem> socialAccountsItems;	//社保套账明细项目
	
	@Transient
	List<AccountItem> accountItems;	//薪资项目(用于前台列显示)
	
	
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


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getProcessId() {
		return processId;
	}


	public void setProcessId(String processId) {
		this.processId = processId;
	}


	public List<SocialAccountItem> getSocialAccountsItems() {
		return socialAccountsItems;
	}


	public void setSocialAccountsItems(List<SocialAccountItem> socialAccountsItems) {
		this.socialAccountsItems = socialAccountsItems;
	}


	public List<AccountItem> getAccountItems() {
		return accountItems;
	}


	public void setAccountItems(List<AccountItem> accountItems) {
		this.accountItems = accountItems;
	}
	
	
	
	
	
	
}
