package net.sion.company.salary.listener;


import net.sion.company.salary.domain.SystemSalaryItemEnum;
import net.sion.company.salary.event.SystemSalaryItemEvent;

import org.springframework.context.ApplicationListener;

public abstract class AbstractSystemSalaryItemListener implements ApplicationListener<SystemSalaryItemEvent>{

	
	@Override
	public void onApplicationEvent(SystemSalaryItemEvent event) {
		if (event.getItemType() == hook()) {
			this.getValue(event);
		}
	}
	
	public abstract void getValue(SystemSalaryItemEvent event);

	public abstract SystemSalaryItemEnum hook();
}