package com.wyait.manage.entity.manufacture;

public class WorkPlanDTO {
    private Integer prodPlanId;
    private String plandate;
    private String invCode;
    private String invName;
    private String invStd;
    private String procedureCode;
    private String procedureName;
    private Double planHour;
    private Integer planQty;
    private Integer qualifiedQty;
    private Integer unqualifiedQty;
    private Boolean hasInspected;
    private String state;

    public Integer getProdPlanId() {
        return prodPlanId;
    }

    public void setProdPlanId(Integer prodPlanId) {
        this.prodPlanId = prodPlanId;
    }

    public String getPlandate() {
        return plandate;
    }

    public void setPlandate(String plandate) {
        this.plandate = plandate;
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

    public String getInvStd() {
        return invStd;
    }

    public void setInvStd(String invStd) {
        this.invStd = invStd;
    }

    public String getProcedureCode() {
        return procedureCode;
    }

    public void setProcedureCode(String procedureCode) {
        this.procedureCode = procedureCode;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public Double getPlanHour() {
        return planHour;
    }

    public void setPlanHour(Double planHour) {
        this.planHour = planHour;
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

    public Integer getUnqualifiedQty() {
        return unqualifiedQty;
    }

    public void setUnqualifiedQty(Integer unqualifiedQty) {
        this.unqualifiedQty = unqualifiedQty;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getHasInspected() {
        return hasInspected;
    }

    public void setHasInspected(Boolean hasInspected) {
        this.hasInspected = hasInspected;
    }
}
