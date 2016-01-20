package net.sion.company.salary.event;

import java.util.Date;
import java.util.List;

import net.sion.company.salary.domain.SystemSalaryItem;
import net.sion.company.salary.event.SystemSalaryItemEvent.SystemSalaryItemEventType;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

@Service
public class SystemSalaryItemPublisher implements ApplicationEventPublisherAware {
	

	private ApplicationEventPublisher publisher;

	
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		publisher = applicationEventPublisher;
	}
	
	public void getValue(SystemSalaryItem item, String personId) {
		SystemSalaryItemEvent e = new SystemSalaryItemEvent(this,item,personId,SystemSalaryItemEventType.GetValue);
		publisher.publishEvent(e);
	}
	
	public void getValue(SystemSalaryItem item, String personId, String deptId) {
		SystemSalaryItemEvent e = new SystemSalaryItemEvent(this,item,personId,deptId,SystemSalaryItemEventType.GetValue);
		publisher.publishEvent(e);
	}
	
	public void getValue(SystemSalaryItem item, String userId, String personId, String companyId, String deptId, Date date) {
		SystemSalaryItemEvent e = new SystemSalaryItemEvent(this,item,userId,personId,companyId,deptId,date,SystemSalaryItemEventType.GetValue);
		publisher.publishEvent(e);
	}
	
	public List<SystemSalaryItem> regist() {
		SystemSalaryItemEvent e = new SystemSalaryItemEvent(this,SystemSalaryItemEventType.Regist);
		publisher.publishEvent(e);
		return e.getRegistItems();
	}
	
}
