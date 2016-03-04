package net.sion.company.salary.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sion.company.salary.domain.Account;
import net.sion.company.salary.domain.AccountItem;
import net.sion.company.salary.domain.InsuredPerson;
import net.sion.company.salary.domain.Level;
import net.sion.company.salary.domain.LevelItem;
import net.sion.company.salary.domain.PersonAccountFile;
import net.sion.company.salary.domain.PersonAccountFile.ItemSetting;
import net.sion.company.salary.domain.PersonAccountItem;
import net.sion.company.salary.domain.SalaryItem.SalaryItemType;
import net.sion.company.salary.domain.SocialAccount;
import net.sion.company.salary.domain.SocialAccountItem;
import net.sion.company.salary.sessionrepository.AccountRepository;
import net.sion.company.salary.sessionrepository.LevelRepository;
import net.sion.company.salary.sessionrepository.PersonAccountFileRepository;
import net.sion.company.salary.sessionrepository.SocialAccountRepository;

@Service
public class PersonAccountFileService {
	@Autowired
	private PersonAccountFileRepository pafRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private LevelRepository levelRepository;
	@Autowired
	private SocialAccountRepository socialRepository;

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
									if (StringUtils.isNotBlank(accountItem.getValue())) {
										accountValue = new Double(accountItem.getValue());
										break;
									}
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
	 * 
	 * @param salaryAccountId
	 */
	public void updateSalaryItems(String salaryAccountId) {
		Account account = accountRepository.findOne(salaryAccountId);
		List<AccountItem> accountItems = account.getAccountItems();
		List<PersonAccountFile> persons = pafRepository.findByAccountId(salaryAccountId);
		for (PersonAccountFile personAccountFile : persons) {

			List<PersonAccountItem> pAccountItems = personAccountFile.getAccountItems();
			boolean exist = false;
			// 先删除档案里有，方案里没有的
			for (int i = pAccountItems.size() - 1; i >= 0; i--) {
				PersonAccountItem pAccountItem = pAccountItems.get(i);
				for (AccountItem item : accountItems) {
					if (item.getSalaryItemId().equals(pAccountItem.getAccountItemId())
							&& SalaryItemType.Input.equals(item.getType())) {
						exist = true;
						break;
					}
				}

				if (exist == false) {
					pAccountItems.remove(i);
					personAccountFile.getAccountItemsSetting().remove(pAccountItem.getAccountItemId());
				} else {
					exist = false;
				}
			}

			exist = false;
			// 再添加方案里有，档案里没有的
			for (AccountItem accountItem : accountItems) {
				for (PersonAccountItem pAccountItem : pAccountItems) {
					if (accountItem.getSalaryItemId().equals(pAccountItem.getAccountItemId())) {
						exist = true;
						break;
					}
				}
				if (exist == false && SalaryItemType.Input.equals(accountItem.getType())) {
					PersonAccountItem paItem = new PersonAccountItem();
					paItem.setAccountItemId(accountItem.getSalaryItemId());
					paItem.setAccountItemName(accountItem.getName());
					pAccountItems.add(paItem);

					if (personAccountFile.getAccountItemsSetting() == null) {
						personAccountFile.setAccountItemsSetting(new HashMap<String, ItemSetting>());
					}
					personAccountFile.getAccountItemsSetting().put(accountItem.getSalaryItemId(), ItemSetting.Solution);
				} else {
					exist = false;
				}
			}

			// 保存
			pafRepository.save(personAccountFile);
		}
	}

	/**
	 * 更新指定社保方案的所有档案
	 * 
	 * @param socialAccountId
	 */
	public void updateSocialItems(String socialAccountId) {
		SocialAccount account = socialRepository.findOne(socialAccountId);
		List<PersonAccountFile> persons = pafRepository.findByInsuredPersonAccountId(socialAccountId);
		if (persons != null)
			for (PersonAccountFile personAccountFile : persons) {
				personAccountFile.getInsuredPerson().setAccountId(socialAccountId);
				personAccountFile.setInsuredItems(account.getSocialAccountItems());
				// 保存
				pafRepository.save(personAccountFile);
			}
	}

	/**
	 * 更新指定层次的所有档案
	 * 
	 * @param levelId
	 */
	public void updateLevel(String levelId) {
		Level level = levelRepository.findOne(levelId);

		List<PersonAccountFile> persons = pafRepository.findByLevel(levelId);
		for (PersonAccountFile personAccountFile : persons) {
			List<PersonAccountItem> items = personAccountFile.getAccountItems();
			Map<String, ItemSetting> setting = personAccountFile.getAccountItemsSetting();
			LevelItem levelItem = null;
			// 循环对比，找到薪资档案中层级值
			for (LevelItem item : level.getLevelItems()) {
				if (item.getRank().equals(personAccountFile.getRank())) {
					levelItem = item;
					if (personAccountFile.getAccountItems() != null)
						for (PersonAccountItem paItem : personAccountFile.getAccountItems()) {
							// 如果原有薪资项目取值设置为“层级值”，则在取不到层级值的情况下设置为“方案值”
							if (ItemSetting.Level.equals(setting.get(paItem.getAccountItemId()))) {
								if (item.getSalaryItemValues().get(paItem.getAccountItemId()) == null) {
									setting.put(paItem.getAccountItemId(), ItemSetting.Solution);
								}
							}
						}
				}
			}
			// 如果原薪资档案中的层级已经不存在，应该修改薪资档案
			if (levelItem == null && personAccountFile.getAccountItems() != null) {
				for (PersonAccountItem paItem : personAccountFile.getAccountItems()) {
					// 如果原有薪资项目取值设置为“层级值”，设置为“方案值”
					if (ItemSetting.Level.equals(setting.get(paItem.getAccountItemId()))) {
						setting.put(paItem.getAccountItemId(), ItemSetting.Solution);
					}
				}
			}

			// 保存
			pafRepository.save(personAccountFile);
		}
	}

	/**
	 * 删除薪资方案，修改相关记录
	 * 
	 * @param salaryAccountId
	 */
	public void deleteSalaryItems(String salaryAccountId) {
		List<PersonAccountFile> persons = pafRepository.findByAccountId(salaryAccountId);
		if (persons != null) {
			for (PersonAccountFile personAccountFile : persons) {
				personAccountFile.setAccountId(null);
				personAccountFile.setAccountItems(null);
				personAccountFile.setAccountItemsSetting(null);

				// 保存
				pafRepository.save(personAccountFile);
			}
		}
	}

	/**
	 * 删除社保方案，修改相关记录
	 * 
	 * @param socialAccountId
	 */
	public void deleteSocialItems(String socialAccountId) {

		List<PersonAccountFile> persons = pafRepository.findByInsuredPersonAccountId(socialAccountId);
		if (persons != null) {
			for (PersonAccountFile personAccountFile : persons) {
				personAccountFile.getInsuredPerson().setAccountId(null);
				personAccountFile.setInsuredItems(null);
				// 保存
				pafRepository.save(personAccountFile);
			}
		}
	}

	/**
	 * 删除层级，修改相关记录
	 * 
	 * @param levelId
	 */
	public void deleteLevel(String levelId) {
		List<PersonAccountFile> persons = pafRepository.findByLevel(levelId);
		for (PersonAccountFile personAccountFile : persons) {
			Map<String, ItemSetting> setting = personAccountFile.getAccountItemsSetting();
			personAccountFile.setLevel(null);
			personAccountFile.setRank(null);

			if (personAccountFile.getAccountItems() != null) {
				for (PersonAccountItem paItem : personAccountFile.getAccountItems()) {
					// 如果原有薪资项目取值设置为“层级值”，设置为“方案值”
					if (ItemSetting.Level.equals(setting.get(paItem.getAccountItemId()))) {
						setting.put(paItem.getAccountItemId(), ItemSetting.Solution);
					}
				}
			}

			// 保存
			pafRepository.save(personAccountFile);
		}
	}
}