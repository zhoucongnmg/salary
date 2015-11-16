package net.sion.company.salary.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;

/**
 * 工资条明细项（存储工资条项目中每个单元格的信息）
 * @author niex
 *
 */

@RooJavaBean
@RooMongoEntity
@Document(collection="Company_Salary_PayrollCell")
public class PayrollCell {
	
	@Id
	String id;
	
	String personId; //人员档案id
	
	String payId;	//工资发放id
	
	String accountId;	//套账id
	
	String itemId;	//薪资项目id
	
	double amount;	//金额
	
	String remark;	//备注
	
	
	
	
	
}
