package com.wyait.manage.service.manufacture;

import com.wyait.manage.entity.manufacture.PlanProcessSearchDTO;
import com.wyait.manage.utils.PageDataResult;

public interface PlanProcessService {
    PageDataResult getPlanProcessList(Integer page, Integer limit, PlanProcessSearchDTO planProcessSearchDTO);
}
