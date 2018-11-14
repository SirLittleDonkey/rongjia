package com.wyait.manage.service.business;

import com.wyait.manage.entity.business.oiSearchDTO;
import com.wyait.manage.utils.PageDataResult;

public interface OperationInstructionService {
    public PageDataResult getOperationInstructions(Integer page, Integer limit, oiSearchDTO oiSearchDTO);
}
