package com.wyait.manage.web.manufacture;

import com.wyait.manage.service.manufacture.QualityService;
import com.wyait.manage.utils.PageDataResult;
import com.wyait.manage.web.basic.WorkStationController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/manufacture")
public class QualityController {

    private static final Logger logger = LoggerFactory.getLogger(WorkStationController.class);
    @Autowired
    private QualityService qualityServiceService;

    @RequestMapping("/quality")
    public String toWorkStation(){
        return "/manufacture/quality";
    }

    @RequestMapping(value = "/getDailyWorkPlanQuality", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "work")
    public PageDataResult getDailyWorkPlan(@RequestParam("page") Integer page,
                                           @RequestParam("limit") Integer limit,
                                           @RequestParam("workStationCode") String workStationCode){
        logger.debug("分页查询工位列表！搜索条件：workStationCode：" + workStationCode + ",page:" + page
                + ",每页记录数量limit:" + limit);
        PageDataResult pdr = new PageDataResult();
        try{
            if (null == page) {
                page = 1;
            }
            if (null == limit) {
                limit = 10;
            }
            //获取工位列表
            pdr = qualityServiceService.getDailyWorkPlan(page, limit, workStationCode);
            logger.debug("工位列表查询=pdr:" + pdr);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("工位列表查询异常！", e);
        }
        return pdr;
    }
}
