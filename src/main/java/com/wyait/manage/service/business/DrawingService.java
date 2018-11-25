package com.wyait.manage.service.business;

import com.wyait.manage.entity.business.DrawingSearchDTO;
import com.wyait.manage.entity.business.DrawingSetDTO;
import com.wyait.manage.pojo.business.Drawing;
import com.wyait.manage.utils.PageDataResult;

public interface DrawingService {
    public PageDataResult getDrawings(Integer page, Integer limit, DrawingSearchDTO drawingSearchDTO);
    public String setDrawing(DrawingSetDTO drawingSetDTO);
    public Drawing getDrawing(Integer id);
    public String setDelDrawing(Integer id, Integer isDel);
}
