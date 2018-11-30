package com.wyait.manage.service.manufacture;

import com.wyait.manage.entity.manufacture.DailyDeliverySearchDTO;
import com.wyait.manage.utils.PageDataResult;

import java.text.ParseException;

public interface DeliveryService {
    PageDataResult getDailyDeliveryList(Integer page, Integer limit, DailyDeliverySearchDTO dailyDeliverySearchDTO) throws ParseException;
}
