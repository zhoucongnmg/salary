package net.sion.company.salary.config;


import net.sion.company.salary.event.SystemSalaryItemPublisher;
import net.sion.company.salary.sessionrepository.SystemSalaryItemRepository;
import net.sion.core.admin.domain.Company;
import net.sion.core.app.domain.App;
import net.sion.core.app.domain.AppEnum;
import net.sion.core.app.listener.AbstractAppInstallListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SalaryInstaller extends AbstractAppInstallListener{
	
	@Autowired
	SystemSalaryItemRepository ssir;
	
	@Autowired
	SystemSalaryItemPublisher publisher;

	@Override
	public String listenTo() {
		return AppEnum.Salary.getId();
	}

	@Override
	public void install(App app, Company company) {
		//List<SystemSalaryItem> items = publisher.regist();
		//ssir.save(items);
	}

	@Override
	public void uninstall(App app, Company company) {
		System.out.println("我要卸载！:" + app.getId());
	} 
}
 