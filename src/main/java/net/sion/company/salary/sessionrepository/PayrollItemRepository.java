/**
 * LevelRepository.java
 */
package net.sion.company.salary.sessionrepository;

import java.util.List;

import net.sion.company.salary.domain.PayrollItem;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author zhangligang
 *
 */
public interface PayrollItemRepository extends MongoRepository<PayrollItem, String>,
		PagingAndSortingRepository<PayrollItem, String> {
	public List<PayrollItem> findByPersonId(String personId);
	
	public List<PayrollItem> findByPayrollIdAndPersonIdIn(String payrollId,List<String> personIds);
	
	public List<PayrollItem> findByPayrollIdAndNameRegexOrderByPersonIdAsc(String payrollId,String name);
	
	public List<PayrollItem> findByPayrollId(String payrollId);
	
}
