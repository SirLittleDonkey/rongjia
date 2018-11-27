package com.wyait.manage.web.manufacture;

import com.wyait.manage.entity.manufacture.WorkVO;
import com.wyait.manage.service.manufacture.WorkService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/manufacture")
public class WorkController {

    private static final Logger logger = LoggerFactory.getLogger(WorkStationController.class);
    @Autowired
    private WorkService workService;

    @RequestMapping("/work")
    public String toWorkStation(){
        return "/manufacture/work";
    }

    @RequestMapping(value = "/getDailyWorkPlan", method = RequestMethod.POST)
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
            pdr = workService.getDailyWorkPlan(page, limit, workStationCode);
            logger.debug("工位列表查询=pdr:" + pdr);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("工位列表查询异常！", e);
        }
        return pdr;
    }

    @RequestMapping(value = "/getWeeklyWorkPlan", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "work")
    public PageDataResult getWeeklyWorkPlan(@RequestParam("page") Integer page,
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
            pdr = workService.getWeeklyWorkPlan(page, limit, workStationCode);
            logger.debug("工位列表查询=pdr:" + pdr);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("工位列表查询异常！", e);
        }
        return pdr;
    }

    @RequestMapping("/getWorkStationCode")
    @ResponseBody
    public String getWorkStationCode(HttpServletRequest request){

        return workService.getWorkStationCode(request);

    }

    @RequestMapping("/startWork")
    @ResponseBody
    public Map<String, Object> startWork(Integer prodPlanId){
        logger.debug("开始生产！prodPlanId:" + prodPlanId);
        Map<String, Object> map = new HashMap<>();
        try {
            if (null == prodPlanId) {
                logger.debug("开始生产==请求参数有误，请您稍后再试");
                map.put("msg", "请求参数有误，请您稍后再试");
                return map;
            }
            WorkVO workVO = workService.startWork(prodPlanId);
            if (null != workVO) {
                map.put("workVO", workVO);
                // 获取全部角色数据
                map.put("msg", "ok");
            } else {
                map.put("msg", "开工错误，请您稍后再试");
            }
            logger.debug("开工成功！map=" + map);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "开工错误，请您稍后再试");
            logger.error("查询生产计划数据异常！", e);
        }
        return map;

    }


    @RequestMapping("/setquality")
    @ResponseBody
    public Map<String, Object> quality(Integer prodPlanId){
        logger.debug("开始生产！prodPlanId:" + prodPlanId);
        Map<String, Object> map = new HashMap<>();
        try {
            if (null == prodPlanId) {
                logger.debug("开始生产==请求参数有误，请您稍后再试");
                map.put("msg", "请求参数有误，请您稍后再试");
                return map;
            }
            WorkVO workVO = workService.quality(prodPlanId);
            if (null != workVO) {
                map.put("workVO", workVO);
                // 获取全部角色数据
                map.put("msg", "ok");
            } else {
                map.put("msg", "开工错误，请您稍后再试");
            }
            logger.debug("开工成功！map=" + map);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "开工错误，请您稍后再试");
            logger.error("查询生产计划数据异常！", e);
        }
        return map;

    }

    @RequestMapping("/setunquality")
    @ResponseBody
    public Map<String, Object> unquality(Integer prodPlanId){
        logger.debug("开始生产！prodPlanId:" + prodPlanId);
        Map<String, Object> map = new HashMap<>();
        try {
            if (null == prodPlanId) {
                logger.debug("开始生产==请求参数有误，请您稍后再试");
                map.put("msg", "请求参数有误，请您稍后再试");
                return map;
            }
            WorkVO workVO = workService.unquality(prodPlanId);
            if (null != workVO) {
                map.put("workVO", workVO);
                // 获取全部角色数据
                map.put("msg", "ok");
            } else {
                map.put("msg", "开工错误，请您稍后再试");
            }
            logger.debug("开工成功！map=" + map);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "开工错误，请您稍后再试");
            logger.error("查询生产计划数据异常！", e);
        }
        return map;

    }
}
