package net.sion.company.salary.domain;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author niex
 *	公式
 */
@Document(collection="Company_Salary_Formula")
public class Formula implements Cloneable{
	@Id
	String id;
	//公式中变量（公式项）列表
	List<FormulaItem> items;

	//结果字段Id
	String resultFieldId;
	//计算公式文本 形式如： 1-（2X 请假天数）+补助
	String formula;
	
	public Formula () {
		
	}
	public Formula(String formula,List<FormulaItem> items,String resultFieldId) {
		this.formula = formula;
		this.items = items;
		this.resultFieldId = resultFieldId;
	}
	
	@Override  
    public Object clone() {  
		Formula a = null;  
        try{  
            a = (Formula)super.clone();  
        }catch(CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return a;  
    }
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<FormulaItem> getItems() {
		return items;
	}

	public void setItems(List<FormulaItem> items) {
		this.items = items;
	}

	public String getResultFieldId() {
		return resultFieldId;
	}

	public void setResultFieldId(String resultFieldId) {
		this.resultFieldId = resultFieldId;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}
	
	
}
