package com.wyait.manage.dao.business;

import com.wyait.manage.entity.business.oiDTO;
import com.wyait.manage.entity.business.oiSearchDTO;
import com.wyait.manage.entity.business.oiSetDTO;
import com.wyait.manage.pojo.business.OperationInstruction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OperationInstructionMapper {
    List<oiDTO> getOIs(@Param("oiSearchDTO") oiSearchDTO oiSearchDTO);
    OperationInstruction findOperationInstructionByInvCodeAndProcedureCode(@Param("invCode")String invCode, @Param("procedureCode")String procedureCode);
    int insert(oiSetDTO oiSetDTO);
    int insertTotallyNew (oiSetDTO oiSetDTO);
    OperationInstruction getOperationInstruction(Integer id);
    OperationInstruction findOperationInstructionByIPV(@Param("invCode")String invCode, @Param("procedureCode")String procedureCode, @Param("version")Integer version);
    int update(oiSetDTO oiSetDTO);
}
