/**
 * PersonAccountFile.java
 */
package net.sion.company.salary.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author zhangligang
 *
 */
@Document(collection="Company_Salary_PersonAccountFile")
public class PersonAccountFile {
	@Id
	private String id;
	private String personId;
	private String personCode;//员工编号
	private String name;//员工姓名
	private String dept;//部门
	private String duty;//职务
	private String idCard;//身份证号
	private String bank;//银行
	private String bankAccount;//银行帐号
	private String bankOfDeposit;//开户网点
	private String accountId;//薪资方案
	private String level;//薪资层次
	private String rank;//薪资级别
	private String note;//备注
	private List<PersonAccountItem> accountItems;//工资项目设置
	private InsuredPerson insuredPerson;//社保信息
	private List<SocialAccountItem> insuredItems;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPersonCode() {
		return personCode;
	}
	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getBankOfDeposit() {
		return bankOfDeposit;
	}
	public void setBankOfDeposit(String bankOfDeposit) {
		this.bankOfDeposit = bankOfDeposit;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public List<PersonAccountItem> getAccountItems() {
		return accountItems;
	}
	public void setAccountItems(List<PersonAccountItem> accountItems) {
		this.accountItems = accountItems;
	}
	public InsuredPerson getInsuredPerson() {
		return insuredPerson;
	}
	public void setInsuredPerson(InsuredPerson insuredPerson) {
		this.insuredPerson = insuredPerson;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
		public List<SocialAccountItem> getInsuredItems() {
		return insuredItems;
	}
	public void setInsuredItems(List<SocialAccountItem> insuredItems) {
		this.insuredItems = insuredItems;
	}

}
