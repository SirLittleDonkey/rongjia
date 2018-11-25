package com.wyait.manage.web.business;

import com.wyait.manage.entity.business.qiSearchDTO;
import com.wyait.manage.entity.business.qiSetDTO;
import com.wyait.manage.pojo.User;
import com.wyait.manage.pojo.business.QualityInstruction;
import com.wyait.manage.service.business.QualityInstructionService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/business")
public class QualityInstructionController {
    private static final Logger logger = LoggerFactory.getLogger(WorkStationController.class);

    @Autowired
    private QualityInstructionService qualityInstructionService;

    @RequestMapping("/qualityInstruction")
    public String toWorkStation(){
        return "/business/qualityInstruction";
    }

    @RequestMapping("/getQualityInstructions")
    @ResponseBody
    @RequiresPermissions(value = "QualityInstruction")
    public PageDataResult getQualityInstructions(@RequestParam("page") Integer page,
                                                   @RequestParam("limit") Integer limit, qiSearchDTO qiSearchDTO ){
        logger.debug("分页查询检验指导书列表！搜索条件：piSearch：" + qiSearchDTO + ",page:" + page
                + ",每页记录数量limit:" + limit);
        PageDataResult pdr = new PageDataResult();
        try{
            if (null == page) {
                page = 1;
            }
            if (null == limit) {
                limit = 10;
            }
            //获取检验指导书列表
            pdr = qualityInstructionService.getQualityInstructions(page, limit, qiSearchDTO);
            logger.debug("检验指导书列表查询=pdr:" + pdr);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("检验指导书列表查询异常！", e);
        }
        return pdr;
    }

    /**
     * 设置工位[新增或更新]
     * @return ok/fail
     */
    @RequestMapping(value = "/setQualityInstruction", method = RequestMethod.POST)
    @ResponseBody
    public String setQualityInstruction(qiSetDTO qiSetDTO) {
        logger.debug("设置检验指导书[新增或更新]！piSetDTO:" + qiSetDTO);
        try {
            if (null == qiSetDTO) {
                logger.debug("设置检验指导书[新增或更新]，结果=请您填写检验指导书信息");
                return "请您填写检验指导书信息";
            }
            User existUser = (User) SecurityUtils.getSubject().getPrincipal();
            if (null == existUser) {
                logger.debug("设置工位[新增或更新]，结果=您未登录或登录超时，请您登录后再试");
                return "您未登录或登录超时，请您登录后再试";
            }
            // 设置检验指导书[新增或更新]
            logger.info("设置检验指导书[新增或更新]成功！workStation=" + qiSetDTO
                    + "，操作的用户ID=" + existUser.getId());
            return qualityInstructionService.setQualityInstruction(qiSetDTO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("设置检验指导书[新增或更新]异常！", e);
            return "操作异常，请您稍后再试";
        }
    }

    @RequestMapping(value = "/getQualityInstruction", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getQualityInstruction(@RequestParam("id") Integer id){
        logger.debug("查询检验指导书数据！id:" + id);
        Map<String, Object> map = new HashMap<>();
        try {
            if (null == id) {
                logger.debug("查询检验指导书数据==请求参数有误，请您稍后再试");
                map.put("msg", "请求参数有误，请您稍后再试");
                return map;
            }
            // 查询工位
            QualityInstruction qualityInstruction = qualityInstructionService.getQualityInstruction(id);
            logger.debug("查询检验指导书数据！pi=" + qualityInstruction);
            if (null != qualityInstruction) {
                map.put("qualityInstruction", qualityInstruction);
                // 获取全部角色数据
                map.put("msg", "ok");
            } else {
                map.put("msg", "查询检验指导书信息有误，请您稍后再试");
            }
            logger.debug("查询检验指导书数据成功！map=" + map);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "查询检验指导书错误，请您稍后再试");
            logger.error("查询检验指导书数据异常！", e);
        }
        return map;
    }

    /**
     * 删除检验指导书
     * @return ok/fail
     */
    @RequestMapping(value = "/delQualityInstruction", method = RequestMethod.POST)
    @ResponseBody
    public String delQualityInstruction(@RequestParam("id") Integer id) {
        logger.debug("删除工位！id:" + id );
        String msg = "";
        try {
            if (null == id ) {
                logger.debug("删除工位，结果=请求参数有误，请您稍后再试");
                return "请求参数有误，请您稍后再试";
            }
            User existUser = (User) SecurityUtils.getSubject().getPrincipal();
            if (null == existUser) {
                logger.debug("删除工位，结果=您未登录或登录超时，请您登录后再试");
                return "您未登录或登录超时，请您登录后再试";
            }
            // 删除工位
            msg =qualityInstructionService.setDelQualityInstruction(id, 1);
            logger.info("删除工位:" + msg + "！board=" + id + "，操作用户id:"
                    + existUser.getId() );
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除用户异常！", e);
            msg = "操作异常，请您稍后再试";
        }
        return msg;
    }

    @RequestMapping(value = "/revoverQualityInstruction", method = RequestMethod.POST)
    @ResponseBody
    public String recoverQualityInstruction(@RequestParam("id") Integer id) {
        logger.debug("恢复工位！id:" + id );
        String msg = "";
        try {
            if (null == id ) {
                logger.debug("恢复工位，结果=请求参数有误，请您稍后再试");
                return "请求参数有误，请您稍后再试";
            }
            User existUser = (User) SecurityUtils.getSubject().getPrincipal();
            if (null == existUser) {
                logger.debug("恢复工位，结果=您未登录或登录超时，请您登录后再试");
                return "您未登录或登录超时，请您登录后再试";
            }
            // 删除工位
            msg = qualityInstructionService.setDelQualityInstruction(id, 0);
            logger.info("恢复工位:" + msg + "！board=" + id + "，操作用户id:"
                    + existUser.getId() );
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除用户异常！", e);
            msg = "操作异常，请您稍后再试";
        }
        return msg;
    }

    @RequestMapping(value = "/getQualityInstructionPDF", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getQualityInstructionPDF(@RequestParam("id") Integer id){
        logger.debug("查询检验指导书数据！id:" + id);
        Map<String, Object> map = new HashMap<>();
        try {
            if (null == id) {
                logger.debug("查询检验指导书数据==请求参数有误，请您稍后再试");
                map.put("msg", "请求参数有误，请您稍后再试");
                return map;
            }
            // 查询工位
            QualityInstruction qualityInstruction = qualityInstructionService.getQualityInstruction(id);
            logger.debug("查询检验指导书数据！pi=" + qualityInstruction);
            if (null != qualityInstruction) {
                map.put("fileName", qualityInstruction.getFilepath());
                // 获取全部角色数据
                map.put("msg", "ok");
            } else {
                map.put("msg", "查询检验指导书信息有误，请您稍后再试");
            }
            logger.debug("查询检验指导书数据成功！map=" + map);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "查询检验指导书错误，请您稍后再试");
            logger.error("查询检验指导书数据异常！", e);
        }
        return map;
    }

}
