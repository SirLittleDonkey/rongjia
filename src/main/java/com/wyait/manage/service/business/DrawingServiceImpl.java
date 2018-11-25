package com.wyait.manage.service.business;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.dao.business.DrawingMapper;
import com.wyait.manage.entity.business.DrawingDTO;
import com.wyait.manage.entity.business.DrawingSearchDTO;
import com.wyait.manage.entity.business.DrawingSetDTO;
import com.wyait.manage.pojo.User;
import com.wyait.manage.pojo.business.Drawing;
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
public class DrawingServiceImpl implements DrawingService {

    private static final Logger logger = LoggerFactory
            .getLogger(WorkStationServiceImpl.class);

    @Autowired
    private DrawingMapper drawingMapper;

    @Override
    public PageDataResult getDrawings(Integer page, Integer limit, DrawingSearchDTO drawingSearchDTO) {
        PageDataResult pdr = new PageDataResult();
        PageHelper.startPage(page, limit);
        List<DrawingDTO> drawingDTOs = drawingMapper.getDrawings(drawingSearchDTO);
        //获取分页查询后的数据
        PageInfo<DrawingDTO> pageInfo = new PageInfo<>(drawingDTOs);
        //设置获取到的总记录数total：
        pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
        pdr.setList(drawingDTOs);
        return pdr;
    }

    @Override
    public String setDrawing(DrawingSetDTO drawingSetDTO) {
        int workStationId;
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(drawingSetDTO.getId() != null){

            Drawing existInstruction = this.drawingMapper.findDrawingByIPV(drawingSetDTO.getInvCode(), drawingSetDTO.getProcedureCode(), drawingSetDTO.getVersion());
            if(null != existInstruction
                    && !String.valueOf(existInstruction.getId()).equals(
                    String.valueOf(drawingSetDTO.getId())) && drawingSetDTO.getFile().getOriginalFilename().equals("")){
                return "该图纸已存在";
            }
            drawingSetDTO.setInsertUid(user.getId());
            drawingSetDTO.setIsDel(false);
            if(!drawingSetDTO.getFile().getOriginalFilename().equals("")) {
                drawingSetDTO.setFilePath(saveFileInServer(drawingSetDTO.getFile()));
            }
            this.drawingMapper.update(drawingSetDTO);
        }else {
            //判断工位是否已经存在
            Drawing existInstruction = this.drawingMapper.findDrawingByInvCodeAndProcedureCode(drawingSetDTO.getInvCode(), drawingSetDTO.getProcedureCode());
            if(null != existInstruction){
                //版本号+1
                drawingSetDTO.setInsertUid(user.getId());
                drawingSetDTO.setIsDel(false);
                System.out.println(drawingSetDTO.getFile().getOriginalFilename());
                if(!drawingSetDTO.getFile().getOriginalFilename().equals("")) {
                    drawingSetDTO.setFilePath(saveFileInServer(drawingSetDTO.getFile()));
                }
                drawingSetDTO.setVersion(existInstruction.getVersion() + 1);
                this.drawingMapper.insert(drawingSetDTO);
            }else{
                //新增，版本号为1
                drawingSetDTO.setInsertUid(user.getId());
                drawingSetDTO.setIsDel(false);
                if(!drawingSetDTO.getFile().getOriginalFilename().equals("")) {
                    drawingSetDTO.setFilePath(saveFileInServer(drawingSetDTO.getFile()));
                }
                this.drawingMapper.insertTotallyNew(drawingSetDTO);

            }
        }
        return "ok";
    }

    @Override
    public  String  setDelDrawing (Integer id, Integer isDel){
        return this.drawingMapper.setDelDrawing(id, isDel) == 1 ? "ok": "删除失败，请您稍后再试";
    }


    @Override
    public Drawing getDrawing(Integer id) {
        return this.drawingMapper.getDrawing(id);
    }

    public String saveFileInServer(MultipartFile file){
        String fielName = UUID.randomUUID().toString();
        String filePath = "E:/rongjia/" + fielName + ".pdf";
        File desFile = new File(filePath);
        try {
            file.transferTo(desFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }
}
