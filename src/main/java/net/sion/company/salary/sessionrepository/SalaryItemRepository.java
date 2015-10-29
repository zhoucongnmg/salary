package net.sion.company.salary.sessionrepository;

import java.util.List;

import net.sion.company.salary.domain.SalaryItem;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SalaryItemRepository extends MongoRepository<SalaryItem, String>,
		PagingAndSortingRepository<SalaryItem, String> {
	@Query(value = "?0")
	List<SalaryItem> getPageData(Object query, Pageable pageable);

	@Query(value = "?0")
	List<SalaryItem> getPages(Object query);
}