package com.wyait.manage.service.manufacture;

import com.wyait.manage.utils.PageDataResult;

public interface DeliveryService {
    PageDataResult getDailyDeliveryList(Integer page, Integer limit);
}
