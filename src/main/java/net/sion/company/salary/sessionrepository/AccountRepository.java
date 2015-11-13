package net.sion.company.salary.sessionrepository;

import java.util.List;

import net.sion.company.salary.domain.Account;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AccountRepository extends MongoRepository<Account, String>,
		PagingAndSortingRepository<Account, String> {
	@Query(value = "?0")
	List<Account> getPageData(Object query, Pageable pageable);

	@Query(value = "?0")
	List<Account> getPages(Object query);
}