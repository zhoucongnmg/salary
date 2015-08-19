package net.sion.company.salary.config;

import java.util.ArrayList;
import java.util.List;

import net.sion.core.admin.domain.Duties;
import net.sion.core.app.domain.App;
import net.sion.core.app.domain.Module;
import net.sion.core.app.domain.Role;
import net.sion.core.app.listener.AbstractAppRegisterListener;

import org.springframework.stereotype.Component;

@Component
public class SalaryRegister extends AbstractAppRegisterListener {
	
	Module module = new Module("250_1", "sion.salary.main", "薪资管理");
	App app = new App("250","薪资管理");

	
	@Override
	public List<Module> registModule(List<Module> empty) {
		empty.add(module);
		return empty;   
	}
	
	@Override
	public List<App> registApp(List<App> empty) {
		empty.add(app);
		return empty;
	}
	
	@Override
	public List<Role> registRole(List<Role> empty) {
		Role role = new Role(app,"salary","薪资管理员","薪资管理角色");
		List<Module> modules = new ArrayList<Module>();
		modules.add(module);
		role.addModules(modules);
		role.criteriaIn(new Duties[]{Duties.SuperAdmin});
		empty.add(role);
		return empty;
	}
	
}
