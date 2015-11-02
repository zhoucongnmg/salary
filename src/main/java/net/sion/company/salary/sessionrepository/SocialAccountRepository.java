package net.sion.company.salary.sessionrepository;

import java.util.List;

import net.sion.company.salary.domain.SocialAccount;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SocialAccountRepository extends MongoRepository<SocialAccount, String>,
		PagingAndSortingRepository<SocialAccount, String> {
	@Query(value = "?0")
	List<SocialAccount> getPageData(Object query, Pageable pageable);

	@Query(value = "?0")
	List<SocialAccount> getPages(Object query);
}