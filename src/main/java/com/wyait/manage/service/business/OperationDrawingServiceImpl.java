package com.wyait.manage.service.business;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.dao.business.OperationDrawingMapper;
import com.wyait.manage.entity.business.OperationDrawingDTO;
import com.wyait.manage.entity.business.OperationDrawingSearchDTO;
import com.wyait.manage.entity.business.OperationDrawingSetDTO;
import com.wyait.manage.pojo.User;
import com.wyait.manage.pojo.business.OperationDrawing;
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
public class OperationDrawingServiceImpl implements OperationDrawingService {

    private static final Logger logger = LoggerFactory
            .getLogger(WorkStationServiceImpl.class);

    @Autowired
    private OperationDrawingMapper operationDrawingMapper;

    @Override
    public PageDataResult getOperationDrawings(Integer page, Integer limit, OperationDrawingSearchDTO operationDrawingSearchDTO) {
        PageDataResult pdr = new PageDataResult();
        //PageHelper.startPage(page, limit);
        List<OperationDrawingDTO> operationDrawingDTOs = operationDrawingMapper.getOperationDrawings(operationDrawingSearchDTO);
        int listLen = operationDrawingDTOs.size();
        List<OperationDrawingDTO> pagedOperationDrawingDTOs;
        if(limit * page > listLen){
            pagedOperationDrawingDTOs  = operationDrawingDTOs.subList(limit * (page - 1) , listLen);
        }else{
            pagedOperationDrawingDTOs = operationDrawingDTOs.subList(limit * (page - 1) , limit * page);
        }

        //获取分页查询后的数据
        PageInfo<OperationDrawingDTO> pageInfo = new PageInfo<>(operationDrawingDTOs);
        //设置获取到的总记录数total：
        pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
        pdr.setList(pagedOperationDrawingDTOs);
        return pdr;
    }

    @Override
    public String setOperationDrawing(OperationDrawingSetDTO operationDrawingSetDTO) {
        int workStationId;
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(operationDrawingSetDTO.getId() != null){

            OperationDrawing existInstruction = this.operationDrawingMapper.findOperationDrawingByIPV(operationDrawingSetDTO.getInvCode(), operationDrawingSetDTO.getProcedureCode(), operationDrawingSetDTO.getVersion());
            if(null != existInstruction
                    && !String.valueOf(existInstruction.getId()).equals(
                    String.valueOf(operationDrawingSetDTO.getId())) && operationDrawingSetDTO.getFile().getOriginalFilename().equals("")){
                return "该图纸已存在";
            }
            operationDrawingSetDTO.setInsertUid(user.getId());
            operationDrawingSetDTO.setIsDel(false);
            if(!operationDrawingSetDTO.getFile().getOriginalFilename().equals("")) {
                operationDrawingSetDTO.setFilePath(saveFileInServer(operationDrawingSetDTO.getFile()));
            }
            OperationDrawing existInstructionV = this.operationDrawingMapper.findOperationDrawingByInvCodeAndProcedureCode(operationDrawingSetDTO.getInvCode(), operationDrawingSetDTO.getProcedureCode());
            if (null != existInstructionV){
                if (existInstructionV.getId()==operationDrawingSetDTO.getId()){
                    operationDrawingSetDTO.setVersion(existInstructionV.getVersion() );
                }else {
                    operationDrawingSetDTO.setVersion(existInstructionV.getVersion() + 1);
                }
            } else {
                operationDrawingSetDTO.setVersion(1);
            }
            this.operationDrawingMapper.update(operationDrawingSetDTO);
        }else {
            //判断工位是否已经存在
            OperationDrawing existInstruction = this.operationDrawingMapper.findOperationDrawingByInvCodeAndProcedureCode(operationDrawingSetDTO.getInvCode(), operationDrawingSetDTO.getProcedureCode());
            if(null != existInstruction){
                //版本号+1
                operationDrawingSetDTO.setInsertUid(user.getId());
                operationDrawingSetDTO.setIsDel(false);
                System.out.println(operationDrawingSetDTO.getFile().getOriginalFilename());
                if(!operationDrawingSetDTO.getFile().getOriginalFilename().equals("")) {
                    operationDrawingSetDTO.setFilePath(saveFileInServer(operationDrawingSetDTO.getFile()));
                }
                operationDrawingSetDTO.setVersion(existInstruction.getVersion() + 1);
                this.operationDrawingMapper.insert(operationDrawingSetDTO);
            }else{
                //新增，版本号为1
                operationDrawingSetDTO.setInsertUid(user.getId());
                operationDrawingSetDTO.setIsDel(false);
                if(!operationDrawingSetDTO.getFile().getOriginalFilename().equals("")) {
                    operationDrawingSetDTO.setFilePath(saveFileInServer(operationDrawingSetDTO.getFile()));
                }
                this.operationDrawingMapper.insertTotallyNew(operationDrawingSetDTO);

            }
        }
        return "ok";
    }

    @Override
    public  String  setDelOperationDrawing (Integer id, Integer isDel){
        return this.operationDrawingMapper.setDelOperationDrawing(id, isDel) == 1 ? "ok": "删除失败，请您稍后再试";
    }


    @Override
    public OperationDrawing getOperationDrawing(Integer id) {
        return this.operationDrawingMapper.getOperationDrawing(id);
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
