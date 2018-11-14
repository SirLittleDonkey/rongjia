package com.wyait.manage.web.business;

import com.wyait.manage.entity.business.ProdPlanSearchDTO;
import com.wyait.manage.service.business.ProdPlanService;
import com.wyait.manage.utils.PageDataResult;
import com.wyait.manage.web.basic.WorkStationController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
