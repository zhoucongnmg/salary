/**
 * TaxItem.java
 */
package net.sion.company.salary.domain;

/**
 * @author zhangligang
 *	薪资管理--个税设置--个税区段设置
 */
public class TaxItem {
	String id;
	Double start;
	Double end;
	Double rate;
	Double fastNumber;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Double getStart() {
		return start;
	}
	public void setStart(Double start) {
		this.start = start;
	}
	public Double getEnd() {
		return end;
	}
	public void setEnd(Double end) {
		this.end = end;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Double getFastNumber() {
		return fastNumber;
	}
	public void setFastNumber(Double fastNumber) {
		this.fastNumber = fastNumber;
	}
}
