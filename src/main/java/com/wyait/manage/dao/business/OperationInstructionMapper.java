package com.wyait.manage.dao.business;

import com.wyait.manage.entity.business.oiDTO;
import com.wyait.manage.entity.business.oiSearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OperationInstructionMapper {
    List<oiDTO> getOIs(@Param("oiSearchDTO") oiSearchDTO oiSearchDTO);
}
