package com.wyait.manage.service.manufacture;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.dao.manufacture.DeliveryMapper;
import com.wyait.manage.entity.manufacture.DailyDeliveryDTO;
import com.wyait.manage.utils.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService{
    @Autowired
    private DeliveryMapper deliveryMapper;

    @Override
    public PageDataResult getDailyDeliveryList(Integer page, Integer limit) {
        PageDataResult pdr = new PageDataResult();
        PageHelper.startPage(page, limit);
        List<DailyDeliveryDTO> workStations = deliveryMapper.getDailyDeliveryList();
        //获取分页查询后的数据
        PageInfo<DailyDeliveryDTO> pageInfo = new PageInfo<>(workStations);
        //设置获取到的总记录数total：
        pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
        pdr.setList(workStations);
        return pdr;
    }
}
