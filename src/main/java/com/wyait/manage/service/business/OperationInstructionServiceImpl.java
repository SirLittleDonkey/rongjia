package com.wyait.manage.service.business;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.dao.business.OperationInstructionMapper;
import com.wyait.manage.entity.business.oiDTO;
import com.wyait.manage.entity.business.oiSearchDTO;
import com.wyait.manage.service.basic.WorkStationServiceImpl;
import com.wyait.manage.utils.PageDataResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationInstructionServiceImpl implements OperationInstructionService{
    private static final Logger logger = LoggerFactory
            .getLogger(WorkStationServiceImpl.class);

    @Autowired
    private OperationInstructionMapper operationInstructionMapper;

    @Override
    public PageDataResult getOperationInstructions(Integer page, Integer limit, oiSearchDTO oiSearchDTO) {
        PageDataResult pdr = new PageDataResult();
        PageHelper.startPage(page, limit);
        List<oiDTO> oiDTOs = operationInstructionMapper.getOIs(oiSearchDTO);
        //获取分页查询后的数据
        PageInfo<oiDTO> pageInfo = new PageInfo<>(oiDTOs);
        //设置获取到的总记录数total：
        pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
        pdr.setList(oiDTOs);
        return pdr;
    }
}
