package com.wyait.manage.dao.manufacture;

import com.wyait.manage.entity.manufacture.FirstInspectVO;
import com.wyait.manage.entity.manufacture.WorkPlanDTO;
import com.wyait.manage.entity.manufacture.WorkPlanQualityDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QualityMapper {
    List<WorkPlanQualityDTO> getDailyWorkPlan(@Param("workStationCode")String workStationCode);

    FirstInspectVO getFirstInspectData(@Param("prodPlanId")Integer prodPlanId);

    int firstInspect(@Param("prodPlanId")Integer prodPlanId);
}
