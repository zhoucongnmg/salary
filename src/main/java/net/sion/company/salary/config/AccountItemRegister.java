package net.sion.company.salary.config;

import java.util.List;

import net.sion.company.salary.domain.InsuredPerson;
import net.sion.company.salary.domain.PersonAccountFile;
import net.sion.company.salary.domain.SocialAccount;
import net.sion.company.salary.domain.SocialAccountItem;
import net.sion.company.salary.domain.SystemSalaryItem;
import net.sion.company.salary.domain.SystemSalaryItem.SystemSalaryItemType;
import net.sion.company.salary.domain.SystemSalaryItemEnum;
import net.sion.company.salary.event.SystemSalaryItemEvent;
import net.sion.company.salary.listener.AbstractSystemSalaryItemListener;
import net.sion.company.salary.service.SocialService;
import net.sion.company.salary.sessionrepository.PersonAccountFileRepository;
import net.sion.company.salary.sessionrepository.SocialAccountRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class AccountItemRegister extends AbstractSystemSalaryItemListener{
	@Autowired SocialService socialService;
	@Autowired PersonAccountFileRepository personAccountFileRepository;
	@Autowired SocialAccountRepository socialAccountRepository;
	@Override
	public Double getValue(SystemSalaryItemEvent event) {
		//查询社保方案
		//查询社保项
		PersonAccountFile personAccountFile = personAccountFileRepository.findOne(event.getPersonId());
		InsuredPerson InsuredPerson = personAccountFile.getInsuredPerson();
		String accountId = InsuredPerson.getAccountId();
		SocialAccount socialAccount = socialAccountRepository.findOne(accountId);
		List<SocialAccountItem> items = socialAccount.getSocialAccountItems();
		for(SocialAccountItem item : items){
			if(item.getId().equals(event.getItem().getItemId())){
				if (event.getItem().getSystemType() == SystemSalaryItemType.Company) {
					return item.getCompanyPaymentFinalValue();
				}else if (event.getItem().getSystemType() == SystemSalaryItemType.Personal) {
					return item.getPersonalPaymentFinalValue();
				}
			}
		}
		return Double.valueOf(0);
	}
	@Override
	public SystemSalaryItemEnum hook() {
		// TODO Auto-generated method stub
		return SystemSalaryItemEnum.AccountItem;
	}
	@Override
	public List<SystemSalaryItem> regist(List<SystemSalaryItem> empty) {
		// TODO Auto-generated method stub
		return empty;
	}
}
