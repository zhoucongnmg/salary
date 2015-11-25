package net.sion.company.salary.service;

import java.util.List;

import net.sion.company.salary.domain.Formula;
import net.sion.company.salary.domain.FormulaItem;

import org.springframework.stereotype.Service;

@Service
public class FormulaService {
	
	
	/**
	 * 通过公式计算返回计算结果
	 * @param formula 
	 * @param items
	 * @return
	 */
	public Double calculate(String formulaId,List<Double> items) {
		return null;
	}
	
	/**
	 * 通过公式级联计算
	 * @param formula 
	 * @param items
	 * @return
	 */
	public List<FormulaItem> calculateCascade(String fieldId,List<FormulaItem> calculateItems) {
		
		//TODO 级联查找传入的计算项与其关联的公式结果项，返回关联的公式
		List<Formula> formulas = this.findCascadeFormulaByFieldId(fieldId);
		
		for (Formula formula  : formulas) {
			//TODO 传入计算项 计算公式结果
			//TODO 将计算结果返回
		}
		
		return null;
	}
	
	/**
	 * 通过发送改变的计算项fieldId 查找与其关联的公式计算项
	 * （一般用于在修改公式中某一计算项之前，获得该项变更与其关联影响的其他计算项，以便在计算时将其他计算项传入级联计算）
	 * @param fieldId
	 * @return
	 */
	public List<FormulaItem> findCascadeCalculateItemByFieldId(String fieldId) {
		
		//TODO 查找计算项关联的公式
		
		//TODO 递归查找公式结果是否是其他公式的计算项（如果是，则将与其级联的其他公式的全部计算项都添加到返回结果）
		return null;
	}
	
	
	/**
	 * 通过发送改变的计算项fieldId 查找与其关联的公式结果项
	 * @param fieldId
	 * @return
	 */
	public List<Formula> findCascadeFormulaByFieldId(String fieldId) {
		
		//TODO 查找计算项关联的公式
		
		//TODO 递归查找公式结果是否关联其他公式（如果是，则将与其关联的其他公式返回）
		return null;
	}
	
	
}
