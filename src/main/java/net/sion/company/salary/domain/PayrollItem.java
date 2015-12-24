package net.sion.company.salary.domain;



import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import net.sion.boot.config.jackson.CustomJackson;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	String personId;	//人事档案id
	
	String name;	//姓名
	
	String duty;	//职务
	
	String dept;	//部门
	
	Map<String,Double> values = new HashMap<String,Double>();	//薪资明细项
	
	@Autowired CustomJackson jackson;
	
	public Map<String,Object> parseMap() {
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
				m.invoke(this, value);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				if (value instanceof Double) {
					values.put(key,(Double)value);
				}else if (value instanceof Integer) {
					values.put(key,Double.valueOf(((Integer) value).intValue()));
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
	
	
}
