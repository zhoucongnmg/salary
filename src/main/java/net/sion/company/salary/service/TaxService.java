package net.sion.company.salary.service;

import net.sion.company.salary.sessionrepository.TaxRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaxService {
	@Autowired TaxRepository taxRepository;
	
	public double getFastNumber(Double value){
		
		return 0;
	}
}
