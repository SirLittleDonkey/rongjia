package com.wyait.manage.pojo.basic;

import com.wyait.manage.pojo.BasePojo;

import java.util.Date;

public class Board extends BasePojo {
    private static final long serialVersionUID = -3096736268081409238L;
    private Integer id;

    private String factoryCode;

    private String workShopCode;

    private String boardCode;

    private String ipAddress;

    private Integer insertUid;

    private Date insertTime;

    private Date updateTime;

    private Boolean isDel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFactoryCode() {
        return factoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode;
    }

    public String getWorkShopCode() {
        return workShopCode;
    }

    public void setWorkShopCode(String workShopCode) {
        this.workShopCode = workShopCode;
    }

    public String getBoardCode() {
        return boardCode;
    }

    public void setBoardCode(String boardCode) {
        this.boardCode = boardCode;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public Integer getInsertUid() {
        return insertUid;
    }

    @Override
    public void setInsertUid(Integer insertUid) {
        this.insertUid = insertUid;
    }

    @Override
    public Date getInsertTime() {
        return insertTime;
    }

    @Override
    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

}
