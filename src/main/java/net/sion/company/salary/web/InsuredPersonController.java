package net.sion.company.salary.web;

import java.util.List;
import java.util.Map;

import net.sion.company.salary.domain.InsuredPerson;
import net.sion.company.salary.domain.Level;
import net.sion.company.salary.domain.PersonAccountFile;
import net.sion.company.salary.domain.PersonAccountItem;
import net.sion.company.salary.sessionrepository.PersonAccountRepository;
import net.sion.util.mvc.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 社保投保
 * @author niex 
 */
@RestController
@RequestMapping("/salary/person/") 
public class InsuredPersonController {
	@Autowired
	private PersonAccountRepository personAccountRepo;
	
	/**
	 * 创建投保人
	 * 
	 * @param person
	 * @return
	 */
	@RequestMapping(value = "create")
	public Response create(@RequestBody PersonAccountFile person) {
		personAccountRepo.save(person);
		return new Response("操作成功",	true);
	}

	/**
	 * 读取投保人信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "read")
	public PersonAccountFile read(@RequestParam String id) {
		return personAccountRepo.findOne(id);
	}
	
	/**
	 * 读取HR人员档案信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "readPerson")
	public Response readPerson(@RequestParam String id) {
		return new Response(true);
	}

	/**
	 * 更新投保人
	 * 
	 * @param person
	 * @return
	 */
	@RequestMapping(value = "update")
	public Response update(@RequestBody PersonAccountFile person) {
		return new Response(true);
	}


	/**
	 * 查询未投保人员(查询HR人员档案的信息)
	 * @param 搜索条件、分页信息
	 * @return
	 */
	@RequestMapping(value = "loadUninsuredPerson")
	public Response loadUninsuredPerson(@RequestBody Map<String,String> paramMap) {
		return new Response(true);
	}
	
	/**
	 * 查询已投保人员
	 * @param 搜索条件、分页信息
	 * @return
	 */
	@RequestMapping(value = "loadInsuredPerson")
	public Response loadInsuredPerson(@RequestBody Map<String,String> paramMap) {
		return new Response(true);
	}

	@RequestMapping(value="load")
	public Response load() {
		List<PersonAccountFile> personAccountFiles=personAccountRepo.findAll();
		
		return new Response("操作成功", personAccountFiles, true);
	}
	
	@RequestMapping(value="remove")
	public Response remove(@RequestParam String id) {
		personAccountRepo.delete(id);
		return new Response("操作成功",	true);
	}
	
	
}
