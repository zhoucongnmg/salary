package net.sion.company.salary.domain;

public class ReadPayRollParameter {
	private Integer page;
	private Integer start;
	private Integer limit;
	private String state;
	private String subject;
	private String month;
	private String socialCostMonth;

	public ReadPayRollParameter(Integer page, Integer start, Integer limit, String state, String subject, String month,
			String socialCostMonth) {
		this.page = page;
		this.start = start;
		this.limit = limit;
		this.state = state;
		this.subject = subject;
		this.month = month;
		this.socialCostMonth = socialCostMonth;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getSocialCostMonth() {
		return socialCostMonth;
	}

	public void setSocialCostMonth(String socialCostMonth) {
		this.socialCostMonth = socialCostMonth;
	}
}