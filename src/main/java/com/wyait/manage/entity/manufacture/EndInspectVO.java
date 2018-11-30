package com.wyait.manage.entity.manufacture;

public class EndInspectVO {
    private Integer prodPlanId;
    private String invCode;
    private String invName;
    private String invStd;
    private String procedureName;
    private String qualifiedInstructionFilePath;
    private String qualifiedDrawingFilePath;

    public Integer getProdPlanId() {
        return prodPlanId;
    }

    public void setProdPlanId(Integer prodPlanId) {
        this.prodPlanId = prodPlanId;
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

    public String getQualifiedInstructionFilePath() {
        return qualifiedInstructionFilePath;
    }

    public void setQualifiedInstructionFilePath(String qualifiedInstructionFilePath) {
        this.qualifiedInstructionFilePath = qualifiedInstructionFilePath;
    }

    public String getQualifiedDrawingFilePath() {
        return qualifiedDrawingFilePath;
    }

    public void setQualifiedDrawingFilePath(String qualifiedDrawingFilePath) {
        this.qualifiedDrawingFilePath = qualifiedDrawingFilePath;
    }
}
