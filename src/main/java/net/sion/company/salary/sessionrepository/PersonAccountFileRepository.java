package net.sion.company.salary.sessionrepository;

import java.util.List;

import net.sion.company.salary.domain.PersonAccountFile;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PersonAccountFileRepository extends MongoRepository<PersonAccountFile, String>,
		PagingAndSortingRepository<PersonAccountFile, String> {
	@Query(value = "?0")
	List<PersonAccountFile> getPageData(Object query, Pageable pageable);

	@Query(value = "?0")
	List<PersonAccountFile> getPages(Object query);
	
	List<PersonAccountFile> findByPersonIdIn(List<String> personIds);
	
	List<PersonAccountFile> findByAccountId(String accountId);
	
}