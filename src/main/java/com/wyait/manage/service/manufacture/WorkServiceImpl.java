package com.wyait.manage.service.manufacture;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.dao.manufacture.WorkMapper;
import com.wyait.manage.entity.manufacture.WorkPlanDTO;
import com.wyait.manage.entity.manufacture.WorkVO;
import com.wyait.manage.pojo.User;
import com.wyait.manage.utils.DateUtil;
import com.wyait.manage.utils.IPUtil;
import com.wyait.manage.utils.PageDataResult;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class WorkServiceImpl implements WorkService{
    @Autowired
    private WorkMapper workMapper;

    @Override
    public String getWorkStationCode(HttpServletRequest request) {
        String ipAddress = IPUtil.getIpAddr(request);
        System.out.println(ipAddress);
        return this.workMapper.getWorkStationCodeByIpAddress(ipAddress);
    }

    @Override
    public PageDataResult getDailyWorkPlan(Integer page, Integer limit, String workStationCode) throws ParseException {

        PageDataResult pdr = new PageDataResult();
        PageHelper.startPage(page, limit);
        List<WorkPlanDTO> dailyWorkPlan = workMapper.getDailyWorkPlan(workStationCode);
        for(WorkPlanDTO workPlanDTO : dailyWorkPlan){
            workPlanDTO.setState(DateUtil.getCurrentState(workPlanDTO.getPlandate(), workPlanDTO.getPlanHour(), workPlanDTO.getPlanQty(), workPlanDTO.getQualifiedQty(), workPlanDTO.getUnqualifiedQty()));
        }
        //获取分页查询后的数据
        PageInfo<WorkPlanDTO> pageInfo = new PageInfo<>(dailyWorkPlan);
        //设置获取到的总记录数total：
        pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
        pdr.setList(dailyWorkPlan);
        return pdr;
    }

    @Override
    public PageDataResult getWeeklyWorkPlan(Integer page, Integer limit, String workStationCode) {
        PageDataResult pdr = new PageDataResult();
        PageHelper.startPage(page, limit);
        List<WorkPlanDTO> dailyWorkPlan = workMapper.getWeeklyWorkPlan(workStationCode);
        //获取分页查询后的数据
        PageInfo<WorkPlanDTO> pageInfo = new PageInfo<>(dailyWorkPlan);
        //设置获取到的总记录数total：
        pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
        pdr.setList(dailyWorkPlan);
        return pdr;
    }

    @Override
    public WorkVO startWork(Integer prodPlanId) throws ParseException {
        //1.查询该生产计划是否已开工
        Date startTime = workMapper.getStartTime(prodPlanId);
        if(startTime == null){
            //未开工则开工
            workMapper.setStartTime(prodPlanId);
        }
        //2.查询该生产计划执行状况
        WorkVO workVO = workMapper.getWorkVO(prodPlanId);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        workVO.setUserName(user.getUsername());
        workVO.setState(DateUtil.getCurrentState(workVO.getPlanDate(), workVO.getPlanHour(), workVO.getPlanQty(), workVO.getQualifiedqty(), workVO.getUnqualifiedqty()));
        return workVO;
    }

    @Override
    public WorkVO quality(Integer prodPlanId) {
        //1.setQualityQty
        workMapper.quality(prodPlanId);
        //2.查询该生产计划执行情况
        WorkVO workVO = workMapper.getWorkVO(prodPlanId);
        return workVO;
    }

    @Override
    public WorkVO unquality(Integer prodPlanId) {
        //1.setQualityQty
        workMapper.unquality(prodPlanId);
        //2.查询该生产计划执行情况
        WorkVO workVO = workMapper.getWorkVO(prodPlanId);
        return workVO;
    }
}
