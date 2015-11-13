package net.sion.company.salary.sessionrepository;

import java.util.List;

import net.sion.company.salary.domain.Tax;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TaxRepository extends MongoRepository<Tax, String>,
		PagingAndSortingRepository<Tax, String> {
	@Query(value = "?0")
	List<Tax> getPageData(Object query, Pageable pageable);

	@Query(value = "?0")
	List<Tax> getPages(Object query);
}