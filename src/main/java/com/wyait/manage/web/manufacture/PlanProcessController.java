package com.wyait.manage.web.manufacture;

import com.wyait.manage.entity.manufacture.PlanProcessSearchDTO;
import com.wyait.manage.service.manufacture.PlanProcessService;
import com.wyait.manage.utils.PageDataResult;
import com.wyait.manage.web.basic.WorkStationController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/manufacture")
public class PlanProcessController {
    private static final Logger logger = LoggerFactory.getLogger(WorkStationController.class);
    @Autowired
    private PlanProcessService planProcessService;
    @RequestMapping("/planProcess")
    public String toWorkStation(){
        return "/manufacture/planProcess";
    }

    @RequestMapping("/getPlanProcessList")
    @ResponseBody
    @RequiresPermissions(value = "planProcess")
    public PageDataResult getPlanProcessList(Integer page, Integer limit, PlanProcessSearchDTO planProcessSearchDTO){
        PageDataResult pdr = new PageDataResult();
        try{
            if (null == page) {
                page = 1;
            }
            if (null == limit) {
                limit = 10;
            }
            //获取工位列表
            pdr = planProcessService.getPlanProcessList(page, limit, planProcessSearchDTO);
            logger.debug("计划与进程列表查询=pdr:" + pdr);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("计划与进程列表查询异常！", e);
        }
        return pdr;
    }
}
