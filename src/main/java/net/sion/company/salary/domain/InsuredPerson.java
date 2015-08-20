package net.sion.company.salary.domain;

import org.springframework.data.annotation.Id;

/**
 * 投保人
 * @author niex
 *
 */
public class InsuredPerson {
	@Id
	String id;
	
	String insuredNo;	//社保号
	
	String insuredDate;	//参保日期
	
	String workplace;	//工作地
	
	InsuredStaus status;	//参保状态
	
	String outDate;	//退保日期
	
					//套账Id
	
	String socialWorkplace;	//社保代办地
	
	String accumulationFundsWorkplace;	//公积金代办地
	
	String remark;	//备注
	
	public enum InsuredStaus {
		In, //在保
		Out //退保
	}
}
