package com.wyait.manage.service.business;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.dao.business.QualityInstructionMapper;
import com.wyait.manage.entity.business.qiDTO;
import com.wyait.manage.entity.business.qiSearchDTO;
import com.wyait.manage.entity.business.qiSetDTO;
import com.wyait.manage.pojo.User;
import com.wyait.manage.pojo.business.QualityInstruction;
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
public class QualityInstructionServiceImpl implements QualityInstructionService {

    private static final Logger logger = LoggerFactory
            .getLogger(WorkStationServiceImpl.class);

    @Autowired
    private QualityInstructionMapper qualityInstructionMapper;

    @Override
    public PageDataResult getQualityInstructions(Integer page, Integer limit, qiSearchDTO qiSearchDTO) {
        PageDataResult pdr = new PageDataResult();
        //PageHelper.startPage(page, limit);
        List<qiDTO> qiDTOs = qualityInstructionMapper.getQIs(qiSearchDTO);
        int listLen = qiDTOs.size();
        List<qiDTO> pagedQiDTOs;
        if(limit * page > listLen){
            pagedQiDTOs = qiDTOs.subList(limit * (page - 1) , listLen);
        }else{
            pagedQiDTOs = qiDTOs.subList(limit * (page - 1) , limit * page);
        }
        //获取分页查询后的数据
        PageInfo<qiDTO> pageInfo = new PageInfo<>(qiDTOs);
        //设置获取到的总记录数total：
        pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
        pdr.setList(pagedQiDTOs);
        return pdr;
    }

    @Override
    public String setQualityInstruction(qiSetDTO qiSetDTO) {
        int workStationId;
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(qiSetDTO.getId() != null){

            QualityInstruction existInstruction = this.qualityInstructionMapper.findQualityInstructionByIPV(qiSetDTO.getInvCode(), qiSetDTO.getProcedureCode(), qiSetDTO.getVersion());
            if(null != existInstruction
                    && !String.valueOf(existInstruction.getId()).equals(
                    String.valueOf(qiSetDTO.getId())) && qiSetDTO.getFile().getOriginalFilename().equals("")){
                return "该检验指导书已存在";
            }
            qiSetDTO.setInsertUid(user.getId());
            qiSetDTO.setIsDel(false);
            if(!qiSetDTO.getFile().getOriginalFilename().equals("")) {
                qiSetDTO.setFilePath(saveFileInServer(qiSetDTO.getFile()));
            }
            QualityInstruction existInstructionV = this.qualityInstructionMapper.findQualityInstructionByInvCodeAndProcedureCode(qiSetDTO.getInvCode(), qiSetDTO.getProcedureCode());
            if (null != existInstructionV){
                if (existInstructionV.getId()==qiSetDTO.getId()){
                    qiSetDTO.setVersion(existInstructionV.getVersion());
                } else {
                    qiSetDTO.setVersion(existInstructionV.getVersion() + 1);
                }
            } else {
                qiSetDTO.setVersion(1);
            }
            this.qualityInstructionMapper.update(qiSetDTO);
        }else {
            //判断工位是否已经存在
            QualityInstruction existInstruction = this.qualityInstructionMapper.findQualityInstructionByInvCodeAndProcedureCode(qiSetDTO.getInvCode(), qiSetDTO.getProcedureCode());
            if(null != existInstruction){
                //版本号+1
                qiSetDTO.setInsertUid(user.getId());
                qiSetDTO.setIsDel(false);
                System.out.println(qiSetDTO.getFile().getOriginalFilename());
                if(!qiSetDTO.getFile().getOriginalFilename().equals("")) {
                    qiSetDTO.setFilePath(saveFileInServer(qiSetDTO.getFile()));
                }
                qiSetDTO.setVersion(existInstruction.getVersion() + 1);
                this.qualityInstructionMapper.insert(qiSetDTO);
            }else{
                //新增，版本号为1
                qiSetDTO.setInsertUid(user.getId());
                qiSetDTO.setIsDel(false);
                if(!qiSetDTO.getFile().getOriginalFilename().equals("")) {
                    qiSetDTO.setFilePath(saveFileInServer(qiSetDTO.getFile()));
                }
                this.qualityInstructionMapper.insertTotallyNew(qiSetDTO);

            }
        }
        return "ok";
    }

    @Override
    public  String  setDelQualityInstruction (Integer id, Integer isDel){
        return this.qualityInstructionMapper.setDelQualityInstruction(id, isDel) == 1 ? "ok": "删除失败，请您稍后再试";
    }


    @Override
    public QualityInstruction getQualityInstruction(Integer id) {
        return this.qualityInstructionMapper.getQualityInstruction(id);
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
