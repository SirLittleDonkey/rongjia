package com.wyait.manage.dao.manufacture;

import com.wyait.manage.entity.manufacture.WorkPlanDTO;
import com.wyait.manage.entity.manufacture.WorkVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface WorkMapper {
    String getWorkStationCodeByIpAddress(String ipAddress);

    List<WorkPlanDTO> getDailyWorkPlan(@Param("workStationCode")String workStationCode);

    Date getStartTime(@Param("prodPlanId")Integer prodPlanId);

    int setStartTime(@Param("prodPlanId")Integer prodPlanId);

    WorkVO getWorkVO(@Param("prodPlanId")Integer prodPlanId);
}
