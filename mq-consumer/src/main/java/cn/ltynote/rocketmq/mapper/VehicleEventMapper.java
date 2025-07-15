package cn.ltynote.rocketmq.mapper;

import cn.ltynote.rocketmq.model.po.VehiclePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author litianyu
 * @version 1.0.0
 * @title VehicleEventMapper
 * @create 2025/7/14 13:53
 */
@Mapper
@Repository
public interface VehicleEventMapper {

    boolean saveVehicleEvent(VehiclePO po);

    int countByMessageId(@Param("messageId") String messageId);
}
