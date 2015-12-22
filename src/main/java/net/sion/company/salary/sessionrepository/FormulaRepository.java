package net.sion.company.salary.sessionrepository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.sion.company.salary.domain.Formula;

public interface FormulaRepository extends MongoRepository<Formula, String> {

	List<Formula> findByItemsFieldId(String fieldId);

	Formula findByResultFieldId(String fieldId);

}
