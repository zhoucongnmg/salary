package net.sion.company.salary.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sion.company.salary.domain.SocialItem.SocialItemType;
import net.sion.company.salary.domain.SystemSalaryItem.SystemSalaryItemType;
import net.sion.company.salary.domain.SystemSalaryItem;
import net.sion.company.salary.domain.SystemSalaryItemEnum;
import net.sion.company.salary.event.SystemSalaryItemEvent;
import net.sion.company.salary.listener.AbstractSystemSalaryItemListener;
import net.sion.company.salary.service.SocialService;
@Component
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
	@Override
	public List<SystemSalaryItem> regist(List<SystemSalaryItem> empty) {
		// TODO Auto-generated method stub
		SystemSalaryItem item = new SystemSalaryItem(SystemSalaryItemEnum.CompanySocialTotal,"公司社保合计",SystemSalaryItemType.Company);
		empty.add(item);
		return empty;
	}
}
