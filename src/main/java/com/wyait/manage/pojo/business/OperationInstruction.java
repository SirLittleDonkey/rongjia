package com.wyait.manage.pojo.business;

import com.wyait.manage.pojo.BasePojo;

public class OperationInstruction extends BasePojo {
    private Integer id;
    private String invCode;
    private String procedurecode;
    private String filepath;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvCode() {
        return invCode;
    }

    public void setInvCode(String invCode) {
        this.invCode = invCode;
    }

    public String getProcedurecode() {
        return procedurecode;
    }

    public void setProcedurecode(String procedurecode) {
        this.procedurecode = procedurecode;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
