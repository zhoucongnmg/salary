package net.sion.company.salary.sessionrepository;

import java.util.List;

import net.sion.company.salary.domain.PayrollSub;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author niex
 *
 */
public interface PayrollSubRepository extends MongoRepository<PayrollSub, String>,
		PagingAndSortingRepository<PayrollSub, String> {
	List<PayrollSub> findByPayrollId(String payrollId);
}
