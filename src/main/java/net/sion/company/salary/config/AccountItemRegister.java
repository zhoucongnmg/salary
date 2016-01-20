package net.sion.company.salary.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.stereotype.Component;

@Component
public class AccountItemRegister extends AbstractSystemSalaryItemListener{
	@Autowired SocialService socialService;
	@Autowired PersonAccountFileRepository personAccountFileRepository;
	@Autowired SocialAccountRepository socialAccountRepository;
	@Override
	public Double getValue(SystemSalaryItemEvent event) {
		//查询社保方案
		//查询社保项
		PersonAccountFile personAccountFile = personAccountFileRepository.findOne(event.getPersonId());
		List<SocialAccountItem> items = new ArrayList<SocialAccountItem>();
		InsuredPerson InsuredPerson = personAccountFile.getInsuredPerson();
		String accountId = InsuredPerson.getAccountId();
		SocialAccount socialAccount = socialAccountRepository.findOne(accountId);
		Map<String, SocialAccountItem> map = new HashMap<String, SocialAccountItem>();
		List<SocialAccountItem> socialAccountItems = socialAccount.getSocialAccountItems();
		//社保方案中的值
		for(SocialAccountItem item : socialAccountItems){
			map.put(item.getId(), item);
		}
		if(personAccountFile.getInsuredItems() != null && personAccountFile.getInsuredItems().size()>0){
//			items = personAccountFile.getInsuredItems();
			//如果薪资档案中对社保项目设置了值，则以薪资档案的为主
			for(SocialAccountItem item : personAccountFile.getInsuredItems()){
				if(map.get(item.getId()) != null){
					map.put(item.getId(), item);
				}
			}
		}
//		else{
//			items = socialAccount.getSocialAccountItems();
//		}
//		for(SocialAccountItem item : items){
		for (SocialAccountItem item : map.values()) {  
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
