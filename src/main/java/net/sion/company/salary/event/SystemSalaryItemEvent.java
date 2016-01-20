package net.sion.company.salary.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sion.company.salary.domain.SystemSalaryItem;

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
	
	public SystemSalaryItemEvent(Object source) {
		super(source);
		registItems = new ArrayList<SystemSalaryItem>();
	}
	
	public SystemSalaryItemEvent(Object source,SystemSalaryItemEventType type) {
		this(source);
		this.type = type;
	}
			
	
	public SystemSalaryItemEvent(Object source,SystemSalaryItem item, SystemSalaryItemEventType type) {
		this(source,type);
		this.item = item;
	}
	
	public SystemSalaryItemEvent(Object source, SystemSalaryItem item, String userId, SystemSalaryItemEventType type) {
		this(source,item,type);
		this.userId = userId;
	}
	
	public SystemSalaryItemEvent(Object source, SystemSalaryItem item,
			String personId, String deptId, SystemSalaryItemEventType type) {
		this(source,item,type);
		this.personId = personId;
		this.deptId = deptId;
	}
	
	
	public SystemSalaryItemEvent(Object source, SystemSalaryItem item,
			String userId, String personId, String companyId, String deptId,
			Date date, SystemSalaryItemEventType type) {
		this(source);
		this.item = item;
		this.userId = userId;
		this.personId = personId;
		this.companyId = companyId;
		this.deptId = deptId;
		this.date = date;
		this.type = type;
	}


	
	public void addRegistItems(List<SystemSalaryItem> addRegistItems) {
		this.registItems.addAll(addRegistItems);
	}
		
}


