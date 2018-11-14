package com.wyait.manage.web.business;

import com.wyait.manage.entity.business.oiSearchDTO;
import com.wyait.manage.service.business.OperationInstructionService;
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
public class OperationInstructionController {
    private static final Logger logger = LoggerFactory.getLogger(WorkStationController.class);

    @Autowired
    private OperationInstructionService operationInstructionService;

    @RequestMapping("/operationInstruction")
    public String toWorkStation(){
        return "/business/operationInstruction";
    }

    @RequestMapping("/getOperationInstructions")
    @ResponseBody
    @RequiresPermissions(value = "OperationInstruction")
    public PageDataResult getOperationInstructions(@RequestParam("page") Integer page,
                                                   @RequestParam("limit") Integer limit, oiSearchDTO oiSearchDTO ){
        logger.debug("分页查询作业指导书列表！搜索条件：oiSearch：" + oiSearchDTO + ",page:" + page
                + ",每页记录数量limit:" + limit);
        PageDataResult pdr = new PageDataResult();
        try{
            if (null == page) {
                page = 1;
            }
            if (null == limit) {
                limit = 10;
            }
            //获取作业指导书列表
            pdr = operationInstructionService.getOperationInstructions(page, limit, oiSearchDTO);
            logger.debug("作业指导书列表查询=pdr:" + pdr);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("作业指导书列表查询异常！", e);
        }
        return pdr;
    }
}
