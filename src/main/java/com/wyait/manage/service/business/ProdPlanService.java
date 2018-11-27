package com.wyait.manage.service.business;

import com.wyait.manage.entity.business.ProdPlanSearchDTO;
import com.wyait.manage.entity.business.ProdPlanVO;
import com.wyait.manage.entity.business.ppSetDTO;
import com.wyait.manage.utils.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface ProdPlanService {
    public PageDataResult getProdPlans(Integer page, Integer limit, ProdPlanSearchDTO prodPlanSearchDTO);

    String setProdPlan(ppSetDTO ppSetDTO);

    ProdPlanVO getProdPlan(Integer id);

    Map<String, Object> uploadProdPlan(MultipartFile file) throws IOException;
}
