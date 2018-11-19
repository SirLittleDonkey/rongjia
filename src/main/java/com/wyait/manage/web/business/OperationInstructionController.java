package com.wyait.manage.web.business;

import com.wyait.manage.entity.business.oiSearchDTO;
import com.wyait.manage.entity.business.oiSetDTO;
import com.wyait.manage.pojo.User;
import com.wyait.manage.pojo.business.OperationInstruction;
import com.wyait.manage.service.business.OperationInstructionService;
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

    /**
     * 设置工位[新增或更新]
     * @return ok/fail
     */
    @RequestMapping(value = "/setOperationInstruction", method = RequestMethod.POST)
    @ResponseBody
    public String setOperationInstruction(oiSetDTO oiSetDTO) {
        logger.debug("设置作业指导书[新增或更新]！oiSetDTO:" + oiSetDTO);
        try {
            if (null == oiSetDTO) {
                logger.debug("设置作业指导书[新增或更新]，结果=请您填写作业指导书信息");
                return "请您填写作业指导书信息";
            }
            User existUser = (User) SecurityUtils.getSubject().getPrincipal();
            if (null == existUser) {
                logger.debug("设置工位[新增或更新]，结果=您未登录或登录超时，请您登录后再试");
                return "您未登录或登录超时，请您登录后再试";
            }
            // 设置作业指导书[新增或更新]
            logger.info("设置作业指导书[新增或更新]成功！workStation=" + oiSetDTO
                    + "，操作的用户ID=" + existUser.getId());
            return operationInstructionService.setOperationInstruction(oiSetDTO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("设置作业指导书[新增或更新]异常！", e);
            return "操作异常，请您稍后再试";
        }
    }

    @RequestMapping(value = "/getOperationInstruction", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getOperationInstruction(@RequestParam("id") Integer id){
        logger.debug("查询作业指导书数据！id:" + id);
        Map<String, Object> map = new HashMap<>();
        try {
            if (null == id) {
                logger.debug("查询作业指导书数据==请求参数有误，请您稍后再试");
                map.put("msg", "请求参数有误，请您稍后再试");
                return map;
            }
            // 查询工位
            OperationInstruction operationInstruction = operationInstructionService.getOperationInstruction(id);
            logger.debug("查询作业指导书数据！oi=" + operationInstruction);
            if (null != operationInstruction) {
                map.put("operationInstruction", operationInstruction);
                // 获取全部角色数据
                map.put("msg", "ok");
            } else {
                map.put("msg", "查询作业指导书信息有误，请您稍后再试");
            }
            logger.debug("查询作业指导书数据成功！map=" + map);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "查询作业指导书错误，请您稍后再试");
            logger.error("查询作业指导书数据异常！", e);
        }
        return map;
    }

    @RequestMapping(value = "/getOperationInstructionPDF", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getOperationInstructionPDF(@RequestParam("id") Integer id){
        logger.debug("查询作业指导书数据！id:" + id);
        Map<String, Object> map = new HashMap<>();
        try {
            if (null == id) {
                logger.debug("查询作业指导书数据==请求参数有误，请您稍后再试");
                map.put("msg", "请求参数有误，请您稍后再试");
                return map;
            }
            // 查询工位
            OperationInstruction operationInstruction = operationInstructionService.getOperationInstruction(id);
            logger.debug("查询作业指导书数据！oi=" + operationInstruction);
            if (null != operationInstruction) {
                map.put("fileName", operationInstruction.getFilepath());
                // 获取全部角色数据
                map.put("msg", "ok");
            } else {
                map.put("msg", "查询作业指导书信息有误，请您稍后再试");
            }
            logger.debug("查询作业指导书数据成功！map=" + map);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "查询作业指导书错误，请您稍后再试");
            logger.error("查询作业指导书数据异常！", e);
        }
        return map;
    }

    @RequestMapping(value = "/getPDF", method = RequestMethod.GET)
    public void getPDF(@RequestParam("filePath")String filePath, HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        String fileName = filePath;

        File file = new File(fileName);

        FileInputStream inputStream = new FileInputStream(file);
        byte[] data = new byte[(int)file.length()];
        int length = inputStream.read(data);
        inputStream.close();

        OutputStream stream = response.getOutputStream();
        stream.write(data);
        stream.flush();
        stream.close();
    }
}
