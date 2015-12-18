package net.sion.company.salary.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.sion.company.salary.domain.Formula;
import net.sion.company.salary.sessionrepository.FormulaRepository;
import net.sion.util.mvc.Response;

@RestController
@RequestMapping("/salary/formula/") 
public class FormulaController {
	@Autowired
	private FormulaRepository formulaRepository;

	@RequestMapping(value="create")
	public Response create(@RequestBody Formula formula) {
		formulaRepository.save(formula);
		return new Response("操作成功",	true);
	}
	
	@RequestMapping(value="read")
	public Formula read(@RequestParam String id) {
		return formulaRepository.findOne(id);
	}
	
	@RequestMapping(value = "update")
	public Response update(@RequestBody Formula formula) {
		return new Response("操作成功",	true);
	}
	
	@RequestMapping(value="remove")
	public Response remove(@RequestParam String id) {
		formulaRepository.delete(id);
		return new Response("操作成功",	true);
	}
}
