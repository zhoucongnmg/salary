/**
 * PersonAccountRepository.java
 */
package net.sion.company.salary.sessionrepository;

import java.util.List;

import net.sion.company.salary.domain.PersonAccountFile;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author zhangligang
 *
 */
public interface PersonAccountRepository extends
		MongoRepository<PersonAccountFile, String>,
		PagingAndSortingRepository<PersonAccountFile, String> {
	
	public List<PersonAccountFile> findByPersonCode(String personCode);
	
	public List<PersonAccountFile> findByAccountId(String accountId);
}
