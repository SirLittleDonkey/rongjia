package com.wyait.manage.dao.business;

import com.wyait.manage.entity.business.OperationDrawingDTO;
import com.wyait.manage.entity.business.OperationDrawingSearchDTO;
import com.wyait.manage.entity.business.OperationDrawingSetDTO;
import com.wyait.manage.pojo.business.OperationDrawing;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OperationDrawingMapper {
    List<OperationDrawingDTO> getOperationDrawings(@Param("OperationDrawingSearchDTO") OperationDrawingSearchDTO operationDrawingSearchDTO);
    OperationDrawing findOperationDrawingByInvCodeAndProcedureCode(@Param("invCode") String invCode, @Param("procedureCode") String procedureCode);
    int insert(OperationDrawingSetDTO operationDrawingSetDTO);
    int insertTotallyNew(OperationDrawingSetDTO operationDrawingSetDTO);
    OperationDrawing getOperationDrawing(Integer id);
    OperationDrawing findOperationDrawingByIPV(@Param("invCode") String invCode, @Param("procedureCode") String procedureCode, @Param("version") Integer version);
    int update(OperationDrawingSetDTO operationDrawingSetDTO);
    int setDelOperationDrawing(@Param("id") Integer id, @Param("isDel") Integer isDel);
}
