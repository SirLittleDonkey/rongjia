package com.wyait.manage.dao.manufacture;

import com.wyait.manage.entity.manufacture.DailyDeliveryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeliveryMapper {
    List<DailyDeliveryDTO> getDailyDeliveryList();
}
