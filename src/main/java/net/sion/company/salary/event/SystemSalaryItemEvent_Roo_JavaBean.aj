// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package net.sion.company.salary.event;

import java.util.Date;
import java.util.List;
import net.sion.company.salary.domain.SystemSalaryItem;
import net.sion.company.salary.event.SystemSalaryItemEvent;

privileged aspect SystemSalaryItemEvent_Roo_JavaBean {
    
    public SystemSalaryItem SystemSalaryItemEvent.getItem() {
        return this.item;
    }
    
    public void SystemSalaryItemEvent.setItem(SystemSalaryItem item) {
        this.item = item;
    }
    
    public String SystemSalaryItemEvent.getUserId() {
        return this.userId;
    }
    
    public void SystemSalaryItemEvent.setUserId(String userId) {
        this.userId = userId;
    }
    
    public String SystemSalaryItemEvent.getPersonId() {
        return this.personId;
    }
    
    public void SystemSalaryItemEvent.setPersonId(String personId) {
        this.personId = personId;
    }
    
    public String SystemSalaryItemEvent.getCompanyId() {
        return this.companyId;
    }
    
    public void SystemSalaryItemEvent.setCompanyId(String companyId) {
        this.companyId = companyId;
    }
    
    public String SystemSalaryItemEvent.getDeptId() {
        return this.deptId;
    }
    
    public void SystemSalaryItemEvent.setDeptId(String deptId) {
        this.deptId = deptId;
    }
    
    public Date SystemSalaryItemEvent.getDate() {
        return this.date;
    }
    
    public void SystemSalaryItemEvent.setDate(Date date) {
        this.date = date;
    }
    
    public List<SystemSalaryItem> SystemSalaryItemEvent.getRegistItems() {
        return this.registItems;
    }
    
    public void SystemSalaryItemEvent.setRegistItems(List<SystemSalaryItem> registItems) {
        this.registItems = registItems;
    }
    
    public SystemSalaryItemEventType SystemSalaryItemEvent.getType() {
        return this.type;
    }
    
    public void SystemSalaryItemEvent.setType(SystemSalaryItemEventType type) {
        this.type = type;
    }
    
    public Double SystemSalaryItemEvent.getValue() {
        return this.value;
    }
    
    public void SystemSalaryItemEvent.setValue(Double value) {
        this.value = value;
    }
    
}