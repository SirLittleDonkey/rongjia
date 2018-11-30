package com.wyait.manage.web.manufacture;

import com.wyait.manage.entity.manufacture.DailyDeliverySearchDTO;
import com.wyait.manage.service.manufacture.DeliveryService;
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
@RequestMapping("/manufacture")
public class DeliveryController {
    private static final Logger logger = LoggerFactory.getLogger(WorkStationController.class);

    @Autowired
    private DeliveryService deliveryService;

    @RequestMapping("/delivery")
    public String toWorkStation(){
        return "/manufacture/delivery";
    }

    @RequestMapping("/getDailyDeliveryList")
    @ResponseBody
    @RequiresPermissions(value = "delivery")
    public PageDataResult getDailyDeliveryList(@RequestParam("page")Integer page, @RequestParam("limit")Integer limit, DailyDeliverySearchDTO dailyDeliverySearchDTO ){
        PageDataResult pdr = new PageDataResult();
        try{
            if (null == page) {
                page = 1;
            }
            if (null == limit) {
                limit = 10;
            }
            //获取工位列表
            pdr = deliveryService.getDailyDeliveryList(page, limit,dailyDeliverySearchDTO);
            logger.debug("每日发货列表查询=pdr:" + pdr);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("每日发货列表查询异常！", e);
        }
        return pdr;
    }
}
