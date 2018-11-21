package com.wyait.manage.service.manufacture;

import com.wyait.manage.utils.PageDataResult;

public interface QualityService {
    public PageDataResult getDailyWorkPlan(Integer page, Integer limit, String workStationCode);
}
