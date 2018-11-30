package com.wyait.manage.dao.manufacture;

import com.wyait.manage.entity.manufacture.EndInspectVO;
import com.wyait.manage.entity.manufacture.FirstInspectVO;
import com.wyait.manage.entity.manufacture.WorkPlanDTO;
import com.wyait.manage.entity.manufacture.WorkPlanQualityDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QualityMapper {
    List<WorkPlanQualityDTO> getDailyWorkPlan();

    FirstInspectVO getFirstInspectData(@Param("prodPlanId")Integer prodPlanId);

    EndInspectVO getEndInspectData(@Param("prodPlanId")Integer prodPlanId);

    int firstInspect(@Param("prodPlanId")Integer prodPlanId);

    int firstInspect2(@Param("prodPlanId")Integer prodPlanId);

    int firstInspectCancel(@Param("prodPlanId")Integer prodPlanId);

    int endInspect(@Param("prodPlanId")Integer prodPlanId, @Param("FqualifiedQty")Integer FqualifiedQipty, @Param("FunqualifiedQty")Integer FunqualifiedQty);

    int hasInspected(@Param("prodPlanId")Integer prodPlanId);

    int hasInspected2(@Param("prodPlanId")Integer prodPlanId);

    int endInspectCancel(@Param("prodPlanId")Integer prodPlanId);
}
