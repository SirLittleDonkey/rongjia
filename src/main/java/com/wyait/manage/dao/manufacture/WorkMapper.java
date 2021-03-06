package com.wyait.manage.dao.manufacture;

import com.wyait.manage.entity.manufacture.WorkPlanDTO;
import com.wyait.manage.entity.manufacture.WorkVO;
import com.wyait.manage.entity.manufacture.WorkVO2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface WorkMapper {
    String getWorkStationCodeByIpAddress(String ipAddress);

    List<WorkPlanDTO> getDailyWorkPlan(@Param("workStationCode")String workStationCode);

    List<WorkPlanDTO> getWeeklyWorkPlan(@Param("workStationCode")String workStationCode);

    Date getStartTime(@Param("prodPlanId")Integer prodPlanId);

    int setStartTime(@Param("prodPlanId")Integer prodPlanId);

    int quality(@Param("prodPlanId")Integer prodPlanId);

    int unquality(@Param("prodPlanId")Integer prodPlanId);

    WorkVO getWorkVO(@Param("prodPlanId")Integer prodPlanId);

    WorkVO2 getWorkVO2(@Param("prodPlanId")Integer prodPlanId);

    void setWork(@Param("lastTime")long lastTime, @Param("prodPlanId")Integer prodPlanId);

    void setWorkNotPause(@Param("prodPlanId")Integer prodPlanId);

    void setWorkComplete(@Param("prodPlanId")Integer prodPlanId);
}
