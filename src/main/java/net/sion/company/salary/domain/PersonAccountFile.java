/**
 * PersonAccountFile.java
 */
package net.sion.company.salary.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author zhangligang
 *
 */
@Document(collection="Company_Salary_PersonAccountFile")
public class PersonAccountFile {
	@Id
	private String id;
	private String personCode;
	private String name;
	private String dept;
	private String duty;
	private String idCard;
	private String bank;
	private String bankAccount;
	private String bankOfDeposit;
	private String accountId;
	private String level;
	private String rank;
	private String note;
	private List<PersonAccountItem> accountItems;
	private InsuredPerson insuredPerson;
	
	
}
