package com.wyait.manage.service.manufacture;

import com.wyait.manage.utils.PageDataResult;

import java.text.ParseException;

public interface DeliveryService {
    PageDataResult getDailyDeliveryList(Integer page, Integer limit) throws ParseException;
}
