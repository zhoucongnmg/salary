package net.sion.company.salary.domain;



import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;





import net.sion.boot.config.jackson.CustomJackson;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * 工资表条目(用于前台显示)
 * @author niex
 *
 */

@RooJavaBean
@RooMongoEntity
@Document(collection="Company_Salary_PayrollItem")
public class PayrollItem {
	
	@Id
	String id;
	
	String payrollId;
	
	String payrollSubId;
	
	String personId;	//人事档案id
	
	String name;	//姓名
	
	String duty;	//职务
	
	String dept;	//部门
	
	String personCode; //人员编号
	
	String idCard;	//身份证号
	
	String bankAccount;	//银行账号
	
	String bank;//银行
	
	String bankOfDeposit;//开户网点
	
	Map<String,Double> values = new HashMap<String,Double>();	//薪资明细项
	
	public PayrollItem() {
	}

	public PayrollItem(String id, String payrollId, String personId,
			String name, String duty, String dept, Map<String, Double> values) {
		super();
		this.id = new ObjectId().toString();
		this.payrollId = payrollId;
		this.personId = personId;
		this.name = name;
		this.duty = duty;
		this.dept = dept;
		this.values = values;
	}

	public Map<String,Object> parseMap() {
		CustomJackson jackson = new CustomJackson();
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			map = jackson.readValue(jackson.writeValueAsString(this), new TypeReference<Map<String,Object>>(){});
			map.putAll(values);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	public void convertDomain(Map<String,?> map) {
		Class<? extends PayrollItem> me = this.getClass();
		for (Map.Entry<String, ?> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			try {
				Method m = me.getMethod("set" + toUpperCaseFirstOne(key),String.class);
				if ("id".equals(key)&&StringUtils.isBlank((String)value)) {
					value = new ObjectId().toString();
				}
				m.invoke(this, value);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				if (value instanceof Double) {
					values.put(key,(Double)value);
				}else if (value instanceof Integer) {
					values.put(key,Double.valueOf(((Integer) value).intValue()));
				}else if (value instanceof String) {
					values.put(key, Double.valueOf((String)value));
				}
				
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	
	//首字母转大写
    public String toUpperCaseFirstOne(String s)
    {
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public Map<String, Double> getValues() {
		return values;
	}

	public void setValues(Map<String, Double> values) {
		this.values = values;
	}


	public String getPayrollId() {
		return payrollId;
	}


	public void setPayrollId(String payrollId) {
		this.payrollId = payrollId;
	}

	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankOfDeposit() {
		return bankOfDeposit;
	}

	public void setBankOfDeposit(String bankOfDeposit) {
		this.bankOfDeposit = bankOfDeposit;
	}
	
	
	
	
	
}
