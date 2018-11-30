package com.wyait.manage.service.manufacture;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.dao.manufacture.DeliveryMapper;
import com.wyait.manage.entity.manufacture.DailyDeliveryDTO;
import com.wyait.manage.entity.manufacture.DailyDeliverySearchDTO;
import com.wyait.manage.utils.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService{
    @Autowired
    private DeliveryMapper deliveryMapper;

    @Override
    public PageDataResult getDailyDeliveryList(Integer page, Integer limit,DailyDeliverySearchDTO dailyDeliverySearchDTO) throws ParseException {
        PageDataResult pdr = new PageDataResult();
        //PageHelper.startPage(page, limit);
        List<DailyDeliveryDTO> dailyDeliveryList = deliveryMapper.getDailyDeliveryList(dailyDeliverySearchDTO);
        for(DailyDeliveryDTO dailyDeliveryDTO : dailyDeliveryList){
            DecimalFormat df = new DecimalFormat("0.00%");
            Float f = (float)dailyDeliveryDTO.getDelyQty() / dailyDeliveryDTO.getPlanQty();
            String compRate = df.format(f);
            dailyDeliveryDTO.setCompletionRate(compRate);
            dailyDeliveryDTO.setState(getCurrentState(dailyDeliveryDTO.getPreDate(), dailyDeliveryDTO.getDelyQty(), dailyDeliveryDTO.getPlanQty()));
        }
        int listLen = dailyDeliveryList.size();
        List<DailyDeliveryDTO> pagedDeliveryList ;
        if(limit * page > listLen){
            pagedDeliveryList = dailyDeliveryList.subList(limit * (page - 1) , listLen);
        }else{
            pagedDeliveryList = dailyDeliveryList.subList(limit * (page - 1) , limit * page);
        }
        //获取分页查询后的数据
        PageInfo<DailyDeliveryDTO> pageInfo = new PageInfo<>(dailyDeliveryList);
        //设置获取到的总记录数total：
        pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
        pdr.setList(pagedDeliveryList);
        return pdr;
    }

    public String getCurrentState(String preDate, Integer delyQty, Integer planQty) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(preDate));
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(new Date());
        long mills = calendar.getTimeInMillis() - calendar1.getTimeInMillis();
        String state = "";
        if(mills <= 2*60*60*1000 ) {
            if(mills < 0){
                if(delyQty >= planQty){
                    state = "正常";
                }else{
                    state = "超期";
                }
            }else{
                state = "紧急";
            }
        }else{
            state = "正常";
        }
        return state;
    }
}
