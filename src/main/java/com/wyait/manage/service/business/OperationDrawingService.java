package com.wyait.manage.service.business;

import com.wyait.manage.entity.business.OperationDrawingSearchDTO;
import com.wyait.manage.entity.business.OperationDrawingSetDTO;
import com.wyait.manage.pojo.business.OperationDrawing;
import com.wyait.manage.utils.PageDataResult;

public interface OperationDrawingService {
    public PageDataResult getOperationDrawings(Integer page, Integer limit, OperationDrawingSearchDTO operationDrawingSearchDTO);
    public String setOperationDrawing(OperationDrawingSetDTO operationDrawingSetDTO);
    public OperationDrawing getOperationDrawing(Integer id);
    public String setDelOperationDrawing(Integer id, Integer isDel);
}
