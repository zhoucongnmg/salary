/**
 * TaxController.java
 */
package net.sion.company.salary.web;

import java.util.List;

import net.sion.company.salary.domain.SystemSalaryItem;
import net.sion.company.salary.event.SystemSalaryItemPublisher;
import net.sion.company.salary.sessionrepository.SystemSalaryItemRepository;
import net.sion.util.mvc.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author niexin
 *	薪资初始化
 */
@RestController
@RequestMapping("salary/init/") 
public class SalaryController {
	
	@Autowired
	SystemSalaryItemRepository ssir;
	
	@Autowired
	SystemSalaryItemPublisher publisher;


	@RequestMapping(value="regist")
	public Response regist() {
		List<SystemSalaryItem> items = publisher.regist();
		ssir.save(items);
		return new Response(true);
	}
	
}
