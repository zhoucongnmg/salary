package net.sion.company.salary.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;

/**
 * 社保套账
 * @author niex
 *
 */

@RooJavaBean
@RooMongoEntity
@Document(collection="Company_Salary_SocialAccount")
public class SocialAccount {
	
	@Id
	String id;
	
	String name;	//套账名称
	
	String date;	//套账创建日期
	
	List<SocialAccountItem> socialAccountItems;	//社保套账明细项目
	
	
	
	
	
}
