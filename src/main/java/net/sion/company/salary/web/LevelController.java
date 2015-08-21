/**
 * LevelController.java
 */
package net.sion.company.salary.web;

import net.sion.company.salary.domain.Level;
import net.sion.util.mvc.Response;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhangligang
 *
 */
public class LevelController {
	@RequestMapping(value="create")
	public Response create(@RequestBody Level level) {
		return new Response(true);
	}
	
	@RequestMapping(value="read")
	public Response read(@RequestParam String id) {
		return new Response(true);
	}
	
	@RequestMapping(value = "update")
	public Response update(@RequestBody Level level) {
		return new Response(true);
	}
	
	@RequestMapping(value="remove")
	public Response remove(@RequestParam String id) {
		return new Response(true);
	}
	
	@RequestMapping(value="load")
	public Response load() {
		return new Response(true);
	}
}
