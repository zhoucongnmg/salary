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
	/***
	 * 
	 * @param taxId 个税设置的id
	 * @param value 工资-五险一金 后的值
	 * @return 返回应纳个人所得税
	 */
	public Double getFastNumber(String taxId, Double value){
		//工资个税的计算公式为：应纳税额=（工资薪金所得 －“五险一金”－个税起征点）×适用税率－速算扣除数
		Tax tax = taxRepository.findOne(taxId);
		value = value - tax.getThreshold();
		List<TaxItem> list = tax.getTaxItems();
		for(TaxItem item : list){
			if(item.getStart() <= value && item.getEnd() >= value){
				value = (value * item.getRate() * 0.01) - item.getFastNumber();
				return item.getFastNumber();
			}
		}
		return 0d;
	}
}
