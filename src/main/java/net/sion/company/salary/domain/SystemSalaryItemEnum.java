package net.sion.company.salary.domain;

import net.sion.company.salary.domain.SalaryItem.SalaryItemType;


public enum SystemSalaryItemEnum {

	PersonalSocialTotal (
			new SalaryItem("PersonalSocialTotal","个人社保合计",SalaryItemType.System)
	);
	
	SystemSalaryItemEnum(){
		
	}
	SystemSalaryItemEnum(SalaryItem item){
		this.item = item;
    }
	
	
	private SalaryItem item;
	
	
    public String getId(){
    	return item.getId();
    }
    
    public String getName(){
    	return item.getName();
    }
    
    public SalaryItem getItem(){
    	return item;
    }
    
    
}
