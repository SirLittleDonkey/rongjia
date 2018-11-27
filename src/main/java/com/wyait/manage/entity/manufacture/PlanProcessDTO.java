package com.wyait.manage.entity.manufacture;

public class PlanProcessDTO {
    private String cusCode;
    private String cusName;
    private String workShopCode;
    private String workShopName;
    private String workStationCode;
    private String invCode;
    private String invName;
    private String planDate;
    private Double planHour;
    private Integer planQty;
    private Integer qualifiedQty;
    private Integer unqualifiedQty;
    private String completionRate;
    private String state;

    public String getCusCode() {
        return cusCode;
    }

    public void setCusCode(String cusCode) {
        this.cusCode = cusCode;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getWorkStationCode() {
        return workStationCode;
    }

    public void setWorkStationCode(String workStationCode) {
        this.workStationCode = workStationCode;
    }

    public String getInvCode() {
        return invCode;
    }

    public void setInvCode(String invCode) {
        this.invCode = invCode;
    }

    public String getInvName() {
        return invName;
    }

    public void setInvName(String invName) {
        this.invName = invName;
    }

    public Integer getPlanQty() {
        return planQty;
    }

    public void setPlanQty(Integer planQty) {
        this.planQty = planQty;
    }

    public Integer getQualifiedQty() {
        return qualifiedQty;
    }

    public void setQualifiedQty(Integer qualifiedQty) {
        this.qualifiedQty = qualifiedQty;
    }

    public String getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(String completionRate) {
        this.completionRate = completionRate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public Double getPlanHour() {
        return planHour;
    }

    public void setPlanHour(Double planHour) {
        this.planHour = planHour;
    }

    public Integer getUnqualifiedQty() {
        return unqualifiedQty;
    }

    public void setUnqualifiedQty(Integer unqualifiedQty) {
        this.unqualifiedQty = unqualifiedQty;
    }

    public String getWorkShopCode() {
        return workShopCode;
    }

    public void setWorkShopCode(String workShopCode) {
        this.workShopCode = workShopCode;
    }

    public String getWorkShopName() {
        return workShopName;
    }

    public void setWorkShopName(String workShopName) {
        this.workShopName = workShopName;
    }
}
