package net.sion.company.salary.event;

import java.util.Date;

import net.sion.company.salary.domain.SystemSalaryItemEnum;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

@Service
public class SystemSalaryItemPublisher implements ApplicationEventPublisherAware {
	

	private static ApplicationEventPublisher publisher;

	
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		publisher = applicationEventPublisher;
	}
	
	public void getValue(SystemSalaryItemEvent e) {
		
		publisher.publishEvent(e);
	}
	
	public void getValues(String userId, String personId, String companyId, String deptId,
			Date date,String itemId) {
		SystemSalaryItemEvent e = new SystemSalaryItemEvent(this,SystemSalaryItemEnum.valueOf(itemId),userId,personId,companyId,deptId,date);
		publisher.publishEvent(e);
	}
	
}
