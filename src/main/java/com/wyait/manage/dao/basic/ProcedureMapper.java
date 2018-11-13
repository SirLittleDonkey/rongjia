package com.wyait.manage.dao.basic;

import com.wyait.manage.entity.basic.ProcedureDTO;
import com.wyait.manage.entity.basic.ProcedureSearchDTO;
import com.wyait.manage.entity.basic.ProcedureVO;
import com.wyait.manage.pojo.basic.Procedure;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProcedureMapper {

    List<ProcedureDTO> getProcedures(@Param("procedureSearchDTO")ProcedureSearchDTO procedureSearchDTO);

    Procedure findProcedureByProcedureCode(String procedureCode);

    int updateByPrimaryKeySelective(Procedure procedure);

    int insert(Procedure procedure);

    ProcedureVO getProcedure(Integer id);

    int setDelProcedure(@Param("id") Integer id, @Param("isDel") Integer isDel);
}
