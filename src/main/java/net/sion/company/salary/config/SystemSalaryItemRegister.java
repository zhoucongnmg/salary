package net.sion.company.salary.config;

import java.util.ArrayList;
import java.util.List;

import net.sion.boot.mongo.template.DynamicMongoTemplate;
import net.sion.company.salary.domain.SalaryItem;
import net.sion.company.salary.domain.SalaryItem.SalaryItemType;
import net.sion.company.salary.domain.SystemSalaryItemEnum;
import net.sion.company.salary.dynamicrepository.DynamicSalaryItemRepository;
import net.sion.core.admin.domain.Company;
import net.sion.core.app.domain.App;
import net.sion.core.app.domain.AppEnum;
import net.sion.core.app.listener.AbstractAppInstallListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SystemSalaryItemRegister extends AbstractAppInstallListener{
	
	@Autowired
	DynamicSalaryItemRepository dsir;
	
	@Override
	public String listenTo() {
		return AppEnum.Salary.getId();
	}

	@Override
	public void install(App app, Company company) {
		SystemSalaryItemEnum[] items = SystemSalaryItemEnum.values();
		List<SalaryItem> salaryItems = new ArrayList<SalaryItem>();
		for (SystemSalaryItemEnum item : items) {
			SalaryItem salaryItem =  new SalaryItem(item.getId(),item.getName(),SalaryItemType.System);
			salaryItems.add(salaryItem);
		}
		dsir.save(salaryItems);
	}

	@Override
	public void uninstall(App app, Company company) {
		System.out.println("我要卸载！:" + app.getId());
	} 
}
