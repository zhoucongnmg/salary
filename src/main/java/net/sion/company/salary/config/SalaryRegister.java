package net.sion.company.salary.config;

import java.util.ArrayList;
import java.util.List;

import net.sion.core.admin.domain.Duties;
import net.sion.core.app.domain.App;
import net.sion.core.app.domain.AppEnum;
import net.sion.core.app.domain.Module;
import net.sion.core.app.domain.Role;
import net.sion.core.app.listener.AbstractAppRegisterListener;

import org.springframework.stereotype.Component;

@Component
public class SalaryRegister extends AbstractAppRegisterListener {
	
	@Override
	public List<Module> registModule(List<Module> empty) {
		List<Module> salaryList=AppEnum.Salary.getModules();
		empty.addAll(AppEnum.Salary.getModules());
		
		
		Module m0 = AppEnum.Salary.getModules().get(0);
		m0.addDepends(salaryList.get(1));
		m0.addDepends(salaryList.get(2));
		m0.addDepends(salaryList.get(3));
		m0.addDepends(salaryList.get(4));
		m0.addDepends(salaryList.get(5));
		
		empty.add(m0);
		return empty;
	}
	
	@Override
	public List<App> registApp(List<App> empty) {
		empty.add(AppEnum.Salary.getApp());
		return empty;
	}
	
	@Override
	public List<Role> registRole(List<Role> empty) {
		Role role = new Role(AppEnum.Salary.getApp(),"Salary","薪资管理员","薪资管理角色可以维护管理本公司人员的薪资信息");
		role.addModules(AppEnum.Salary.getModules());
		role.criteriaIn(new Duties[]{Duties.HR});
		empty.add(role);
		return empty;
	}
	
}
