package net.sion.company.salary.service;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs.collections.impl.factory.Lists;

import net.sion.company.salary.domain.Formula;
import net.sion.company.salary.domain.FormulaItem;
import net.sion.company.salary.sessionrepository.FormulaRepository;

@Service
public class FormulaService {

	@Autowired
	private FormulaRepository formulaRepository;

	private ScriptEngine jsEngine;

	public FormulaService() {
		super();
		jsEngine = new ScriptEngineManager().getEngineByName("JavaScript");
	}

	/**
	 * 方法：传入一个FieldId，查找受其影响的字段
	 * @param fieldId
	 * @return 
	 */
	public List<String> getInfluencedField(List<String> formulaIds,String fieldId){
		//找到所有满足ids条件的公式
		List<Formula> formulas=(List<Formula>) formulaRepository.findAll(formulaIds);
		return findInfluenced(fieldId, formulas);
	}

	private List<String> findInfluenced(String fieldId,List<Formula> formulas) {
		List<String> infuencedList=new ArrayList<String>();
		
		for (int i = formulas.size()-1; i >0; i--) {
			List<FormulaItem> items=formulas.get(i).getItems();
			for (FormulaItem formulaItem : items) {
				if(fieldId.equals(formulaItem.getFieldId())){
					infuencedList.add(formulas.get(i).getResultFieldId());
					formulas.remove(i);
				}
			}
		}
		List<String> temp=new ArrayList<String>();
		for (String string : infuencedList) {
			temp.addAll(this.findInfluenced(string, formulas));
		}
		infuencedList.addAll(temp);
		return infuencedList;
	}
	
	
	/**
	 * TODO 方法：传入 FieldId和值的集合，返回受其影响的公式及结果
	 */
	public List<Formula> caculateInfluencedFormula(){
		
		return null;
	}
	
	/**
	 * 取得要计算某字段值时公式中所有需要的变量字段
	 * （向下递归）
	 * @param formulaId 待计算的字段ID
	 * @return
	 */
	public List<FormulaItem> getFormulaItems(String formulaId) {
		Formula formula = formulaRepository.findOne(formulaId);
		List<FormulaItem> items = formula.getItems();
		for (FormulaItem item : items) {
			if (FormulaItem.FormulaType.ResultItem.equals(item.getType())) {
				items.addAll(this.getFormulaItems(item.getFieldId()));
			}
		}
		return items;
	}

	/**
	 * 通过公式计算, 返回计算结果
	 * 
	 * @param formula
	 * @param items
	 * @return
	 */
	public Double calculate(String formulaId, List<FormulaItem> params) {
		Double result = new Double(0);
		
		Formula formula = formulaRepository.findOne(formulaId);
		List<FormulaItem> items = formula.getItems();
		String formulaString = formula.getFormula();
		
		for (FormulaItem item : items) {
			switch (item.getType()) {
			case CalculateItem:
				formulaString.replaceAll(item.getText(), item.getValue());
				break;
			case ResultItem:
				Double temp = calculate(formulaId, params);
				formulaString.replaceAll(item.getText(), String.valueOf(temp));
				break;
			default:
				break;
			}
		}

		try {
			result = (Double) jsEngine.eval(formulaString);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		return result;
	}

}
