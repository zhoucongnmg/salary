package net.sion.company.salary.config;

import org.springframework.beans.factory.annotation.Autowired;

import net.sion.company.salary.domain.SocialItem.SocialItemType;
import net.sion.company.salary.domain.SystemSalaryItemEnum;
import net.sion.company.salary.event.SystemSalaryItemEvent;
import net.sion.company.salary.listener.AbstractSystemSalaryItemListener;
import net.sion.company.salary.service.SocialService;

public class CompanySocialTotalRegister extends AbstractSystemSalaryItemListener{
	@Autowired SocialService socialService;
	@Override
	public Double getValue(SystemSalaryItemEvent event) {
		// TODO Auto-generated method stub
		String personId = event.getPersonId();
		double value = socialService.getSocialSum(personId, false, SocialItemType.SocialSecurity);//单位 社保
		return Double.valueOf(value);
	}
	@Override
	public SystemSalaryItemEnum hook() {
		// TODO Auto-generated method stub
		return SystemSalaryItemEnum.CompanySocialTotal;
	}
}
