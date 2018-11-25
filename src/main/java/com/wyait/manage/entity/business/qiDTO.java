package com.wyait.manage.entity.business;

public class qiDTO {
    private Integer id;
    private String invCode;
    private String invName;
    private String invStd;
    private String procedureCode;
    private String procedureName;
    private String version;
    private String filepath;
    private Boolean hasUpload;
    private Boolean isDel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Boolean getHasUpload() {
        return hasUpload;
    }

    public void setHasUpload(Boolean hasUpload) {
        this.hasUpload = hasUpload;
    }

    public Boolean getDel() {
        return isDel;
    }

    public void setDel(Boolean del) {
        isDel = del;
    }
}
