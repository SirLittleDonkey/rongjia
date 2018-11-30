package com.wyait.manage.web.business;

import com.wyait.manage.entity.business.ProdPlanSearchDTO;
import com.wyait.manage.entity.business.ProdPlanVO;
import com.wyait.manage.entity.business.ppSetDTO;
import com.wyait.manage.pojo.User;
import com.wyait.manage.service.business.ProdPlanService;
import com.wyait.manage.utils.PageDataResult;
import com.wyait.manage.web.basic.WorkStationController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/business")
public class ProdPlanController {
    private static final Logger logger = LoggerFactory.getLogger(ProdPlanController.class);

    @Autowired
    private ProdPlanService prodPlanService;

    @RequestMapping("/prodPlan")
    public String toProdPlan(){
        return "/business/prodPlan";
    }

    @RequestMapping("/getProdPlans")
    @ResponseBody
    @RequiresPermissions(value = "prodPlan")
    public PageDataResult getProdPlans(@RequestParam("page") Integer page,
                                       @RequestParam("limit") Integer limit, ProdPlanSearchDTO prodPlanSearchDTO){
        logger.debug("分页查询生产计划列表！搜索条件：userSearch：" + prodPlanSearchDTO + ",page:" + page
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
            pdr = prodPlanService.getProdPlans(page, limit, prodPlanSearchDTO);
            logger.debug("工位列表查询=pdr:" + pdr);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("工位列表查询异常！", e);
        }
        return pdr;
    }

    @RequestMapping(value = "/setProdPlan", method = RequestMethod.POST)
    @ResponseBody
    public String setProdPlan(ppSetDTO ppSetDTO){
        logger.debug("设置生产计划[新增或更新]！ppSetDTO:" + ppSetDTO);
        try {
            if (null == ppSetDTO) {
                logger.debug("设置生产计划[新增或更新]，结果=请您填写生产计划信息");
                return "请您填写生产计划信息";
            }
            User existUser = (User) SecurityUtils.getSubject().getPrincipal();
            if (null == existUser) {
                logger.debug("设置生产计划[新增或更新]，结果=您未登录或登录超时，请您登录后再试");
                return "您未登录或登录超时，请您登录后再试";
            }
            // 设置作业指导书[新增或更新]
            logger.info("设置生产计划[新增或更新]成功！workStation=" + ppSetDTO
                    + "，操作的用户ID=" + existUser.getId());
            return prodPlanService.setProdPlan(ppSetDTO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("设置生产计划[新增或更新]异常！", e);
            return "操作异常，请您稍后再试";
        }
    }

    @RequestMapping(value = "/getProdPlan", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProdPlan(@RequestParam("id") Integer id){
        logger.debug("查询生产计划数据！id:" + id);
        Map<String, Object> map = new HashMap<>();
        try {
            if (null == id) {
                logger.debug("查询生产计划数据==请求参数有误，请您稍后再试");
                map.put("msg", "请求参数有误，请您稍后再试");
                return map;
            }
            // 查询工位
            ProdPlanVO prodPlanVO = prodPlanService.getProdPlan(id);
            prodPlanVO.setPlanDate(prodPlanVO.getPlanDate().substring(0,19));
            logger.debug("查询生产计划数据！wsvo=" + prodPlanVO);
            if (null != prodPlanVO) {
                map.put("prodPlanVO", prodPlanVO);
                // 获取全部角色数据
                map.put("msg", "ok");
            } else {
                map.put("msg", "查询生产计划信息有误，请您稍后再试");
            }
            logger.debug("查询生产计划数据成功！map=" + map);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "查询生产计划错误，请您稍后再试");
            logger.error("查询生产计划数据异常！", e);
        }
        return map;
    }

    @RequestMapping(value = "/uploadProdPlan", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadProdPlan(@RequestParam MultipartFile file) throws IOException {
        return prodPlanService.uploadProdPlan(file);
    }
}
