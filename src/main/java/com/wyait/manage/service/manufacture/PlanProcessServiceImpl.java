package com.wyait.manage.service.manufacture;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.dao.manufacture.PlanProcessMapper;
import com.wyait.manage.entity.manufacture.PlanProcessDTO;
import com.wyait.manage.entity.manufacture.PlanProcessSearchDTO;
import com.wyait.manage.utils.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanProcessServiceImpl implements PlanProcessService {
    @Autowired
    private PlanProcessMapper planProcessMapper;

    @Override
    public PageDataResult getPlanProcessList(Integer page, Integer limit, PlanProcessSearchDTO planProcessSearchDTO) {
        PageDataResult pdr = new PageDataResult();
        PageHelper.startPage(page, limit);
        List<PlanProcessDTO> planProcessDTOList = planProcessMapper.getPlanProcessList(planProcessSearchDTO);
        //获取分页查询后的数据
        PageInfo<PlanProcessDTO> pageInfo = new PageInfo<>(planProcessDTOList);
        //设置获取到的总记录数total：
        pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
        pdr.setList(planProcessDTOList);
        return pdr;
    }
}
