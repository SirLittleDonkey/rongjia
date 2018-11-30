package com.wyait.manage.service.manufacture;

import com.wyait.manage.entity.manufacture.EndInspectVO;
import com.wyait.manage.entity.manufacture.FirstInspectVO;
import com.wyait.manage.utils.PageDataResult;

import java.util.Map;

public interface QualityService {
    public PageDataResult getDailyWorkPlan(Integer page, Integer limit);

    FirstInspectVO getFirstInspectData(Integer prodPlanId);

    EndInspectVO getEndInspectData(Integer prodPlanId);

    Map<String, Object> firstInspect(Integer prodPlanId);

    Map<String, Object> firstInspectCancel(Integer prodPlanId);

    void endInspect(Integer prodPlanId, Integer FqualifiedQty, Integer FunqualifiedQty);

    void endInspectCancel(Integer prodPlanId);
}
