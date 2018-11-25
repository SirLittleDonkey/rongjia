package com.wyait.manage.web.manufacture;

import com.wyait.manage.entity.manufacture.FirstInspectVO;
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

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/manufacture")
public class QualityController {

    private static final Logger logger = LoggerFactory.getLogger(WorkStationController.class);
    @Autowired
    private QualityService qualityService;

    @RequestMapping("/quality")
    public String toWorkStation(){
        return "/manufacture/quality";
    }

    @RequestMapping(value = "/getDailyWorkPlanQuality", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "quality")
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
            pdr = qualityService.getDailyWorkPlan(page, limit, workStationCode);
            logger.debug("工位列表查询=pdr:" + pdr);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("工位列表查询异常！", e);
        }
        return pdr;
    }

    @RequestMapping("/getFirstInspectData")
    @ResponseBody
    public Map<String, Object> getFirstInspectData(Integer prodPlanId){
        Map<String, Object> map = new HashMap<>();
        try {
            if (null == prodPlanId) {
                logger.debug("获取首检数据==请求参数有误，请您稍后再试");
                map.put("msg", "请求参数有误，请您稍后再试");
                return map;
            }
            FirstInspectVO firstInspectVO = qualityService.getFirstInspectData(prodPlanId);
            if (null != firstInspectVO) {
                map.put("firstInspectVO", firstInspectVO);
                // 获取全部角色数据
                map.put("msg", "ok");
            } else {
                map.put("msg", "获取首检数据错误，请您稍后再试");
            }
            logger.debug("获取首检数据成功！map=" + map);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "获取首检数据错误，请您稍后再试");
            logger.error("获取首检数据异常！", e);
        }
        return map;
    }

    @RequestMapping("/firstInspect")
    @ResponseBody
    public Map<String, Object> firstInspect(Integer prodPlanId){
        Map<String, Object> map = new HashMap<>();
        try {
            if (null == prodPlanId) {
                logger.debug("获取首检数据==请求参数有误，请您稍后再试");
                map.put("msg", "请求参数有误，请您稍后再试");
                return map;
            }
            qualityService.firstInspect(prodPlanId);
            map.put("msg", "ok");
            logger.debug("获取首检数据成功！map=" + map);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "获取首检数据错误，请您稍后再试");
            logger.error("获取首检数据异常！", e);
        }
        return map;
    }
}
