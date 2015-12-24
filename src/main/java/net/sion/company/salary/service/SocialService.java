package net.sion.company.salary.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sion.boot.mongo.template.SessionMongoTemplate;
import net.sion.company.salary.domain.PersonAccountFile;
import net.sion.company.salary.domain.PersonExtension;
import net.sion.company.salary.domain.SocialAccount;
import net.sion.company.salary.domain.SocialAccountItem;
import net.sion.company.salary.sessionrepository.PersonAccountFileRepository;
import net.sion.company.salary.sessionrepository.SocialAccountRepository;
import net.sion.company.salary.sessionrepository.SocialItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class SocialService {
	@Autowired SocialAccountRepository socialAccountRepository;
	@Autowired SocialItemRepository socialItemRepository;
	@Autowired SessionMongoTemplate mongoTemplate; 
	@Autowired PersonAccountFileRepository personAccountFileRepository;
	
	public Map<String, PersonExtension> getSocialAccountByPsersons(Set<String> ids){
		Map<String, PersonExtension> map = new HashMap<String, PersonExtension>();
		Query query = new Query(Criteria.where("id").in(ids));
		List<PersonAccountFile> personList = mongoTemplate.find(query, PersonAccountFile.class);
		List<String> accountIds = new ArrayList<String>();
		Map<String, List<String>> accountPerson = new HashMap<String, List<String>>();
		for(PersonAccountFile personAccountFile : personList){
			if(personAccountFile.getInsuredPerson() != null && !"".equals(personAccountFile.getInsuredPerson().getAccountId())){
				accountIds.add(personAccountFile.getInsuredPerson().getAccountId());
				if(accountPerson.get(personAccountFile.getInsuredPerson().getAccountId()) != null){
					List<String> persons = accountPerson.get(personAccountFile.getInsuredPerson().getAccountId());
					persons.add(personAccountFile.getId());
					accountPerson.put(personAccountFile.getInsuredPerson().getAccountId(), persons);
				}else{
					List<String> persons = new ArrayList<String>();
					persons.add(personAccountFile.getId());
					accountPerson.put(personAccountFile.getInsuredPerson().getAccountId(), persons);
				}
			}
		}
		List<SocialAccount> socialAccountList = mongoTemplate.find(new Query(Criteria.where("id").in(accountIds)), SocialAccount.class);
		for(SocialAccount socialAccount : socialAccountList){
			List<String> personIds = accountPerson.get(socialAccount.getId());
			for(String personId : personIds){
				PersonExtension personExtension = new PersonExtension(personId);
				Map<String, SocialAccountItem> items = new HashMap<String, SocialAccountItem>();
				for(SocialAccountItem item : socialAccount.getSocialAccountItems()){
					items.put(item.getSocialItemId(), item);
				}
				personExtension.setItems(items);
				map.put(personId, personExtension);
			}
		}
		return map;
	}
}
