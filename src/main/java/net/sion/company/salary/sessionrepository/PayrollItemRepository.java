/**
 * LevelRepository.java
 */
package net.sion.company.salary.sessionrepository;

import java.util.List;

import net.sion.company.salary.domain.PayrollItem;
import net.sion.company.salary.domain.PersonAccountFile;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author zhangligang
 *
 */
public interface PayrollItemRepository extends MongoRepository<PayrollItem, String>,
		PagingAndSortingRepository<PayrollItem, String> {
	public List<PayrollItem> findByAccountId(String accountId);
}
