/**
 * TaxController.java
 */
package net.sion.company.salary.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sion.company.salary.domain.SocialItem;
import net.sion.company.salary.domain.Tax;
import net.sion.company.salary.sessionrepository.TaxRepository;
import net.sion.util.mvc.Response;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangligang
 *	个税设置
 */
@RestController
@RequestMapping("/salary/tax/") 
public class TaxController {
	@Autowired TaxRepository taxRepository;

	@RequestMapping(value="create")
	public Response create(@RequestBody Tax tax) {
		if(tax.getId() == null || "".equals(tax.getId())){
			tax.setId(new ObjectId().toString());
		}
		taxRepository.save(tax);
		return new Response(true);
	}
	
	@RequestMapping(value = "update")
	public Response update(@RequestBody Tax tax) {
		return new Response(true);
	}
	
	@RequestMapping(value="remove")
	public Response remove(@RequestParam String id) {
		taxRepository.delete(id);
		return new Response(true);
	}
	
	@RequestMapping(value="load")
	public Response load() {
//		Sort sort = new Sort(Direction.ASC, "date");
		List<Tax> list = taxRepository.findAll();
		return new Response(list);
	}
}
