package com.wyait.manage.service.manufacture;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.dao.manufacture.DeliveryMapper;
import com.wyait.manage.entity.manufacture.DailyDeliveryDTO;
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
    public PageDataResult getDailyDeliveryList(Integer page, Integer limit) throws ParseException {
        PageDataResult pdr = new PageDataResult();
        PageHelper.startPage(page, limit);
        List<DailyDeliveryDTO> dailyDeliveryList = deliveryMapper.getDailyDeliveryList();
        for(DailyDeliveryDTO dailyDeliveryDTO : dailyDeliveryList){
            DecimalFormat df = new DecimalFormat("0.00%");
            Float f = (float)dailyDeliveryDTO.getDelyQty() / dailyDeliveryDTO.getPlanQty();
            String compRate = df.format(f);
            dailyDeliveryDTO.setCompletionRate(compRate);
            dailyDeliveryDTO.setState(getCurrentState(dailyDeliveryDTO.getPreDate(), dailyDeliveryDTO.getDelyQty(), dailyDeliveryDTO.getPlanQty()));
        }
        //获取分页查询后的数据
        PageInfo<DailyDeliveryDTO> pageInfo = new PageInfo<>(dailyDeliveryList);
        //设置获取到的总记录数total：
        pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
        pdr.setList(dailyDeliveryList);
        return pdr;
    }

    public String getCurrentState(String preDate, Integer delyQty, Integer planQty) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(preDate));
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(new Date());
        long mills = calendar.getTimeInMillis() - calendar1.getTimeInMillis();
        if(mills <= 2*60*60*1000 ){
            if(mills < 0){
                return "超期";
            }else{
                if(delyQty < planQty){
                    return "紧急";
                }else{
                    return  "正常";
                }
            }
        }else{
            return "正常";
        }
    }
}
