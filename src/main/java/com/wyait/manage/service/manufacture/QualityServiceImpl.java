package com.wyait.manage.service.manufacture;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.dao.manufacture.QualityMapper;
import com.wyait.manage.entity.manufacture.FirstInspectVO;
import com.wyait.manage.entity.manufacture.WorkPlanQualityDTO;
import com.wyait.manage.utils.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QualityServiceImpl implements QualityService{
    @Autowired
    private QualityMapper qualityMapper;

    public PageDataResult getDailyWorkPlan(Integer page, Integer limit, String workStationCode){
        PageDataResult pdr = new PageDataResult();
        PageHelper.startPage(page, limit);
        List<WorkPlanQualityDTO> dailyWorkPlan = qualityMapper.getDailyWorkPlan(workStationCode);
        //获取分页查询后的数据
        PageInfo<WorkPlanQualityDTO> pageInfo = new PageInfo<>(dailyWorkPlan);
        //设置获取到的总记录数total：
        pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
        pdr.setList(dailyWorkPlan);
        return pdr;
    }

    @Override
    public FirstInspectVO getFirstInspectData(Integer prodPlanId) {
        FirstInspectVO firstInspectVO = qualityMapper.getFirstInspectData(prodPlanId);
        return firstInspectVO;
    }

    @Override
    public void firstInspect(Integer prodPlanId) {
        this.qualityMapper.firstInspect(prodPlanId);
    }
}
