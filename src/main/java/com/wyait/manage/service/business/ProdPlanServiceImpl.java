package com.wyait.manage.service.business;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.dao.business.ProdPlanMapper;
import com.wyait.manage.entity.business.ProdPlanDTO;
import com.wyait.manage.entity.business.ProdPlanSearchDTO;
import com.wyait.manage.entity.business.ProdPlanVO;
import com.wyait.manage.entity.business.ppSetDTO;
import com.wyait.manage.pojo.User;
import com.wyait.manage.utils.ExcelUtil;
import com.wyait.manage.utils.PageDataResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Service
public class ProdPlanServiceImpl implements ProdPlanService{

    @Autowired
    private ProdPlanMapper prodPlanMapper;

    @Override
    public PageDataResult getProdPlans(Integer page, Integer limit, ProdPlanSearchDTO prodPlanSearchDTO) {
        PageDataResult pdr = new PageDataResult();
        PageHelper.startPage(page, limit);
        List<ProdPlanDTO> prodPlans = prodPlanMapper.getProdPlans(prodPlanSearchDTO);
        //获取分页查询后的数据
        PageInfo<ProdPlanDTO> pageInfo = new PageInfo<>(prodPlans);
        //设置获取到的总记录数total：
        pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
        pdr.setList(prodPlans);

        return pdr;
    }

    @Override
    public String setProdPlan(ppSetDTO ppSetDTO) {
        int prodPlanId;
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(ppSetDTO.getId() != null){
            //修改
            ppSetDTO.setInsertUid(user.getId());
            this.prodPlanMapper.update(ppSetDTO);
        }else{
            //新增
            ppSetDTO.setInsertUid(user.getId());
            ppSetDTO.setIsDel(false);
            this.prodPlanMapper.insert(ppSetDTO);
        }
        return "ok";
    }

    @Override
    public ProdPlanVO getProdPlan(Integer id) {
        return this.prodPlanMapper.getProdPlan(id);
    }

    @Override
    public Map<String, Object> uploadProdPlan(MultipartFile file) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        String fielName = UUID.randomUUID().toString();
        String name = file.getOriginalFilename();
        String suffix  = name.substring(name.lastIndexOf(".")+1);
        String filePath = "E:/rongjia/" + fielName + "." + suffix;
        File uploadFile = new File(filePath);
        file.transferTo(uploadFile);
        FileInputStream in = new FileInputStream(uploadFile);

        Workbook wb = ExcelUtil.getWorkbok(in, uploadFile);
        Sheet sheet = wb.getSheetAt(0);
        Row head = sheet.getRow(0);
        String headStr = "";
        for(Cell cell: head){
            headStr = headStr + cell.toString();
        }
        if(!headStr.equals("客户编码车间号工位号产品编码工序号计划时间计划数加工小时数")){
            map.put("msg","上传的文件有误！请重新上传！");
            return map;
        }else {
            map.put("msg", "ok");
            return map;
        }
    }

}
