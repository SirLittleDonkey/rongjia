package com.wyait.manage.service.business;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.dao.business.OperationInstructionMapper;
import com.wyait.manage.entity.business.oiDTO;
import com.wyait.manage.entity.business.oiSearchDTO;
import com.wyait.manage.entity.business.oiSetDTO;
import com.wyait.manage.pojo.User;
import com.wyait.manage.pojo.business.OperationInstruction;
import com.wyait.manage.service.basic.WorkStationServiceImpl;
import com.wyait.manage.utils.PageDataResult;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class OperationInstructionServiceImpl implements OperationInstructionService{
    private static final Logger logger = LoggerFactory
            .getLogger(WorkStationServiceImpl.class);

    @Autowired
    private OperationInstructionMapper operationInstructionMapper;

    @Override
    public PageDataResult getOperationInstructions(Integer page, Integer limit, oiSearchDTO oiSearchDTO) {
        PageDataResult pdr = new PageDataResult();
        //PageHelper.startPage(page, limit);
        List<oiDTO> oiDTOs = operationInstructionMapper.getOIs(oiSearchDTO);
        int listLen = oiDTOs.size();
        List<oiDTO> pagedOIDTOs;
        if(limit * page > listLen){
            pagedOIDTOs  = oiDTOs.subList(limit * (page - 1) , listLen);
        }else{
            pagedOIDTOs = oiDTOs.subList(limit * (page - 1) , limit * page);
        }

        //获取分页查询后的数据
        PageInfo<oiDTO> pageInfo = new PageInfo<>(oiDTOs);
        //设置获取到的总记录数total：
        pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
        pdr.setList(pagedOIDTOs);
        return pdr;
    }

    @Override
    public String setOperationInstruction(oiSetDTO oiSetDTO) {
        int workStationId;
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(oiSetDTO.getId() != null){

            OperationInstruction existInstruction = this.operationInstructionMapper.findOperationInstructionByIPV(oiSetDTO.getInvCode(), oiSetDTO.getProcedureCode(), oiSetDTO.getVersion());
            if(null != existInstruction
                    && !String.valueOf(existInstruction.getId()).equals(
                    String.valueOf(oiSetDTO.getId())) && oiSetDTO.getFile().getOriginalFilename().equals("")){
                return "该作业指导书已存在";
            }
            oiSetDTO.setInsertUid(user.getId());
            oiSetDTO.setIsDel(false);
            if(!oiSetDTO.getFile().getOriginalFilename().equals("")) {
                oiSetDTO.setFilePath(saveFileInServer(oiSetDTO.getFile()));
            }
            OperationInstruction existInstructionV = this.operationInstructionMapper.findOperationInstructionByInvCodeAndProcedureCode(oiSetDTO.getInvCode(), oiSetDTO.getProcedureCode());
            if (null != existInstructionV){
                if (existInstructionV.getId()==oiSetDTO.getId()){
                    oiSetDTO.setVersion(existInstructionV.getVersion());
                }else {
                    oiSetDTO.setVersion(existInstructionV.getVersion() + 1);
                }
            } else {
                oiSetDTO.setVersion(1);
            }
            this.operationInstructionMapper.update(oiSetDTO);
        }else {
            //判断工位是否已经存在
            OperationInstruction existInstruction = this.operationInstructionMapper.findOperationInstructionByInvCodeAndProcedureCode(oiSetDTO.getInvCode(), oiSetDTO.getProcedureCode());
            if(null != existInstruction){
                //版本号+1
                oiSetDTO.setInsertUid(user.getId());
                oiSetDTO.setIsDel(false);
                System.out.println(oiSetDTO.getFile().getOriginalFilename());
                if(!oiSetDTO.getFile().getOriginalFilename().equals("")) {
                    oiSetDTO.setFilePath(saveFileInServer(oiSetDTO.getFile()));
                }
                oiSetDTO.setVersion(existInstruction.getVersion() + 1);
                this.operationInstructionMapper.insert(oiSetDTO);
            }else{
                //新增，版本号为1
                oiSetDTO.setInsertUid(user.getId());
                oiSetDTO.setIsDel(false);
                if(!oiSetDTO.getFile().getOriginalFilename().equals("")) {
                    oiSetDTO.setFilePath(saveFileInServer(oiSetDTO.getFile()));
                }
                this.operationInstructionMapper.insertTotallyNew(oiSetDTO);

            }
        }
        return "ok";
    }

    @Override
    public OperationInstruction getOperationInstruction(Integer id) {
        return this.operationInstructionMapper.getOperationInstruction(id);
    }

    public String saveFileInServer(MultipartFile file){
        String fielName = UUID.randomUUID().toString();
        String filePath = "C:/rongjia/PDF/" + fielName + ".pdf";
        File desFile = new File(filePath);
        try {
            file.transferTo(desFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }
}
