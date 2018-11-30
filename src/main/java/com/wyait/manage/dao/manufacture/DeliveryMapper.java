package com.wyait.manage.dao.manufacture;

import com.wyait.manage.entity.manufacture.DailyDeliveryDTO;
import com.wyait.manage.entity.manufacture.DailyDeliverySearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DeliveryMapper {
    List<DailyDeliveryDTO> getDailyDeliveryList(@Param("dailyDeliverySearchDTO") DailyDeliverySearchDTO dailyDeliverySearchDTO);
}
