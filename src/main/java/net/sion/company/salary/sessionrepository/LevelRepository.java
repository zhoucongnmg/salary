/**
 * LevelRepository.java
 */
package net.sion.company.salary.sessionrepository;

import net.sion.company.salary.domain.Level;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author zhangligang
 *
 */
public interface LevelRepository extends MongoRepository<Level, String>,
		PagingAndSortingRepository<Level, String> {

}
