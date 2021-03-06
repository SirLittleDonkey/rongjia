package com.wyait.manage.entity.manufacture;

import java.util.Date;

public class WorkVO {
    private Integer prodPlanId;
    private String userName;
    private String planDate;
    private Double planHour;
    private Integer planQty;
    private String startTime;
    private String invCode;
    private String invName;
    private String invStd;
    private String procedureCode;
    private String procedureName;
    private String operationInstruction;
    private String operationDrawing;
    private Integer realQty;
    private String pdfPath;
    private String state;
    private Integer qualifiedqty;
    private Integer unqualifiedqty;

    public Integer getProdPlanId() {
        return prodPlanId;
    }

    public void setProdPlanId(Integer prodPlanId) {
        this.prodPlanId = prodPlanId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getPlanQty() {
        return planQty;
    }

    public void setPlanQty(Integer planQty) {
        this.planQty = planQty;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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

    public Integer getRealQty() {
        return realQty;
    }

    public void setRealQty(Integer realQty) {
        this.realQty = realQty;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
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

    public Integer getQualifiedqty() {
        return qualifiedqty;
    }

    public void setQualifiedqty(Integer qualifiedqty) {
        this.qualifiedqty = qualifiedqty;
    }

    public Integer getUnqualifiedqty() {
        return unqualifiedqty;
    }

    public void setUnqualifiedqty(Integer unqualifiedqty) {
        this.unqualifiedqty = unqualifiedqty;
    }

    public String getOperationInstruction() {
        return operationInstruction;
    }

    public void setOperationInstruction(String operationInstruction) {
        this.operationInstruction = operationInstruction;
    }

    public String getOperationDrawing() {
        return operationDrawing;
    }

    public void setOperationDrawing(String operationDrawing) {
        this.operationDrawing = operationDrawing;
    }
}
