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
	public Map<String, Double> getItemValue(PersonAccountFile paf) {
		Map<String, Double> result = new HashMap<>();
		Map<String, Double> accountMap = new HashMap<>();
		Map<String, Double> levelMap = new HashMap<>();

		PersonAccountFile person = pafRepository.findOne(paf.getId());
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
}
