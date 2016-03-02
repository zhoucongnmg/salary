package net.sion.company.salary.web;

import java.util.List;

import net.sion.company.salary.domain.Level;
import net.sion.company.salary.domain.Payroll;
import net.sion.company.salary.domain.PayrollItem;
import net.sion.company.salary.domain.PayrollSub;
import net.sion.company.salary.service.PayrollItemService;
import net.sion.company.salary.sessionrepository.PayrollItemRepository;
import net.sion.company.salary.sessionrepository.PayrollRepository;
import net.sion.company.salary.sessionrepository.PayrollSubRepository;
import net.sion.util.mvc.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author niex
 *
 */
@RestController
@RequestMapping("/salary/payrollsub/") 
public class PayrollSubController {
	@Autowired
	PayrollSubRepository payrollSubRepository;
	
	@Autowired 
	PayrollItemService payrollItemService;
	
	@Autowired
	PayrollRepository payrollRepository;
	
	@Autowired
	PayrollItemRepository payrollItemRepository;
	
	@RequestMapping(value="create")
	public Response create(@RequestBody PayrollSub payrollSub) {
		payrollSubRepository.save(payrollSub);
		
		Payroll payroll = payrollRepository.findOne(payrollSub.getPayrollId());
		List<PayrollItem> items = payrollItemService.generatePayrollItem(payroll,payrollSub,payroll.getPersons().keySet());
		payrollItemRepository.save(items);
		return new Response("操作成功",	true);
	}
	
	
	@RequestMapping(value = "update")
	public Response update(@RequestBody Level level) {
		return new Response("操作成功",	true);
	}
	
	@RequestMapping(value="remove")
	public Response remove(@RequestParam String id) {
		payrollSubRepository.delete(id);
		return new Response("操作成功",	true);
	}
	
	@RequestMapping(value="findByPayrollId")
	public Response findByPayrollId(@RequestParam String payrollId) {
		List<PayrollSub> payrollSubs = payrollSubRepository.findByPayrollId(payrollId);
		
		return new Response(payrollSubs);
	}
	
}
