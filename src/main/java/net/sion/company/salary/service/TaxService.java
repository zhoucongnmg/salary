package net.sion.company.salary.service;

import java.util.List;

import net.sion.company.salary.domain.Tax;
import net.sion.company.salary.domain.TaxItem;
import net.sion.company.salary.sessionrepository.TaxRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaxService {
	@Autowired TaxRepository taxRepository;
	
	public Double getFastNumber(String taxId, Double value){
		Tax tax = taxRepository.findOne(taxId);
		List<TaxItem> list = tax.getTaxItems();
		for(TaxItem item : list){
			if(item.getStart() <= value && item.getEnd() >= value){
				return item.getFastNumber();
			}
		}
		return 0d;
	}
}
