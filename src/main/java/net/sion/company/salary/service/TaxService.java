package net.sion.company.salary.service;

import java.math.BigDecimal;
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

		Double d = 0d;
		//工资个税的计算公式为：应纳税额=（工资薪金所得 －“五险一金”－个税起征点）×适用税率－速算扣除数
		Tax tax = taxRepository.findOne(taxId);
		BigDecimal b_value = new BigDecimal(value.toString());
		BigDecimal threshold =  new BigDecimal(tax.getThreshold().toString());
		Double subtractValue = b_value.subtract(threshold).doubleValue();
		List<TaxItem> list = tax.getTaxItems();
		for(TaxItem item : list){
			if(item.getStart() <= subtractValue && item.getEnd() >= subtractValue){
				d = new BigDecimal(subtractValue).multiply(new BigDecimal(item.getRate().toString())).subtract(new BigDecimal(item.getFastNumber())).doubleValue();
				break;
			}
		}
		return d;
	}
	
}
