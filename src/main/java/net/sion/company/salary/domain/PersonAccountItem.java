/**
 * PersonAccountItem.java
 */
package net.sion.company.salary.domain;

/**
 * @author zhangligang
 *薪资管理：套帐管理--套帐关联人员工资项目值
 */
public class PersonAccountItem {
String personId;//人员ID
String accountItemId;//套帐项目ID
String accountItemName;//套帐项目名称
double value;//值

public String getPersonId() {
	return personId;
}
public void setPersonId(String personId) {
	this.personId = personId;
}
public String getAccountItemId() {
	return accountItemId;
}
public void setAccountItemId(String accountItemId) {
	this.accountItemId = accountItemId;
}
public String getAccountItemName() {
	return accountItemName;
}
public void setAccountItemName(String accountItemName) {
	this.accountItemName = accountItemName;
}
public double getValue() {
	return value;
}
public void setValue(double value) {
	this.value = value;
}


}
