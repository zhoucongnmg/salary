package net.sion.company.salary.event;

import java.util.Date;

import net.sion.company.salary.domain.SystemSalaryItemEnum;
import net.sion.core.admin.domain.Company;

import org.springframework.context.ApplicationEvent;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class SystemSalaryItemEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1L;

	public SystemSalaryItemEnum itemType;
	
	String userId;
	
	String personId;
	
	String companyId;
	
	String deptId;
	
	Date date;
	
			
	
	public SystemSalaryItemEvent(Object source,SystemSalaryItemEnum itemType) {
		super(source);
		this.itemType = itemType;
	}
	
	public SystemSalaryItemEvent(Object source, SystemSalaryItemEnum itemType, String userId) {
		this(source,itemType);
		this.userId = userId;
	}
	
	public SystemSalaryItemEvent(Object source, SystemSalaryItemEnum itemType,
			String personId, String deptId) {
		super(source);
		this.itemType = itemType;
		this.personId = personId;
		this.deptId = deptId;
	}
	
	
	public SystemSalaryItemEvent(Object source, SystemSalaryItemEnum itemType,
			String userId, String personId, String companyId, String deptId,
			Date date) {
		super(source);
		this.itemType = itemType;
		this.userId = userId;
		this.personId = personId;
		this.companyId = companyId;
		this.deptId = deptId;
		this.date = date;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public SystemSalaryItemEnum getItemType() {
		return itemType;
	}

	public void setItemType(SystemSalaryItemEnum itemType) {
		this.itemType = itemType;
	}

	
	
}


