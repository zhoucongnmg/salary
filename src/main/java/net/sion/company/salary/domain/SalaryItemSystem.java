package net.sion.company.salary.domain;

public enum SalaryItemSystem {
    ITEM1(new SalaryItem("ststem1", "名称1", "字段1", "系统提取项", false, 0, true, true, "")),
    ITEM2(new SalaryItem("ststem2", "名称2", "字段2", "系统提取项", false, 0, true, true, ""));
    private SalaryItem salaryItem;

    private SalaryItemSystem(SalaryItem salaryItem) {
        this.salaryItem = salaryItem;
    }
    public SalaryItem getSalaryItem() {
        return salaryItem;
    }
    public void setSalaryItem(SalaryItem salaryItem) {
        this.salaryItem = salaryItem;
    }
}