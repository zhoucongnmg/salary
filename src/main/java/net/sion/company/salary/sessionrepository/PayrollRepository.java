/**
 * LevelRepository.java
 */
package net.sion.company.salary.sessionrepository;

import java.util.List;

import net.sion.company.salary.domain.Formula;
import net.sion.company.salary.domain.Payroll;
import net.sion.company.salary.domain.PayrollItem;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author zhangligang
 *
 */
public interface PayrollRepository extends MongoRepository<Payroll, String>,
		PagingAndSortingRepository<Payroll, String> {
	List<Payroll> findByAccountId(String accountId);
	List<Payroll> findByAccountIdAndState(String accountId, String state);
}
