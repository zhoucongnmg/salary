package net.sion.company.salary.sessionrepository;

import java.util.List;
import java.util.Set;

import net.sion.company.salary.domain.Account;
import net.sion.company.salary.domain.PersonAccountFile;

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
	
//	@Query(value = "{$or:[{_id:?0},{name:?1}]}")
//	List<Account> findByIdAndName(String id,String name);
	List<Account> findByName(String name);
}