package net.sion.company.salary.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.sion.company.salary.domain.Formula;
import net.sion.company.salary.domain.FormulaItem;
import net.sion.company.salary.domain.FormulaItem.FormulaType;
import net.sion.company.salary.sessionrepository.FormulaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public Set<String> getInfluencedField(Set<String> formulaIds,String fieldId){
		//找到所有满足ids条件的公式
		List<Formula> formulas=(List<Formula>) formulaRepository.findAll(formulaIds);
		return findInfluenced(fieldId, formulas);
	}

	private Set<String> findInfluenced(String fieldId,List<Formula> formulas) {
		Set<String> infuencedList=new HashSet<String>();
		
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
	 * 方法：传入 FieldId和值的集合，返回受其影响的公式及结果
	 * 
	 * @param formulaIds
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> caculateFormulas(Set<String> formulaIds,Map<String,String>params) throws Exception{
		Map<String, String> result=new HashMap<String,String>();
		for (String formulaId : formulaIds) {
			Formula formula = formulaRepository.findOne(formulaId);
			List<FormulaItem> items=this.getFormulaItems(formulaId);
			
			for (FormulaItem formulaItem : items) {
				String value=params.get(formulaItem.getFieldId());
				if(value==null){
					throw new Exception("无法确定公式中变量的值："+formulaItem.getText());
				}
				formulaItem.setValue(value);
			}
			
			result.put(formula.getResultFieldId(),String.valueOf(this.calculate(formulaId, items)));
		}
		
		return result;
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
			if (FormulaType.Result.equals(item.getType())) {
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
			case Calculate:
				formulaString.replaceAll(item.getText(), item.getValue());
				break;
			case Result:
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
	
	
	/**
	 * 保存公式
	 * @param formula 公式内容
	 * @param result	公式结果项
	 * @param items	公式计算项
	 */
	public Formula create(String formula,Map<String,String> result,Map<String,String> items){
		List<FormulaItem> formulaItems = new ArrayList<FormulaItem>();
		//TODO save type of Calculate item.
		for (Map.Entry<String, String> entry : items.entrySet()) {
			FormulaItem item = new FormulaItem();
			String key = entry.getKey();
			String value = entry.getValue();
			if ("id".equals(key)) {
				item.setFieldId(value);
			}else if ("name".equals(key)) {
				item.setText(value);
			}
			item.setType(FormulaType.Calculate);
			formulaItems.add(item);
		}
		//TODO save type of Result item.
		String resultFieldId = result.get("id");
		String resultName = result.get("name");
		
		FormulaItem resultItem = new FormulaItem(resultFieldId,resultName,FormulaType.Result);
		formulaItems.add(resultItem);
		
		Formula f = new Formula(formula,formulaItems,resultFieldId);
		
		return formulaRepository.save(f);
	}
	
	/**
	 * 保存公式
	 * @param f 公式domain
	 */
	public Formula create(Formula f){
		
		
		return formulaRepository.save(f);
	}
	
}
