/**
 * SalaryService.java
 */
package net.sion.company.salary.service;

import java.util.Map;

import net.sion.company.salary.domain.PayrollItem;

import org.springframework.stereotype.Service;

/**
 * @author zhangligang
 *	薪资管理服务类
 */
@Service
public class SalaryService {

	/**
	 * 生成工资记录
	 * @param personMap  待发工资人员列表信息
	 * @return 发放记录列表
	 */
	public Map<String,Object> computeSalary(Map<String,Object> personMap,String accountId){
		processAccount(personMap);
		processPersonAccountItem(personMap);
		processInsure(personMap);
		processLevel(personMap);
		processTax(personMap);
		
		return null;
	}
	
	/**
	 * 计算关联薪资项
	 * @param 工资明细
	 * @param 账套id
	 * @param 当前薪资项
	 * @return 发放记录列表
	 */
	public Map<String,Object> calculateAssociatedSalary(PayrollItem payItem,String accountId,String activeItemId){
		
		return null;
	}
	
	/**
	 * 根据套帐规则生成记录
	 * @param personMap 待发工资人员列表信息
	 * @return 工资列表
	 */
	private Map<String,Object> processAccount(Map<String,Object> personMap) {
		return null;
	}
	
	/**
	 * 根据套帐中人员薪资设置调整生成的工资
	 * 如果套帐中设置了人员薪资值，则覆盖套帐中的默认值
	 * @param personMap 待发工资信息
	 * @return 工资列表
	 */
	private Map<String,Object> processPersonAccountItem(Map<String,Object> personMap) {
		return null;
	}
	
	/**
	 * 根据保险信息处理工资列表
	 * @param personMap 待发工资信息
	 * @return 包含保险信息的工资列表
	 */
	private Map<String,Object> processInsure(Map<String,Object> personMap) {
		return null;
	}
	
	/**
	 * 根据薪资层次信息处理工资列表
	 * @param personMap 待发工资信息
	 * @return 工资列表
	 */
	private Map<String,Object> processLevel(Map<String,Object> personMap) {
		return null;
	}
	
	/**
	 * 根据个税设置处理工资列表
	 * @param personMap 待发工资信息
	 * @return 工资列表
	 */
	private Map<String,Object> processTax(Map<String,Object> personMap) {
		return null;
	}
}
