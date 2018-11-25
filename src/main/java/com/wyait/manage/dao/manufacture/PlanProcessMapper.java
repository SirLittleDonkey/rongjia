package com.wyait.manage.dao.manufacture;

import com.wyait.manage.entity.manufacture.PlanProcessDTO;
import com.wyait.manage.entity.manufacture.PlanProcessSearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlanProcessMapper {
    List<PlanProcessDTO> getPlanProcessList(@Param("planProcessSearchDTO")PlanProcessSearchDTO planProcessSearchDTO);
}
