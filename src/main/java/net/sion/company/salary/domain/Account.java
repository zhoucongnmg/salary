/**
 * Account.java
 */
package net.sion.company.salary.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sion.company.salary.domain.AccountItem.AccountItemType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;

/**
 * @author zhangligang 薪资管理：套账
 */
@RooJavaBean
@RooMongoEntity
@Document(collection = "Company_Salary_Account")
public class Account {
	@Id
	String id;

	String name;// 套帐名称

	boolean enableLevel;// 是否启用薪资体系

	String danyGridBusinessId;// 动态表单ID

	List<AccountItem> accountItems;// 套帐项目
	// List<Map<String,String>> persons;//套帐关联人员Id和姓名

	String createUserId;//
	String createUserName;
	String date; // 套账创建日期
	String remark; // 备注
	@Transient
	boolean updatePayroll;//修改时是否更新工资条
	public boolean isUpdatePayroll() {
		return updatePayroll;
	}
	public void setUpdatePayroll(boolean updatePayroll) {
		this.updatePayroll = updatePayroll;
	}

	public Set<String> getFormulaIds() {
		Set<String> formulaIds = new HashSet<String>();
		for (AccountItem item : accountItems) {
			if (item.getType() == AccountItemType.Calculate&&StringUtils.isNotBlank(item.getFormulaId())) {
				formulaIds.add(item.getFormulaId());
			}
		}
		
		return formulaIds;
	}
	
	public Map<String,String> getSalaryItemValues() {
		Map<String,String> salaryItemValues = new HashMap<String,String>();
		for (AccountItem item : accountItems) {
			if (item.getType() == AccountItemType.Input) {
				salaryItemValues.put(item.getSalaryItemId(),item.getValue());
			}
		}
		
		return salaryItemValues;
	}
	
	
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

	public boolean isEnableLevel() {
		return enableLevel;
	}

	public void setEnableLevel(boolean enableLevel) {
		this.enableLevel = enableLevel;
	}

	public String getDanyGridBusinessId() {
		return danyGridBusinessId;
	}

	public void setDanyGridBusinessId(String danyGridBusinessId) {
		this.danyGridBusinessId = danyGridBusinessId;
	}

	public List<AccountItem> getAccountItems() {
		return accountItems;
	}

	public void setAccountItems(List<AccountItem> accountItems) {
		this.accountItems = accountItems;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
