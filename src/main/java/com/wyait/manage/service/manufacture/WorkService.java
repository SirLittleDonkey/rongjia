package com.wyait.manage.service.manufacture;

import com.wyait.manage.entity.manufacture.WorkVO;
import com.wyait.manage.utils.PageDataResult;

import javax.servlet.http.HttpServletRequest;

public interface WorkService {
    String getWorkStationCode(HttpServletRequest request);

    PageDataResult getDailyWorkPlan(Integer page, Integer limit, String workStationCode);

    WorkVO startWork(Integer prodPlanId);
}
