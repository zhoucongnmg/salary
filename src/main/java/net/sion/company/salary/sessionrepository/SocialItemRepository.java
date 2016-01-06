package net.sion.company.salary.sessionrepository;

import java.util.List;

import net.sion.company.salary.domain.Item.ItemType;
import net.sion.company.salary.domain.SocialItem;
import net.sion.company.salary.domain.SocialItem.SocialItemType;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SocialItemRepository extends MongoRepository<SocialItem, String>,
		PagingAndSortingRepository<SocialItem, String> {
	@Query(value = "?0")
	List<SocialItem> getPageData(Object query, Pageable pageable);

	@Query(value = "?0")
	List<SocialItem> getPages(Object query);
	
	List<SocialItem> findByItemType(SocialItemType itemType);
	List<SocialItem> findByItem(ItemType item);
}