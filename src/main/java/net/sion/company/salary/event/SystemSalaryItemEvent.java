package net.sion.company.salary.event;

import java.util.Date;
import java.util.List;

import net.sion.company.salary.domain.SystemSalaryItem;
import net.sion.company.salary.domain.SystemSalaryItemEnum;

import org.springframework.context.ApplicationEvent;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class SystemSalaryItemEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1L;

	public SystemSalaryItem item;
	
	String userId;
	
	String personId;
	
	String companyId;
	
	String deptId;
	
	Date date;
	
	List<SystemSalaryItem> registItems;
	
	SystemSalaryItemEventType type;
	
	public enum SystemSalaryItemEventType{
		GetValue,
		Regist
	}

	public SystemSalaryItemEvent(Object source,SystemSalaryItemEventType type) {
		super(source);
		this.type = type;
	}
			
	
	public SystemSalaryItemEvent(Object source,SystemSalaryItem item, SystemSalaryItemEventType type) {
		super(source);
		this.item = item;
		this.type = type;
	}
	
	public SystemSalaryItemEvent(Object source, SystemSalaryItem item, String userId, SystemSalaryItemEventType type) {
		this(source,item,type);
		this.userId = userId;
	}
	
	public SystemSalaryItemEvent(Object source, SystemSalaryItem item,
			String personId, String deptId, SystemSalaryItemEventType type) {
		super(source);
		this.item = item;
		this.personId = personId;
		this.deptId = deptId;
		this.type = type;
	}
	
	
	public SystemSalaryItemEvent(Object source, SystemSalaryItem item,
			String userId, String personId, String companyId, String deptId,
			Date date, SystemSalaryItemEventType type) {
		super(source);
		this.item = item;
		this.userId = userId;
		this.personId = personId;
		this.companyId = companyId;
		this.deptId = deptId;
		this.date = date;
		this.type = type;
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

	public SystemSalaryItem getItem() {
		return item;
	}

	public void setItem(SystemSalaryItem item) {
		this.item = item;
	}

	public List<SystemSalaryItem> getRegistItems() {
		return registItems;
	}

	public void setRegistItems(List<SystemSalaryItem> registItems) {
		this.registItems = registItems;
	}
	
	public void addRegistItems(List<SystemSalaryItem> addRegistItems) {
		this.registItems.addAll(addRegistItems);
	}


	public SystemSalaryItemEventType getType() {
		return type;
	}


	public void setType(SystemSalaryItemEventType type) {
		this.type = type;
	}
	
	
	
	
	
}


