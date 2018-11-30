package com.wyait.manage.service.manufacture;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.dao.manufacture.QualityMapper;
import com.wyait.manage.entity.manufacture.EndInspectVO;
import com.wyait.manage.entity.manufacture.FirstInspectVO;
import com.wyait.manage.entity.manufacture.WorkPlanQualityDTO;
import com.wyait.manage.utils.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QualityServiceImpl implements QualityService{
    @Autowired
    private QualityMapper qualityMapper;

    public PageDataResult getDailyWorkPlan(Integer page, Integer limit){
        PageDataResult pdr = new PageDataResult();
        //PageHelper.startPage(page, limit);
        List<WorkPlanQualityDTO> dailyWorkPlan = qualityMapper.getDailyWorkPlan();
        int listLen = dailyWorkPlan.size();
        List<WorkPlanQualityDTO> pagedDailyWorkPlan;
        if(limit * page > listLen ){
            pagedDailyWorkPlan = dailyWorkPlan.subList(limit * (page - 1) , listLen);
        }else {
            pagedDailyWorkPlan = dailyWorkPlan.subList(limit * (page - 1) , limit * page);
        }
        //获取分页查询后的数据
        PageInfo<WorkPlanQualityDTO> pageInfo = new PageInfo<>(dailyWorkPlan);
        //设置获取到的总记录数total：
        pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
        pdr.setList(pagedDailyWorkPlan);
        return pdr;
    }

    @Override
    public FirstInspectVO getFirstInspectData(Integer prodPlanId) {
        FirstInspectVO firstInspectVO = qualityMapper.getFirstInspectData(prodPlanId);
        return firstInspectVO;
    }

    @Override
    public EndInspectVO getEndInspectData(Integer prodPlanId) {
        EndInspectVO endInspectVO = qualityMapper.getEndInspectData(prodPlanId);
        return endInspectVO;
    }

    @Override
    public Map<String, Object> firstInspect(Integer prodPlanId) {
        Map<String, Object> map = new HashMap<String, Object>();
        int count = this.qualityMapper.hasInspected(prodPlanId);
        if(count > 0){
            map.put("msg","请勿重复首检");
        }else{
            int count2 = this.qualityMapper.hasInspected2(prodPlanId);
            if(count2 > 0){
                //update
                this.qualityMapper.firstInspect2(prodPlanId);
                map.put("msg", "ok");
            }else{
                //insert
                this.qualityMapper.firstInspect(prodPlanId);
                map.put("msg", "ok");
            }

        }
        return map;
    }

    @Override
    public Map<String, Object> firstInspectCancel(Integer prodPlanId) {
        Map<String, Object> map = new HashMap<String, Object>();
        int count = this.qualityMapper.hasInspected(prodPlanId);
        if(count < 1){
            map.put("msg","还未首检！");
        }else{
            this.qualityMapper.firstInspectCancel(prodPlanId);
            map.put("msg","ok");
        }
        return map;
    }

    @Override
    public void endInspect(Integer prodPlanId, Integer FqualifiedQty, Integer FunqualifiedQty) {
        this.qualityMapper.endInspect(prodPlanId, FqualifiedQty, FunqualifiedQty);
    }

    @Override
    public void endInspectCancel(Integer prodPlanId) {
        this.qualityMapper.endInspectCancel(prodPlanId);
    }
}
