package com.wyait.manage.dao.business;

import com.wyait.manage.entity.business.ProdPlanDTO;
import com.wyait.manage.entity.business.ProdPlanSearchDTO;
import com.wyait.manage.entity.business.ProdPlanVO;
import com.wyait.manage.entity.business.ppSetDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProdPlanMapper {
    List<ProdPlanDTO> getProdPlans(@Param("prodPlanSearchDTO")ProdPlanSearchDTO prodPlanSearchDTO);

    int insert(ppSetDTO ppSetDTO);

    ProdPlanVO getProdPlan(Integer id);

    int update(ppSetDTO ppSetDTO);
}
