package com.wyait.manage.pojo.basic;

import com.wyait.manage.pojo.BasePojo;

public class Procedure extends BasePojo {
    private String procedureCode;
    private String procedureName;

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
}
