package com.wyait.manage.service.manufacture;

import com.wyait.manage.entity.manufacture.WorkVO;
import com.wyait.manage.utils.PageDataResult;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Map;

public interface WorkService {
    String getWorkStationCode(HttpServletRequest request);

    PageDataResult getDailyWorkPlan(Integer page, Integer limit, String workStationCode) throws ParseException;

    PageDataResult getWeeklyWorkPlan(Integer page, Integer limit, String workStationCode);

    WorkVO startWork(Integer prodPlanId) throws ParseException;

    WorkVO quality(Integer prodPlanId) throws ParseException;

    WorkVO unquality(Integer prodPlanId) throws ParseException;

    Map<String, Object> pause(Integer prodPlanId) throws ParseException;

    Map<String, Object> pauseCancel(Integer prodPlanId) throws ParseException;

    Map<String, Object> complete(Integer prodPlanId);
}
