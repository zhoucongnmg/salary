package net.sion.company.salary.listener;


import java.util.ArrayList;
import java.util.List;

import net.sion.company.salary.domain.SystemSalaryItem;
import net.sion.company.salary.domain.SystemSalaryItemEnum;
import net.sion.company.salary.event.SystemSalaryItemEvent;

import org.springframework.context.ApplicationListener;

public abstract class AbstractSystemSalaryItemListener implements ApplicationListener<SystemSalaryItemEvent>{

	
	@Override
	public void onApplicationEvent(SystemSalaryItemEvent event) {
		switch(event.getType()) {
			case GetValue:
				if (event.getItem().getCategory() == hook()) {
					Double value = this.getValue(event);
					event.setValue(value);
				}
				break;
			case Regist : 
				List<SystemSalaryItem> addSystemSalaryItem = this.regist(new ArrayList<SystemSalaryItem>());
				event.addRegistItems(addSystemSalaryItem);
				break;
		}
		
		
	}
	
	public abstract Double getValue(SystemSalaryItemEvent event);
	
	public abstract List<SystemSalaryItem> regist(List<SystemSalaryItem> empty);

	public abstract SystemSalaryItemEnum hook();
}