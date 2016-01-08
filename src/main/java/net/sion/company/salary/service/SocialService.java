package net.sion.company.salary.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sion.company.salary.domain.PersonAccountFile;
import net.sion.company.salary.domain.PersonExtension;
import net.sion.company.salary.domain.SocialAccount;
import net.sion.company.salary.domain.SocialAccountItem;
import net.sion.company.salary.domain.SocialItem.SocialItemType;
import net.sion.company.salary.sessionrepository.PersonAccountFileRepository;
import net.sion.company.salary.sessionrepository.SocialAccountRepository;
import net.sion.company.salary.sessionrepository.SocialItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SocialService {
	@Autowired SocialAccountRepository socialAccountRepository;
	@Autowired SocialItemRepository socialItemRepository;
	@Autowired PersonAccountFileRepository personAccountFileRepository;
	
	public Map<String, PersonExtension<SocialAccountItem>> getSocialAccountByPersons(Set<String> ids){
		Map<String, PersonExtension<SocialAccountItem>> map = new HashMap<String, PersonExtension<SocialAccountItem>>();
		Iterable<PersonAccountFile> personList = personAccountFileRepository.findAll(ids);
		List<String> accountIds = new ArrayList<String>();
		Map<String, List<String>> accountPerson = new HashMap<String, List<String>>();
		for(PersonAccountFile personAccountFile : personList){
			if(personAccountFile.getInsuredPerson() != null && !"".equals(personAccountFile.getInsuredPerson().getAccountId())){
				accountIds.add(personAccountFile.getInsuredPerson().getAccountId());
				List<String> persons = accountPerson.get(personAccountFile.getInsuredPerson().getAccountId());
				if(persons == null){
					persons = new ArrayList<String>();
				}
				persons.add(personAccountFile.getId());
				accountPerson.put(personAccountFile.getInsuredPerson().getAccountId(), persons);
			}
		}
		Iterable<SocialAccount> socialAccountList =  socialAccountRepository.findAll(accountIds);
		for(SocialAccount socialAccount : socialAccountList){
			List<String> personIds = accountPerson.get(socialAccount.getId());
			for(String personId : personIds){
				PersonExtension<SocialAccountItem> personExtension = new PersonExtension<SocialAccountItem>(personId);
				for(SocialAccountItem item : socialAccount.getSocialAccountItems()){
					personExtension.putItem(item.getSocialItemId(), item);
				}
				map.put(personId, personExtension);
			}
		}
		return map;
	}
	/***
	 * 
	 * @param personId 员工id
	 * @param isPerson 是个人还是公司
	 * @param SocialItemType SocialSecurity:社保; AccumulationFunds:公积金
	 * @return
	 */
	public Double getSocialSum(String personId, boolean isPerson, SocialItemType itemType){
		Double pay = 0d;
		PersonAccountFile personAccountFile = personAccountFileRepository.findOne(personId);
		if(personAccountFile.getInsuredPerson() != null && !"".equals(personAccountFile.getInsuredPerson().getAccountId())){
			SocialAccount socialAccount = socialAccountRepository.findOne(personAccountFile.getInsuredPerson().getAccountId());
			if(itemType == SocialItemType.SocialSecurity){
				if(isPerson){
					pay = socialAccount.getSocialPersonSum();
				}else{
					pay = socialAccount.getSocialCompanySum();
				}
			}else{
				if(isPerson){
					pay = socialAccount.getAccumulationPersonSum();
				}else{
					pay = socialAccount.getAccumulationCompanySum();
				}
			}
		}
		return pay;
	}
}
