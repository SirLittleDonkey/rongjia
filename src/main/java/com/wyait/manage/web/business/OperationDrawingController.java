package com.wyait.manage.web.business;

import com.wyait.manage.entity.business.OperationDrawingSearchDTO;
import com.wyait.manage.entity.business.OperationDrawingSetDTO;
import com.wyait.manage.pojo.User;
import com.wyait.manage.pojo.business.OperationDrawing;
import com.wyait.manage.service.business.OperationDrawingService;
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
public class OperationDrawingController {
    private static final Logger logger = LoggerFactory.getLogger(WorkStationController.class);

    @Autowired
    private OperationDrawingService operationDrawingService;

    @RequestMapping("/operationDrawing")
    public String toWorkStation(){
        return "/business/operationDrawing";
    }

    @RequestMapping("/getOperationDrawings")
    @ResponseBody
    @RequiresPermissions(value = "OperationDrawing")
    public PageDataResult getOperationDrawings(@RequestParam("page") Integer page,
                                      @RequestParam("limit") Integer limit, OperationDrawingSearchDTO operationDrawingSearchDTO ){
        logger.debug("分页查询图纸列表！搜索条件：piSearch：" + operationDrawingSearchDTO + ",page:" + page
                + ",每页记录数量limit:" + limit);
        PageDataResult pdr = new PageDataResult();
        try{
            if (null == page) {
                page = 1;
            }
            if (null == limit) {
                limit = 10;
            }
            //获取图纸列表
            pdr = operationDrawingService.getOperationDrawings(page, limit, operationDrawingSearchDTO);
            logger.debug("图纸列表查询=pdr:" + pdr);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("图纸列表查询异常！", e);
        }
        return pdr;
    }

    /**
     * 设置工位[新增或更新]
     * @return ok/fail
     */
    @RequestMapping(value = "/setOperationDrawing", method = RequestMethod.POST)
    @ResponseBody
    public String setOperationDrawing(OperationDrawingSetDTO operationDrawingSetDTO) {
        logger.debug("设置图纸[新增或更新]！piSetDTO:" + operationDrawingSetDTO);
        try {
            if (null == operationDrawingSetDTO) {
                logger.debug("设置图纸[新增或更新]，结果=请您填写图纸信息");
                return "请您填写图纸信息";
            }
            User existUser = (User) SecurityUtils.getSubject().getPrincipal();
            if (null == existUser) {
                logger.debug("设置工位[新增或更新]，结果=您未登录或登录超时，请您登录后再试");
                return "您未登录或登录超时，请您登录后再试";
            }
            // 设置图纸[新增或更新]
            logger.info("设置图纸[新增或更新]成功！workStation=" + operationDrawingSetDTO
                    + "，操作的用户ID=" + existUser.getId());
            return operationDrawingService.setOperationDrawing(operationDrawingSetDTO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("设置图纸[新增或更新]异常！", e);
            return "操作异常，请您稍后再试";
        }
    }

    @RequestMapping(value = "/getOperationDrawing", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getOperationDrawing(@RequestParam("id") Integer id){
        logger.debug("查询图纸数据！id:" + id);
        Map<String, Object> map = new HashMap<>();
        try {
            if (null == id) {
                logger.debug("查询图纸数据==请求参数有误，请您稍后再试");
                map.put("msg", "请求参数有误，请您稍后再试");
                return map;
            }
            // 查询工位
            OperationDrawing operationDrawing = operationDrawingService.getOperationDrawing(id);
            logger.debug("查询图纸数据！pi=" + operationDrawing);
            if (null != operationDrawing) {
                map.put("operationDrawing", operationDrawing);
                // 获取全部角色数据
                map.put("msg", "ok");
            } else {
                map.put("msg", "查询图纸信息有误，请您稍后再试");
            }
            logger.debug("查询图纸数据成功！map=" + map);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "查询图纸错误，请您稍后再试");
            logger.error("查询图纸数据异常！", e);
        }
        return map;
    }

    /**
     * 删除图纸
     * @return ok/fail
     */
    @RequestMapping(value = "/delOperationDrawing", method = RequestMethod.POST)
    @ResponseBody
    public String delOperationDrawing(@RequestParam("id") Integer id) {
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
            msg =operationDrawingService.setDelOperationDrawing(id, 1);
            logger.info("删除工位:" + msg + "！board=" + id + "，操作用户id:"
                    + existUser.getId() );
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除用户异常！", e);
            msg = "操作异常，请您稍后再试";
        }
        return msg;
    }

    @RequestMapping(value = "/revoverOperationDrawing", method = RequestMethod.POST)
    @ResponseBody
    public String recoverOperationDrawing(@RequestParam("id") Integer id) {
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
            msg = operationDrawingService.setDelOperationDrawing(id, 0);
            logger.info("恢复工位:" + msg + "！board=" + id + "，操作用户id:"
                    + existUser.getId() );
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除用户异常！", e);
            msg = "操作异常，请您稍后再试";
        }
        return msg;
    }

    @RequestMapping(value = "/getOperationDrawingPDF", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getOperationDrawingPDF(@RequestParam("id") Integer id){
        logger.debug("查询图纸数据！id:" + id);
        Map<String, Object> map = new HashMap<>();
        try {
            if (null == id) {
                logger.debug("查询图纸数据==请求参数有误，请您稍后再试");
                map.put("msg", "请求参数有误，请您稍后再试");
                return map;
            }
            // 查询工位
            OperationDrawing operationDrawing = operationDrawingService.getOperationDrawing(id);
            logger.debug("查询图纸数据！pi=" + operationDrawing);
            if (null != operationDrawing) {
                map.put("fileName", operationDrawing.getFilepath());
                // 获取全部角色数据
                map.put("msg", "ok");
            } else {
                map.put("msg", "查询图纸信息有误，请您稍后再试");
            }
            logger.debug("查询图纸数据成功！map=" + map);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "查询图纸错误，请您稍后再试");
            logger.error("查询图纸数据异常！", e);
        }
        return map;
    }

}
