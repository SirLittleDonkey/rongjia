package com.wyait.manage.web.basic;

import com.wyait.manage.entity.basic.ProcedureSearchDTO;
import com.wyait.manage.entity.basic.ProcedureVO;
import com.wyait.manage.pojo.User;
import com.wyait.manage.pojo.basic.Procedure;
import com.wyait.manage.service.basic.ProcedureService;
import com.wyait.manage.utils.PageDataResult;
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

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/basic")
public class ProcedureController {
    private static final Logger logger = LoggerFactory.getLogger(WorkStationController.class);

    @Autowired
    private ProcedureService procedureService;

    @RequestMapping("/procedure")
    public String toWorkStation(){
        return "/basic/procedure";
    }

    @RequestMapping(value = "/getProcedures", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "procedure")
    public PageDataResult getProcedures(@RequestParam("page") Integer page,
                                        @RequestParam("limit") Integer limit,
                                        ProcedureSearchDTO searchDTO){
        logger.debug("分页查询工序列表！搜索条件：searchDTO：" + searchDTO + ",page:" + page
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
            pdr = procedureService.getProcedures(page, limit, searchDTO);
            logger.debug("工序列表查询=pdr:" + pdr);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("工序列表查询异常！", e);
        }
        return pdr;
    }

    /**
     * 设置工序[新增或更新]
     * @return ok/fail
     */
    @RequestMapping(value = "/setProcedure", method = RequestMethod.POST)
    @ResponseBody
    public String setProcedure(Procedure procedure){
        logger.debug("设置工序[新增或更新]！procedure:" + procedure);
        try{
            if(null == procedure){
                logger.debug("设置工位[新增或更新]，结果=请您填写用户信息");
                return "请您填写工序信息";
            }
            User existUser = (User) SecurityUtils.getSubject().getPrincipal();
            if (null == existUser) {
                logger.debug("设置工序[新增或更新]，结果=您未登录或登录超时，请您登录后再试");
                return "您未登录或登录超时，请您登录后再试";
            }
            // 设置工序[新增或更新]
            logger.info("设置工序[新增或更新]成功！procedure=" + procedure
                    + "，操作的用户ID=" + existUser.getId());
            return procedureService.setProcedure(procedure);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("设置工序[新增或更新]异常！", e);
            return "操作异常，请您稍后再试";
        }
    }

    /**
     * 查询工位数据
     * @return map
     */
    @RequestMapping(value = "/getProcedure", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProcedure(@RequestParam("id") Integer id) {
        logger.debug("查询工序数据！id:" + id);
        Map<String, Object> map = new HashMap<>();
        try {
            if (null == id) {
                logger.debug("查询用户数据==请求参数有误，请您稍后再试");
                map.put("msg", "请求参数有误，请您稍后再试");
                return map;
            }
            // 查询工位
            ProcedureVO procedureVO = procedureService.getProcedure(id);
            logger.debug("查询工序数据！pdvo=" + procedureVO);
            if (null != procedureVO) {
                map.put("procedure", procedureVO);
                // 获取全部角色数据
                map.put("msg", "ok");
            } else {
                map.put("msg", "查询工序信息有误，请您稍后再试");
            }
            logger.debug("查询工序数据成功！map=" + map);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "查询工序错误，请您稍后再试");
            logger.error("查询工序数据异常！", e);
        }
        return map;
    }

    /**
     * 删除工位
     * @return ok/fail
     */
    @RequestMapping(value = "/delProcedure", method = RequestMethod.POST)
    @ResponseBody
    public String delProcedure(@RequestParam("id") Integer id) {
        logger.debug("删除工序！id:" + id );
        String msg = "";
        try {
            if (null == id ) {
                logger.debug("删除工序，结果=请求参数有误，请您稍后再试");
                return "请求参数有误，请您稍后再试";
            }
            User existUser = (User) SecurityUtils.getSubject().getPrincipal();
            if (null == existUser) {
                logger.debug("删除工序，结果=您未登录或登录超时，请您登录后再试");
                return "您未登录或登录超时，请您登录后再试";
            }
            // 删除工位
            msg = procedureService.setDelProcedure(id, 1);
            logger.info("删除工序:" + msg + "！procedure=" + id + "，操作用户id:"
                    + existUser.getId() );
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除工序异常！", e);
            msg = "操作异常，请您稍后再试";
        }
        return msg;
    }

    /**
     * 恢复工序
     * @return ok/fail
     */
    @RequestMapping(value = "/revoverProcedure", method = RequestMethod.POST)
    @ResponseBody
    public String revoverProcedure(@RequestParam("id") Integer id) {
        logger.debug("恢复工序！id:" + id );
        String msg = "";
        try {
            if (null == id ) {
                logger.debug("恢复工序，结果=请求参数有误，请您稍后再试");
                return "请求参数有误，请您稍后再试";
            }
            User existUser = (User) SecurityUtils.getSubject().getPrincipal();
            if (null == existUser) {
                logger.debug("恢复工序，结果=您未登录或登录超时，请您登录后再试");
                return "您未登录或登录超时，请您登录后再试";
            }
            // 删除工位
            msg = procedureService.setDelProcedure(id, 0);
            logger.info("恢复工序:" + msg + "！procedure=" + id + "，操作用户id:"
                    + existUser.getId() );
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("恢复工序异常！", e);
            msg = "操作异常，请您稍后再试";
        }
        return msg;
    }
}
