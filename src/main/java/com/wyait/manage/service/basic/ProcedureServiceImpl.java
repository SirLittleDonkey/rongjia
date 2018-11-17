package com.wyait.manage.service.basic;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.dao.basic.ProcedureMapper;
import com.wyait.manage.entity.basic.ProcedureDTO;
import com.wyait.manage.entity.basic.ProcedureSearchDTO;
import com.wyait.manage.entity.basic.ProcedureVO;
import com.wyait.manage.pojo.User;
import com.wyait.manage.pojo.basic.Procedure;
import com.wyait.manage.utils.PageDataResult;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ProcedureServiceImpl implements ProcedureService{
    private static final Logger logger = LoggerFactory
            .getLogger(WorkStationServiceImpl.class);

    @Autowired
    private ProcedureMapper procedureMapper;

    @Override
    public PageDataResult getProcedures(Integer page, Integer limit, ProcedureSearchDTO searchDTO) {
        PageDataResult pdr = new PageDataResult();
        PageHelper.startPage(page, limit);
        List<ProcedureDTO> procedures = procedureMapper.getProcedures(searchDTO);
        //获取分页查询后的数据
        PageInfo<ProcedureDTO> pageInfo = new PageInfo<>(procedures);
        //设置获取到的总记录数total：
        pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
        pdr.setList(procedures);
        return pdr;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {
            RuntimeException.class, Exception.class })
    public String setProcedure(Procedure procedure) {
        int procedureId;
        User existUser = (User) SecurityUtils.getSubject().getPrincipal();

        if(procedure.getId() != null){
            Procedure existProcedure = this.procedureMapper.findProcedureByProcedureCode(procedure.getProcedureCode());
            if(null != existProcedure
                    && !String.valueOf(existProcedure.getId()).equals(
                    String.valueOf(procedure.getId()))){
                return "该工序号已存在";
            }
            existProcedure.setInsertUid(existUser.getId());
            existProcedure.setUpdateTime(new Date());
            existProcedure.setProcedureCode(procedure.getProcedureCode());
            existProcedure.setProcedureName(procedure.getProcedureName());
            this.procedureMapper.updateByPrimaryKeySelective(existProcedure);
        }else {
            //判断工位是否已经存在
            Procedure existProcedure = this.procedureMapper.findProcedureByProcedureCode(procedure.getProcedureCode());
            if(null != existProcedure){
                return "该工序号已存在";
            }
            procedure.setInsertUid(existUser.getId());
            procedure.setInsertTime(new Date());
            procedure.setIsDel(false);
            this.procedureMapper.insert(procedure);
        }
        return "ok";
    }

    @Override
    public ProcedureVO getProcedure(Integer id) {
        return this.procedureMapper.getProcedure(id);
    }

    @Override
    public String setDelProcedure(Integer id, Integer isDel) {
        return this.procedureMapper.setDelProcedure(id, isDel)  == 1 ? "ok": "删除失败，请您稍后再试";
    }
}
