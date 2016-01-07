package net.sion.company.salary.sessionrepository;

import net.sion.company.salary.domain.SystemSalaryItem;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SystemSalaryItemRepository extends MongoRepository<SystemSalaryItem, String>,
		PagingAndSortingRepository<SystemSalaryItem, String> {
}