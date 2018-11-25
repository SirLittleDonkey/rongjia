package com.wyait.manage.entity.manufacture;

public class WorkPlanQualityDTO {
    private Integer prodPlanId;
    private String planDate;
    private String invCode;
    private String invName;
    private String invStd;
    private String procedureName;
    private Integer planQty;
    private Integer realQty;
    private Boolean hasInspected;

    public Integer getProdPlanId() {
        return prodPlanId;
    }

    public void setProdPlanId(Integer prodPlanId) {
        this.prodPlanId = prodPlanId;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
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

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public Integer getPlanQty() {
        return planQty;
    }

    public void setPlanQty(Integer planQty) {
        this.planQty = planQty;
    }

    public Integer getRealQty() {
        return realQty;
    }

    public void setRealQty(Integer realQty) {
        this.realQty = realQty;
    }

    public Boolean getHasInspected() {
        return hasInspected;
    }

    public void setHasInspected(Boolean hasInspected) {
        this.hasInspected = hasInspected;
    }
}
