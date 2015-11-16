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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getStart() {
		return start;
	}
	public void setStart(double start) {
		this.start = start;
	}
	public double getEnd() {
		return end;
	}
	public void setEnd(double end) {
		this.end = end;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getFastNumber() {
		return fastNumber;
	}
	public void setFastNumber(double fastNumber) {
		this.fastNumber = fastNumber;
	}
	double start;
	double end;
	double rate;
	double fastNumber;
	
}
