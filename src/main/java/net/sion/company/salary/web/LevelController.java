/**
 * LevelController.java
 */
package net.sion.company.salary.web;

import java.util.List;

import net.sion.company.salary.domain.Level;
import net.sion.company.salary.domain.SalaryItem;
import net.sion.company.salary.sessionrepository.LevelRepository;
import net.sion.company.salary.sessionrepository.SalaryItemRepository;
import net.sion.util.mvc.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangligang
 *
 */
@RestController
@RequestMapping("/salary/level/") 
public class LevelController {
	@Autowired
	private LevelRepository levelRepository;
	@Autowired
	private SalaryItemRepository salaryItemRepository;
	
	@RequestMapping(value="create")
	public Response create(@RequestBody Level level) {
		levelRepository.save(level);
		return new Response("操作成功",	true);
	}
	
	@RequestMapping(value="read")
	public Level read(@RequestParam String id) {
		return levelRepository.findOne(id);
	}
	
	@RequestMapping(value = "update")
	public Response update(@RequestBody Level level) {
		return new Response("操作成功",	true);
	}
	
	@RequestMapping(value="remove")
	public Response remove(@RequestParam String id) {
		levelRepository.delete(id);
		return new Response("操作成功",	true);
	}
	
	@RequestMapping(value="load")
	public Response load() {
		List<Level> levels=levelRepository.findAll();
		
		return new Response("", levels, true);
	}
	
	@RequestMapping(value="getSalaryItem")
	public List<SalaryItem> getSalaryItem(){
		List<SalaryItem> salaryItems=salaryItemRepository.findAll();
		return salaryItems;
	}
}
