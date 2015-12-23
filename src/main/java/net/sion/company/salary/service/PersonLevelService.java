package net.sion.company.salary.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import net.sion.company.salary.domain.Level;
import net.sion.company.salary.domain.LevelItem;
import net.sion.company.salary.domain.PersonAccountFile;
import net.sion.company.salary.sessionrepository.LevelRepository;
import net.sion.company.salary.sessionrepository.PersonAccountFileRepository;

@Service
public class PersonLevelService {
	@Autowired
	private PersonAccountFileRepository personAccountFileRepo;
	@Autowired
	private LevelRepository levelRepository;
	
	/**
	 * 根据人员
	 * @param personIds 人员薪资档案ID集合
	 * @return Map<人员薪资档案ID , Map<薪资项ID , 值>>
	 */
	public Map<String,Map<String,Double>> findAllPersonLevelItems(Set<String> personIds){
		Map<String,Map<String,Double>> result=new HashMap<String,Map<String,Double>>();
		List<String> ids=new ArrayList<String>();
		for (String id : personIds) {
			ids.add(id);
		}
		//查询得到满足条件薪资档案
		List<PersonAccountFile> pafList= (List<PersonAccountFile>) personAccountFileRepo.findAll(ids);
		
		List<String> pafIds=new ArrayList<String>();
		for (PersonAccountFile personAccountFile : pafList) {
			pafIds.add(personAccountFile.getLevel());
		}
		//查询得到满足条件的薪资层级
		List<Level> levels=(List<Level>) levelRepository.findAll(pafIds);
		for (PersonAccountFile personAccountFile : pafList) {
			for (Level level : levels) {
				if(level.getId().equals(personAccountFile.getLevel())){
					result.put(personAccountFile.getPersonId(), getPersonItemValues(personAccountFile, level));
					break;
				}
			}
		}
		
		return result;
	}

	/**
	 * 从指定薪资层次中找到指定人的薪资值集合
	 * @param personAccountFile
	 * @param level
	 * @return
	 */
	private Map<String, Double> getPersonItemValues(PersonAccountFile personAccountFile, Level level) {
		Map<String,Double> personItemValue=new HashMap<String,Double>();
		for (LevelItem	item : level.getLevelItems()) {
			if(item.getRank().equals(personAccountFile.getRank())){
				for (Map<String, Double> map : item.getSalaryItemValues()) {
					personItemValue.putAll(map);
				}
			}
		}
		return personItemValue;
	}
}
