package net.sion.company.salary.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

/**
 * @author niex
 *	公式
 */
public class Formula {
	@Id
	String id;
	
	List<FormulaItem> items;
	
	FormulaItem result;

	public Formula() {
		
	}
	
	public Formula(String id, List<FormulaItem> items, FormulaItem result) {
		super();
		this.id = id;
		this.items = items;
		this.result = result;
	}
	
	
	public String toString() {
		return items.toString();
		
	}
	
	public List<FormulaItem> getCalculateItems() {
		List<FormulaItem> calculateItems = new ArrayList<FormulaItem>();
		for (FormulaItem item : items) {
		}
		
		
		return calculateItems;
	}
	
	
	
}
