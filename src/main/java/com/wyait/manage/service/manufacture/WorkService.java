package com.wyait.manage.service.manufacture;

import com.wyait.manage.entity.manufacture.WorkVO;
import com.wyait.manage.utils.PageDataResult;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

public interface WorkService {
    String getWorkStationCode(HttpServletRequest request);

    PageDataResult getDailyWorkPlan(Integer page, Integer limit, String workStationCode) throws ParseException;

    PageDataResult getWeeklyWorkPlan(Integer page, Integer limit, String workStationCode);

    WorkVO startWork(Integer prodPlanId) throws ParseException;

    WorkVO quality(Integer prodPlanId);

    WorkVO unquality(Integer prodPlanId);
}
