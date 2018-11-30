package com.wyait.manage.service.manufacture;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.dao.manufacture.WorkMapper;
import com.wyait.manage.entity.manufacture.WorkPlanDTO;
import com.wyait.manage.entity.manufacture.WorkVO;
import com.wyait.manage.entity.manufacture.WorkVO2;
import com.wyait.manage.pojo.User;
import com.wyait.manage.utils.DateUtil;
import com.wyait.manage.utils.IPUtil;
import com.wyait.manage.utils.PageDataResult;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        //PageHelper.startPage(page, limit);
        List<WorkPlanDTO> dailyWorkPlan = workMapper.getDailyWorkPlan(workStationCode);
        for(WorkPlanDTO workPlanDTO : dailyWorkPlan){
            workPlanDTO.setState(DateUtil.getCurrentState(workPlanDTO.getPlandate(), workPlanDTO.getPlanHour(), workPlanDTO.getPlanQty(), workPlanDTO.getQualifiedQty(), workPlanDTO.getUnqualifiedQty()));
        }
        int listLen  = dailyWorkPlan.size();
        List<WorkPlanDTO> pagedDailyWorkPlan ;
        if(limit * page > listLen){
            pagedDailyWorkPlan = dailyWorkPlan.subList(limit * (page - 1) , listLen);
        }else{
            pagedDailyWorkPlan = dailyWorkPlan.subList(limit * (page - 1) , limit * page);
        }
        //获取分页查询后的数据
        PageInfo<WorkPlanDTO> pageInfo = new PageInfo<>(dailyWorkPlan);
        //设置获取到的总记录数total：
        pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
        pdr.setList(pagedDailyWorkPlan);
        return pdr;
    }

    @Override
    public PageDataResult getWeeklyWorkPlan(Integer page, Integer limit, String workStationCode) {
        PageDataResult pdr = new PageDataResult();
        //PageHelper.startPage(page, limit);
        List<WorkPlanDTO> dailyWorkPlan = workMapper.getWeeklyWorkPlan(workStationCode);
        int listLen = dailyWorkPlan.size();
        List<WorkPlanDTO> pagedDailyWorkPlan;
        if(limit * page > listLen){
            pagedDailyWorkPlan = dailyWorkPlan.subList(limit * (page - 1) , listLen);
        }else{
            pagedDailyWorkPlan = dailyWorkPlan.subList(limit * (page - 1) , limit * page);
        }
        //获取分页查询后的数据
        PageInfo<WorkPlanDTO> pageInfo = new PageInfo<>(dailyWorkPlan);
        //设置获取到的总记录数total：
        pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
        pdr.setList(pagedDailyWorkPlan);
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
    public WorkVO quality(Integer prodPlanId) throws ParseException {
        //1.setQualityQty
        workMapper.quality(prodPlanId);
        //2.查询该生产计划执行情况
        WorkVO workVO = workMapper.getWorkVO(prodPlanId);
        workVO.setState(DateUtil.getCurrentState(workVO.getPlanDate(), workVO.getPlanHour(), workVO.getPlanQty(), workVO.getQualifiedqty(), workVO.getUnqualifiedqty()));
        return workVO;
    }

    @Override
    public WorkVO unquality(Integer prodPlanId) throws ParseException {
        //1.setQualityQty
        workMapper.unquality(prodPlanId);
        //2.查询该生产计划执行情况
        WorkVO workVO = workMapper.getWorkVO(prodPlanId);
        workVO.setState(DateUtil.getCurrentState(workVO.getPlanDate(), workVO.getPlanHour(), workVO.getPlanQty(), workVO.getQualifiedqty(), workVO.getUnqualifiedqty()));
        return workVO;
    }

    @Override
    public Map<String, Object> pause(Integer prodPlanId) throws ParseException {
        Map<String, Object> map = new HashMap<String, Object>();
            //检查暂停状态
            boolean flag = false;
            WorkVO2 workVO2 = workMapper.getWorkVO2(prodPlanId);
            if(workVO2.getHascomplete()){
                flag = true;
            }
            if(workVO2.getIspause()){
                flag = true;
            }
            if(flag){
                //1.暂停状态i
                map.put("msg", "已在暂停状态或已在完工状态！不能暂停！");
            }else{
                //2.非暂停状态
                //2.1 设置为暂停状态
                //2.2 设置lastTime = lastTime + 当前时间 - startTime
                String startTime = workVO2.getStartTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date startTimeDate = sdf.parse(startTime);
                long lStartTime = startTimeDate.getTime();
                long lnowTime = new Date().getTime();
                long lastTime = workVO2.getLastTime();
                lastTime = lastTime + lnowTime - lStartTime;
                workMapper.setWork(lastTime, prodPlanId);
                map.put("msg", "ok");
            }
        return map;
    }

    @Override
    public Map<String, Object> pauseCancel(Integer prodPlanId) throws ParseException {
        Map<String, Object> map = new HashMap<String, Object>();
        //检查暂停状态
        boolean flag = false;

        WorkVO2 workVO2 = workMapper.getWorkVO2(prodPlanId);
        if(!workVO2.getIspause()){
            flag = true;
        }
        if(workVO2.getHascomplete()){
            flag = true;
        }
        if(flag){
            //1.非暂停状态i
            map.put("msg", "不在暂停状态或在完工状态！不能取消暂停！");
        }else{
            //2.暂停状态
            //2.1 设置为非暂停状态
            //2.2 设置lastTime = lastTime + 当前时间 - startTime
            workMapper.setWorkNotPause(prodPlanId);
            map.put("msg", "ok");
        }
        return map;
    }

    @Override
    public Map<String, Object> complete(Integer prodPlanId) {
        Map<String, Object> map = new HashMap<String, Object>();
        //检查暂停状态
        WorkVO2 workVO2 = workMapper.getWorkVO2(prodPlanId);
        boolean flag = false;
        if(workVO2.getIspause()){
            flag = true;
        }
        if(workVO2.getHascomplete()){
            flag = true;
        }

        if(flag ){
            //1.非暂停状态i
            map.put("msg", "在暂停状态或完工状态！不能完工！");
        }else{
            //2.暂停状态
            //2.1 设置为非暂停状态
            //2.2 设置lastTime = lastTime + 当前时间 - startTime
            workMapper.setWorkComplete(prodPlanId);
            map.put("msg", "ok");
        }
        return map;
    }
}
