package com.wyait.manage.service.business;

import com.wyait.manage.entity.business.oiSearchDTO;
import com.wyait.manage.entity.business.oiSetDTO;
import com.wyait.manage.pojo.business.OperationInstruction;
import com.wyait.manage.utils.PageDataResult;
import javafx.beans.property.ObjectProperty;

public interface OperationInstructionService {
    public PageDataResult getOperationInstructions(Integer page, Integer limit, oiSearchDTO oiSearchDTO);
    public String setOperationInstruction(oiSetDTO oiSetDTO);
    public OperationInstruction getOperationInstruction(Integer id);
}
