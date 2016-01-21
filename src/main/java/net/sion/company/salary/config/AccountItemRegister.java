package net.sion.company.salary.config;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sion.company.salary.domain.InsuredPerson;
import net.sion.company.salary.domain.PersonAccountFile;
import net.sion.company.salary.domain.SocialAccount;
import net.sion.company.salary.domain.SocialAccountItem;
import net.sion.company.salary.domain.SocialAccountItem.PaymentType;
import net.sion.company.salary.domain.SystemSalaryItem;
import net.sion.company.salary.domain.Item.DecimalCarryType;
import net.sion.company.salary.domain.SalaryItem.SalaryItemType;
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
		Double value = null;
		//查询社保方案
		//查询社保项
		PersonAccountFile personAccountFile = personAccountFileRepository.findOne(event.getPersonId());
//		InsuredPerson insuredPerson = personAccountFile.getInsuredPerson();
//		String accountId = insuredPerson.getAccountId();
//		SocialAccount socialAccount = socialAccountRepository.findOne(accountId);
//		Map<String, SocialAccountItem> map = new HashMap<String, SocialAccountItem>();
//		List<SocialAccountItem> socialAccountItems = socialAccount.getSocialAccountItems();
		//社保方案中的值
//		for(SocialAccountItem item : socialAccountItems){
//			map.put(item.getId(), item);
//		}
//		if(personAccountFile.getInsuredItems() != null && personAccountFile.getInsuredItems().size()>0){
//			//如果薪资档案中对社保项目设置了值，则以薪资档案的为主
//			for(SocialAccountItem item : personAccountFile.getInsuredItems()){
//				if(map.get(item.getId()) != null){
//					map.put(item.getId(), item);
//				}
//			}
//		}
		for (SocialAccountItem item : personAccountFile.getInsuredItems()) {  
			if(item.getSocialItemId().equals(event.getItem().getItemId())){
				if (event.getItem().getSystemType() == SystemSalaryItemType.Company) {
//					value = item.getCompanyPaymentFinalValue();
//					value = sum(SystemSalaryItemType.Company, item.getCarryType(), item.getPrecision(), item.getCardinality(), item.getCompanyPaymentValue(), item.getCompanyPaymentType());
					value = decimal(item.getCarryType(), item.getPrecision(), item.getCardinality() * item.getCompanyPaymentValue());
					break;
				}else if (event.getItem().getSystemType() == SystemSalaryItemType.Personal) {
//					value = item.getPersonalPaymentFinalValue();
//					value = sum(SystemSalaryItemType.Personal, item.getCarryType(), item.getPrecision(), item.getCardinality(), item.getPersonalPaymentValue(), item.getPersonalPaymentType());
					value = decimal(item.getCarryType(), item.getPrecision(), item.getCardinality() * item.getPersonalPaymentValue());
					break;
				}
			}
		}
		return value;
	}
//	private Double sum(SystemSalaryItemType systemSalaryItemType, DecimalCarryType type, int precision, Double cardinality, Double paymentValue, PaymentType paymentType){
//		Double value = null;
//		if(SystemSalaryItemType.Personal == systemSalaryItemType){
//			decimal(type, precision, companyPaymentValue);
//		}else if(SystemSalaryItemType.Company == systemSalaryItemType){
//			
//		}
//			switch(this.companyPaymentType) {
//				case Percent:
//					this.companyPaymentFinalValue = decimal(type, precision, Double.valueOf(cardinality * companyPaymentValue));
//					break;
//				case Quota:
//					this.companyPaymentFinalValue = decimal(type, precision, companyPaymentValue);
//					break;
//			}
////			switch(this.personalPaymentType) {
////				case Percent: 
////					this.personalPaymentFinalValue = decimal(type, precision, Double.valueOf(cardinality * personalPaymentValue));
////					break;
////				case Quota:
////					this.personalPaymentFinalValue = decimal(type, precision, personalPaymentValue);
////					break;
////			}
//		return value;
//	}
	
	private Double decimal(DecimalCarryType type, int precision, Double value){
		if(type == DecimalCarryType.Round){
			//四舍五入
			BigDecimal b = new BigDecimal(value);  
			value = b.setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue();  
		}else if(type == DecimalCarryType.Isopsephy){
			//数值进位
//			value = Math.ceil(value);
			BigDecimal b = new BigDecimal(value);  
			value = b.setScale(precision, BigDecimal.ROUND_UP).doubleValue();  
		}else{
			//数值舍位
//			value = Math.floor(value);
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
