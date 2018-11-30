package com.wyait.manage.entity.manufacture;

public class WorkVO2 {
    private Integer prodPlanId;
    private String startTime;
    private long lastTime;
    private Boolean ispause;
    private Boolean hascomplete;
    private Boolean hasEndInspected;

    public Integer getProdPlanId() {
        return prodPlanId;
    }

    public void setProdPlanId(Integer prodPlanId) {
        this.prodPlanId = prodPlanId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public Boolean getIspause() {
        return ispause;
    }

    public void setIspause(Boolean ispause) {
        this.ispause = ispause;
    }

    public Boolean getHascomplete() {
        return hascomplete;
    }

    public void setHascomplete(Boolean hascomplete) {
        this.hascomplete = hascomplete;
    }

    public Boolean getHasEndInspected() {
        return hasEndInspected;
    }

    public void setHasEndInspected(Boolean hasEndInspected) {
        this.hasEndInspected = hasEndInspected;
    }
}
