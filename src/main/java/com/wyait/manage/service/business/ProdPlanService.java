package com.wyait.manage.service.business;

import com.wyait.manage.entity.business.ProdPlanSearchDTO;
import com.wyait.manage.utils.PageDataResult;

public interface ProdPlanService {
    public PageDataResult getProdPlans(Integer page, Integer limit, ProdPlanSearchDTO prodPlanSearchDTO);
}
