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
import net.sion.company.salary.domain.SalaryItem;
import net.sion.company.salary.sessionrepository.FormulaRepository;
import net.sion.company.salary.sessionrepository.SalaryItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormulaService {

	@Autowired
	private FormulaRepository formulaRepository;
	@Autowired
	private SalaryItemRepository salaryItemRepository;

	private ScriptEngine jsEngine;

	public FormulaService() {
		super();
		jsEngine = new ScriptEngineManager().getEngineByName("JavaScript");
	}

	/**
	 * 方法：传入一个FieldId，查找受其影响的字段
	 * 
	 * @param fieldId
	 * @return
	 */
	public Set<String> getInfluencedField(Set<String> formulaIds, String fieldId) {
		// 找到所有满足ids条件的公式
		List<Formula> formulas = (List<Formula>) formulaRepository.findAll(formulaIds);
		return findInfluenced(fieldId, formulas);
	}

	private Set<String> findInfluenced(String fieldId, List<Formula> formulas) {
		Set<String> infuencedList = new HashSet<String>();

		for (int i = formulas.size() - 1; i > 0; i--) {
			List<FormulaItem> items = formulas.get(i).getItems();
			for (FormulaItem formulaItem : items) {
				if (fieldId.equals(formulaItem.getFieldId())) {
					infuencedList.add(formulas.get(i).getResultFieldId());
					formulas.remove(i);
				}
			}
		}
		List<String> temp = new ArrayList<String>();
		for (String string : infuencedList) {
			temp.addAll(this.findInfluenced(string, formulas));
		}
		infuencedList.addAll(temp);
		return infuencedList;
	}

	/**
	 * 方法：传入 FieldId和值的集合，返回公式结果
	 * 
	 * @param formulaIds
	 *            公式Id集合
	 * @param params
	 *            fieldId和value键值对
	 * @return fieldId和Value键值对
	 * @throws Exception
	 */
	public Map<String, Double> caculateFormulas(Set<String> formulaIds, Map<String, Double> params) throws Exception {
		Map<String, Double> result = new HashMap<String, Double>();
		List<Formula> formulas = (List<Formula>) formulaRepository.findAll(formulaIds);
		// 先过滤掉Params中计算项的值，防止“修改”时旧值干扰
		for (Formula formula : formulas) {
			params.remove(formula.getResultFieldId());
		}
		
		for (Formula formula : formulas) {
			Double value=this.calculate(formula, formulas, params);
			SalaryItem sItem=salaryItemRepository.findOne(formula.getResultFieldId());
			
			result.put(formula.getResultFieldId(), sItem.decimal(sItem.getCarryType(), sItem.getPrecision(), value));
		}

		return result;
	}

	/**
	 * 取得要计算某字段值时公式中所有需要的变量字段 （向下递归）
	 * 
	 * @param formulaId
	 *            待计算的字段ID
	 * @return
	 */
	public List<FormulaItem> getFormulaItems(String formulaId) {
		Formula formula = formulaRepository.findOne(formulaId);
		List<FormulaItem> items = formula.getItems();
		for (FormulaItem item : items) {
			if (FormulaType.Calculate.equals(item.getType())) {
				Formula subFormula = formulaRepository.findByResultFieldId(item.getFieldId());
				if (subFormula != null) {
					items.addAll(this.getFormulaItems(subFormula.getId()));
				}
			}
		}
		return items;
	}

	/**
	 * 从集合中找到指定FieldId做为Result的公式
	 * 
	 * @param formulas
	 * @param fieldId
	 * @return
	 */
	private Formula findFormulaInList(List<Formula> formulas, String fieldId) {
		for (Formula formula : formulas) {
			if (fieldId.equals(formula.getResultFieldId())) {
				return formula;
			}
		}
		return null;
	}

	/**
	 * 通过公式计算, 返回计算结果
	 * 
	 * @param formula
	 * @param items
	 * @return
	 */
	public Double calculate(Formula formula, List<Formula> formulas, Map<String, Double> params) {
		Double result = new Double(0);

		List<FormulaItem> items = formula.getItems();
		String formulaString = formula.getFormula();

		
		for (FormulaItem item : items) {
			if (FormulaType.Calculate.equals(item.getType())) {
				Formula tmpFormula = this.findFormulaInList(formulas, item.getFieldId());
				Double value = params.get(item.getFieldId());
				// 这不是个公式，应该从Param中取值。或者这是个公式，但Param已经存在之前计算出来的结果，也是从param中取值。
				if (tmpFormula == null || value != null) {
					formulaString = formulaString.replaceAll(("\\[".concat(item.getText()).concat("\\]")), value+"");
				} else {
					// 这是个公式，递归计算结果，并把结果加入到Params中以便于后续运算可能会被用到
					Double temp = calculate(this.findFormulaInList(formulas, item.getFieldId()), formulas, params);
					params.put(item.getFieldId(), temp);
					formulaString = formulaString.replaceAll(("\\[".concat(item.getText()).concat("\\]")), String.valueOf(temp));
				}
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
			// TODO
			// case Calculate:
			// formulaString.replaceAll(item.getText(), item.getValue());
			// break;
			// case Result:
			// Double temp = calculate(formulaId, params);
			// formulaString.replaceAll(item.getText(), String.valueOf(temp));
			// break;
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
	 * 
	 * @param formula
	 *            公式内容
	 * @param result
	 *            公式结果项
	 * @param items
	 *            公式计算项
	 */
	public Formula create(String formula, Map<String, String> result, Map<String, String> items) {
		List<FormulaItem> formulaItems = new ArrayList<FormulaItem>();
		// TODO save type of Calculate item.
		for (Map.Entry<String, String> entry : items.entrySet()) {
			FormulaItem item = new FormulaItem();
			String key = entry.getKey();
			String value = entry.getValue();
			if ("id".equals(key)) {
				item.setFieldId(value);
			} else if ("name".equals(key)) {
				item.setText(value);
			}
			item.setType(FormulaType.Calculate);
			formulaItems.add(item);
		}
		// TODO save type of Result item.
		String resultFieldId = result.get("id");
		String resultName = result.get("name");

		// FormulaItem resultItem = new FormulaItem(resultFieldId, resultName,
		// FormulaType.Result);
		// formulaItems.add(resultItem);

		Formula f = new Formula(formula, formulaItems, resultFieldId);

		return formulaRepository.save(f);
	}

	/**
	 * 保存公式
	 * 
	 * @param f
	 *            公式domain
	 */
	public Formula create(Formula f) {

		return formulaRepository.save(f);
	}

}
