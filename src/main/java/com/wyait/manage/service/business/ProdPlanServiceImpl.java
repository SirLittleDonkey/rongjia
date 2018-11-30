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
        //PageHelper.startPage(page, limit);
        List<ProdPlanDTO> prodPlans = prodPlanMapper.getProdPlans(prodPlanSearchDTO);
        int listLen = prodPlans.size();
        List<ProdPlanDTO> pagedProdPlanDTO;
        if(limit * page > listLen){
            pagedProdPlanDTO = prodPlans.subList(limit * (page - 1) , listLen);
        }else{
            pagedProdPlanDTO = prodPlans.subList(limit * (page - 1) , limit * page);
        }
        //获取分页查询后的数据
        PageInfo<ProdPlanDTO> pageInfo = new PageInfo<>(prodPlans);
        //设置获取到的总记录数total：
        pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
        pdr.setList(pagedProdPlanDTO);

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
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String fielName = UUID.randomUUID().toString();
        String name = file.getOriginalFilename();
        String suffix  = name.substring(name.lastIndexOf(".")+1);
        String filePath = "C:/rongjia/Excel/" + fielName + "." + suffix;
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
        if(!headStr.equals("客户编号车间号工位号产品编码工序号计划时间计划数加工小时数")){
            map.put("msg","上传的文件有误！请重新上传！");
            return map;
        }else {
            int count = 0;
            for(Row row: sheet){
                if(count < 1){
                    count ++;
                    continue;
                }

                //客户编码
                Cell cusCell = row.getCell(0);
                String cusCode = cusCell.toString();
                int countCus = prodPlanMapper.getExistCus(cusCode);
                if(countCus < 1){
                    map.put("msg", "客户号:" + cusCode + "不存在！");
                    return map;
                }
                //车间号
                Cell workShopCell = row.getCell(1);
                String workShopCode = workShopCell.toString();
                int countWorkShop = prodPlanMapper.getExistWorkShop(workShopCode);
                if(countWorkShop < 1){
                    map.put("msg", "车间号:" + workShopCode + "不存在！");
                    return map;
                }
                //工位号
                Cell workStationCell = row.getCell(2);
                String workStationCode = workStationCell.toString();
                int countWorkStation = prodPlanMapper.getExistWorkStation(workStationCode);
                if(countWorkStation < 1){
                    map.put("msg", "工位号:" + workStationCode + "不存在！");
                    return map;
                }
                //产品编码
                Cell invCell = row.getCell(3);
                String invCode = invCell.toString();
                int countInv = prodPlanMapper.getExistInv(invCode);
                if(countInv < 1){
                    map.put("msg", "产品编码:" + invCode + "不存在！");
                    return map;
                }
                //工序号
                Cell procedureCell = row.getCell(4);
                String procedureCode = procedureCell.toString();
                int countProcedure = prodPlanMapper.getExistProcedure(procedureCode);
                if(countProcedure < 1){
                    map.put("msg", "产品编码:" + procedureCode + "不存在！");
                    return map;
                }
                //计划时间
                Cell planDateCell = row.getCell(5);
                String planDate = planDateCell.toString();
                //计划数
                Cell planQtyCell = row.getCell(6);
                String planQty = planQtyCell.toString();
                //加工小时数
                Cell planHourCell = row.getCell(7);
                String planHour = planHourCell.toString();

                ppSetDTO ppSetDTO = new ppSetDTO();
                ppSetDTO.setCusCode(cusCode);
                ppSetDTO.setWorkshopCode(workShopCode);
                ppSetDTO.setWorkstationCode(workStationCode);
                ppSetDTO.setProcedureCode(procedureCode);
                ppSetDTO.setInvCode(invCode);
                ppSetDTO.setPlanDate(planDate);
                ppSetDTO.setPlanQty(Integer.valueOf(planQty));
                ppSetDTO.setPlanHour(Double.valueOf(planHour));
                ppSetDTO.setInsertUid(user.getId());
                ppSetDTO.setIsDel(false);
                this.prodPlanMapper.insert(ppSetDTO);
            }
            map.put("msg", "ok");
            return map;
        }
    }

}
