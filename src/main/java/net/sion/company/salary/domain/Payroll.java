package net.sion.company.salary.domain;

import java.util.List;

import javax.persistence.Transient;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;

/**
 * 薪资发放
 * @author niex
 *
 */

@RooJavaBean
@RooMongoEntity
@Document(collection="Company_Salary_Payroll")
public class Payroll {
	
	@Id
	String id;
	
	String subject;	//薪资主题
	
	String month; //薪资月份
	
	String socialCostMonth;	//社保扣费月份
	
	String accountId;	//套账Id
	
	List<String> persons;	//发放人员 
	
	String date;	//套账创建日期
	
	String processId;	//流程实例id
	
	List<SocialAccountItem> socialAccountsItems;	//社保套账明细项目
	
	@Transient
	List<AccountItem> accountItems;	//薪资项目(用于前台列显示)
	
	
	public enum PayrollStatus{
		Unpublish,
		Pass,
		Paid
	}
	
	
	
	
}
