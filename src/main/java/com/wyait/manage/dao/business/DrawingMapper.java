package com.wyait.manage.dao.business;

import com.wyait.manage.entity.business.DrawingDTO;
import com.wyait.manage.entity.business.DrawingSearchDTO;
import com.wyait.manage.entity.business.DrawingSetDTO;
import com.wyait.manage.pojo.business.Drawing;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DrawingMapper {
    List<DrawingDTO> getDrawings(@Param("DrawingSearchDTO") DrawingSearchDTO drawingSearchDTO);
    Drawing findDrawingByInvCodeAndProcedureCode(@Param("invCode") String invCode, @Param("procedureCode") String procedureCode);
    int insert(DrawingSetDTO drawingSetDTO);
    int insertTotallyNew(DrawingSetDTO drawingSetDTO);
    Drawing getDrawing(Integer id);
    Drawing findDrawingByIPV(@Param("invCode") String invCode, @Param("procedureCode") String procedureCode, @Param("version") Integer version);
    int update(DrawingSetDTO drawingSetDTO);
    int setDelDrawing(@Param("id") Integer id, @Param("isDel") Integer isDel);
}
