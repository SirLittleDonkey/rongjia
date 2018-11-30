package com.wyait.manage.service.manufacture;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.dao.manufacture.PlanProcessMapper;
import com.wyait.manage.entity.manufacture.PlanProcessDTO;
import com.wyait.manage.entity.manufacture.PlanProcessSearchDTO;
import com.wyait.manage.utils.DateUtil;
import com.wyait.manage.utils.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

@Service
public class PlanProcessServiceImpl implements PlanProcessService {
    @Autowired
    private PlanProcessMapper planProcessMapper;

    @Override
    public PageDataResult getPlanProcessList(Integer page, Integer limit, PlanProcessSearchDTO planProcessSearchDTO) throws ParseException {
        PageDataResult pdr = new PageDataResult();
        //PageHelper.startPage(page, limit);
        List<PlanProcessDTO> planProcessDTOList = planProcessMapper.getPlanProcessList(planProcessSearchDTO);
        for(PlanProcessDTO planProcessDTO: planProcessDTOList){
            DecimalFormat df = new DecimalFormat("0.00%");
            Float f = (float)planProcessDTO.getQualifiedQty() / planProcessDTO.getPlanQty();
            String compRate = df.format(f);
            planProcessDTO.setCompletionRate(compRate);
            planProcessDTO.setState(DateUtil.getCurrentState(planProcessDTO.getPlanDate(), planProcessDTO.getPlanHour(), planProcessDTO.getPlanQty(),planProcessDTO.getQualifiedQty(), planProcessDTO.getUnqualifiedQty()));
        }
        int listLen = planProcessDTOList.size();
        List<PlanProcessDTO> pagedPlanProcessDTO;
        if(limit * page > listLen){
            pagedPlanProcessDTO = planProcessDTOList.subList(limit * (page - 1) , listLen);
        }else{
            pagedPlanProcessDTO = planProcessDTOList.subList(limit * (page - 1) , limit * page);
        }

        //获取分页查询后的数据
        PageInfo<PlanProcessDTO> pageInfo = new PageInfo<>(planProcessDTOList);
        //设置获取到的总记录数total：
        pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
        pdr.setList(pagedPlanProcessDTO);
        return pdr;
    }
}
