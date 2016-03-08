package net.sion.company.salary.config;

import java.math.BigDecimal;
import java.util.List;

import net.sion.company.salary.domain.Item.DecimalCarryType;
import net.sion.company.salary.domain.PersonAccountFile;
import net.sion.company.salary.domain.SocialAccountItem;
import net.sion.company.salary.domain.SocialAccountItem.PaymentType;
import net.sion.company.salary.domain.SocialItem;
import net.sion.company.salary.domain.SystemSalaryItem;
import net.sion.company.salary.domain.SystemSalaryItem.SystemSalaryItemType;
import net.sion.company.salary.domain.SystemSalaryItemEnum;
import net.sion.company.salary.event.SystemSalaryItemEvent;
import net.sion.company.salary.listener.AbstractSystemSalaryItemListener;
import net.sion.company.salary.service.SocialService;
import net.sion.company.salary.sessionrepository.PersonAccountFileRepository;
import net.sion.company.salary.sessionrepository.SocialAccountRepository;
import net.sion.company.salary.sessionrepository.SocialItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountItemRegister extends AbstractSystemSalaryItemListener{
	@Autowired SocialService socialService;
	@Autowired PersonAccountFileRepository personAccountFileRepository;
	@Autowired SocialAccountRepository socialAccountRepository;
	@Autowired SocialItemRepository socialItemRepository;
	
	@Override 
	public Double getValue(SystemSalaryItemEvent event) {
		Double value = null;
		//查询社保方案
		//查询社保项
		PersonAccountFile personAccountFile = personAccountFileRepository.findOne(event.getPersonId());
		for (SocialAccountItem item : personAccountFile.getInsuredItems()) {  
			if(item.getSocialItemId().equals(event.getItem().getItemId())){
				Double itemValue = 0.0d;
				SocialItem socialItem = socialItemRepository.findOne(item.getSocialItemId());
				if (event.getItem().getSystemType() == SystemSalaryItemType.Company) {
					if (item.getCompanyPaymentType() == PaymentType.Percent) {
						itemValue = item.getCardinality() * item.getCompanyPaymentValue();
					}else if (item.getCompanyPaymentType() == PaymentType.Quota) {
						itemValue = item.getCompanyPaymentValue();
					}
					value = decimal(socialItem.getCarryType(), item.getPrecision(), itemValue);
					break;
				}else if (event.getItem().getSystemType() == SystemSalaryItemType.Personal) {
					if (item.getPersonalPaymentType() == PaymentType.Percent) {
						itemValue = item.getCardinality() * item.getPersonalPaymentValue();
					}else if (item.getPersonalPaymentType() == PaymentType.Quota) {
						itemValue = item.getPersonalPaymentValue();
					}
					value = decimal(socialItem.getCarryType(), item.getPrecision(), itemValue);
					break;
				}
			}
		}
		return value;
	}
	
	private Double decimal(DecimalCarryType type, int precision, Double value){
		if(type == DecimalCarryType.Round){
			//四舍五入
			BigDecimal b = new BigDecimal(value);  
			value = b.setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue();  
		}else if(type == DecimalCarryType.Isopsephy){
			//数值进位
			BigDecimal b = new BigDecimal(value);  
			value = b.setScale(precision, BigDecimal.ROUND_UP).doubleValue();  
		}else{
			//数值舍位
			BigDecimal b = new BigDecimal(value);  
			value = b.setScale(precision, BigDecimal.ROUND_DOWN).doubleValue();  
		}
		return value;
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
