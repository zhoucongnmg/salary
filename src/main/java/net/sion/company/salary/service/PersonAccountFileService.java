package net.sion.company.salary.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sion.company.salary.domain.Account;
import net.sion.company.salary.domain.AccountItem;
import net.sion.company.salary.domain.Level;
import net.sion.company.salary.domain.LevelItem;
import net.sion.company.salary.domain.PersonAccountFile;
import net.sion.company.salary.domain.PersonAccountFile.ItemSetting;
import net.sion.company.salary.domain.SalaryItem.SalaryItemType;
import net.sion.company.salary.domain.PersonAccountItem;
import net.sion.company.salary.sessionrepository.AccountRepository;
import net.sion.company.salary.sessionrepository.LevelRepository;
import net.sion.company.salary.sessionrepository.PersonAccountFileRepository;

@Service
public class PersonAccountFileService {
	@Autowired
	private PersonAccountFileRepository pafRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private LevelRepository levelRepository;

	/**
	 * 输入一个人员档案，返回所有输入项的键值对
	 * 
	 * @param paf
	 * @return
	 */
	public Map<String, Double> getItemsValue(String id) {
		Map<String, Double> result = new HashMap<>();
		Map<String, Double> accountMap = new HashMap<>();
		Map<String, Double> levelMap = new HashMap<>();

		PersonAccountFile person = pafRepository.findOne(id);
		if (person.getAccountId() != null) {
			Account account = accountRepository.findOne(person.getAccountId());
			List<AccountItem> items = account.getAccountItems();
			for (AccountItem accountItem : items) {
				if (SalaryItemType.Input.equals(accountItem.getType())) {
					accountMap.put(accountItem.getId(), new Double(accountItem.getValue()));
				}
			}
		}
		if (person.getLevel() != null && person.getRank() != null) {
			Level level = levelRepository.findOne(person.getLevel());
			for (LevelItem levelItem : level.getLevelItems()) {
				if (person.getRank() != null && person.getRank().equals(levelItem.getRank())) {
					levelMap = levelItem.getSalaryItemValues();
				}
			}
		}

		List<PersonAccountItem> accountItems = person.getAccountItems();
		for (PersonAccountItem pai : accountItems) {
			ItemSetting setting = person.getAccountItemsSetting().get(pai.getAccountItemId());
			if (ItemSetting.Person.equals(setting)) {
				result.put(pai.getAccountItemId(), pai.getValue());
			} else if (ItemSetting.Level.equals(setting)) {
				result.put(pai.getAccountItemId(), levelMap.get(pai.getAccountItemId()));
			} else {
				result.put(pai.getAccountItemId(), accountMap.get(pai.getAccountItemId()));
			}
		}
		return result;
	}

	/**
	 * 输入一个人员档案，返回指定输入项的键值对
	 * 
	 * @param personId
	 * @param itemId
	 * @return
	 */
	public Double getOneItemValue(String id, String itemId) {
		Double result = new Double(0);
		Double accountValue = new Double(0);
		Double levelValue = new Double(0);
		PersonAccountFile person = pafRepository.findOne(id);
		
		List<PersonAccountItem> accountItems = person.getAccountItems();
		for (PersonAccountItem pai : accountItems) {
			if (pai.getAccountItemId().equals(itemId)) {
				ItemSetting setting = person.getAccountItemsSetting().get(pai.getAccountItemId());
				if (ItemSetting.Person.equals(setting)) {
					result = pai.getValue();
				} else if (ItemSetting.Level.equals(setting)) {
					if (person.getLevel() != null && person.getRank() != null) {
						Level level = levelRepository.findOne(person.getLevel());
						if (level != null) {
							for (LevelItem levelItem : level.getLevelItems()) {
								if (person.getRank() != null && person.getRank().equals(levelItem.getRank())) {
									levelValue = levelItem.getSalaryItemValues().get(itemId);
									break;
								}
							}
						}
					}
					result = levelValue;
				} else {
					if (person.getAccountId() != null) {
						Account account = accountRepository.findOne(person.getAccountId());
						if (account != null) {
							List<AccountItem> items = account.getAccountItems();
							for (AccountItem accountItem : items) {
								if (accountItem.getSalaryItemId().equals(itemId)) {
									accountValue = new Double(accountItem.getValue());
									break;
								}
							}
						}
					}
					result = accountValue;
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 更新指定薪资方案的所有档案
	 * @param salaryAccountId
	 */
	public void updateSalaryItems(String salaryAccountId){
		Account account = accountRepository.findOne(salaryAccountId);
		List<AccountItem> accountItems=account.getAccountItems();
		List<PersonAccountFile> persons = pafRepository.findByAccountId(salaryAccountId);
		for (PersonAccountFile personAccountFile : persons) {
			
			List<PersonAccountItem> pAccountItems=personAccountFile.getAccountItems();
			boolean exist=false;
			for (PersonAccountItem pAccountItem : pAccountItems) {
				for (AccountItem item : accountItems) {
					if(item.getId().equals(pAccountItem.getAccountItemId())){
						exist=true;
						break;
					}
				}
				
				if(exist==false){
					pAccountItems.remove(pAccountItem);
					personAccountFile.getAccountItemsSetting().remove(pAccountItem.getAccountItemId());
				}
				else{
					exist=false;
				}
			}
			
			for(AccountItem accountItem:accountItems){
				
			}
		}
	}
	
	/**
	 * 更新指定社保方案的所有档案
	 * @param socialAccountId
	 */
	public void updateSocialItems(String socialAccountId){
	}
}
