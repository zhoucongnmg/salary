package net.sion.company.salary.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import net.sion.company.salary.domain.SocialItem.SocialItemType;
import net.sion.company.salary.domain.SystemSalaryItem.SystemSalaryItemType;
import net.sion.company.salary.domain.SystemSalaryItem;
import net.sion.company.salary.domain.SystemSalaryItemEnum;
import net.sion.company.salary.event.SystemSalaryItemEvent;
import net.sion.company.salary.listener.AbstractSystemSalaryItemListener;
import net.sion.company.salary.service.SocialService;

public class PersonalFundsTotalRegister extends AbstractSystemSalaryItemListener{
	@Autowired SocialService socialService;
	@Override
	public Double getValue(SystemSalaryItemEvent event) {
		// TODO Auto-generated method stub
		String personId = event.getPersonId();
		double value = socialService.getSocialSum(personId, true, SocialItemType.AccumulationFunds);//个人 公积金
		return Double.valueOf(value);
	}
	@Override
	public SystemSalaryItemEnum hook() {
		// TODO Auto-generated method stub
		return SystemSalaryItemEnum.PersonalAccumulationFundsTotal;
	}
	@Override
	public List<SystemSalaryItem> regist(List<SystemSalaryItem> empty) {
		// TODO Auto-generated method stub
		SystemSalaryItem item = new SystemSalaryItem(SystemSalaryItemEnum.PersonalAccumulationFundsTotal,"个人公积金合计",SystemSalaryItemType.Personal);
		empty.add(item);
		return empty;
	}
}
