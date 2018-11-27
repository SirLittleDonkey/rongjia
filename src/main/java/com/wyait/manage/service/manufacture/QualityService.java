package com.wyait.manage.service.manufacture;

import com.wyait.manage.entity.manufacture.EndInspectVO;
import com.wyait.manage.entity.manufacture.FirstInspectVO;
import com.wyait.manage.utils.PageDataResult;

public interface QualityService {
    public PageDataResult getDailyWorkPlan(Integer page, Integer limit, String workStationCode);

    FirstInspectVO getFirstInspectData(Integer prodPlanId);

    EndInspectVO getEndInspectData(Integer prodPlanId);

    void firstInspect(Integer prodPlanId);

    void endInspect(Integer prodPlanId);
}
