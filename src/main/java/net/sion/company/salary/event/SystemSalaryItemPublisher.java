package net.sion.company.salary.event;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import net.sion.company.salary.domain.SystemSalaryItem;
import net.sion.company.salary.event.SystemSalaryItemEvent.SystemSalaryItemEventType;

@Service
public class SystemSalaryItemPublisher implements ApplicationContextAware {
	

	private ApplicationContext context;

	
	
	public void getValue(SystemSalaryItem item, String personId) {
		SystemSalaryItemEvent e = new SystemSalaryItemEvent(this,item,personId,SystemSalaryItemEventType.GetValue);
		this.context.publishEvent(e);
	}
	
	public void getValue(SystemSalaryItem item, String personId, String deptId) {
		SystemSalaryItemEvent e = new SystemSalaryItemEvent(this,item,personId,deptId,SystemSalaryItemEventType.GetValue);
		this.context.publishEvent(e);
	}
	
	public void getValue(SystemSalaryItem item, String userId, String personId, String companyId, String deptId, Date date) {
		SystemSalaryItemEvent e = new SystemSalaryItemEvent(this,item,userId,personId,companyId,deptId,date,SystemSalaryItemEventType.GetValue);
		this.context.publishEvent(e);
	}
	
	public List<SystemSalaryItem> regist() {
		SystemSalaryItemEvent e = new SystemSalaryItemEvent(this,SystemSalaryItemEventType.Regist);
//		publisher.publishEvent(e);
		this.context.publishEvent(e);
		return e.getRegistItems();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context=applicationContext;
	}
	
}
