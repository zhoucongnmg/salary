/**
 * LevelRepository.java
 */
package net.sion.company.salary.sessionrepository;

import java.util.List;

import net.sion.company.salary.domain.PayrollItem;
import net.sion.company.salary.domain.PersonAccountFile;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author zhangligang
 *
 */
public interface PayrollItemRepository extends MongoRepository<PayrollItem, String>,
		PagingAndSortingRepository<PayrollItem, String> {
	public List<PayrollItem> findByPersonId(String personId);
	
	public List<PayrollItem> findByPayrollIdAndPersonIdIn(String payrollId,List<String> personIds);
	
	@Query(value = "{payrollId:?0,name:{$regex:?1}}")
	public List<PayrollItem> findByPayrollIdAndRegexName(String payrollId,String name);
	
	public List<PayrollItem> findByPayrollId(String payrollId);
	
}
