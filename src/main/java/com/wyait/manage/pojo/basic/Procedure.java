package com.wyait.manage.pojo.basic;

public class Procedure {
    private Integer id;
    private String procedureCode;
    private String procedureName;
    private Boolean isDel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean del) {
        isDel = del;
    }
}
