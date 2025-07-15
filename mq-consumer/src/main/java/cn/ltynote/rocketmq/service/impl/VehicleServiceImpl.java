package cn.ltynote.rocketmq.service.impl;

import cn.ltynote.rocketmq.mapper.FailEventMapper;
import cn.ltynote.rocketmq.mapper.VehicleEventMapper;
import cn.ltynote.rocketmq.model.message.VehicleMessage;
import cn.ltynote.rocketmq.model.po.FailEventPO;
import cn.ltynote.rocketmq.model.po.VehiclePO;
import cn.ltynote.rocketmq.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author litianyu
 * @version 1.0.0
 * @title VehicleServiceImpl
 * @create 2025/7/14 13:39
 */
@Slf4j
@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleEventMapper vehicleEventMapper;
    @Autowired
    private FailEventMapper failEventMapper;

    @Override
    public boolean existsByMessageId(String messageId) {
        return vehicleEventMapper.countByMessageId(messageId) > 0;
    }

    @Override
    public void handleMessage(VehicleMessage message) {
        VehiclePO po = new VehiclePO();
        po.setMessageId(message.getMessageId());
        po.setPlateNumber(message.getPlateNumber());
        po.setCarType(message.getCarType());
        po.setGateId(message.getGateId());
        po.setEventType(message.getEventType().name());
        po.setEventTime(message.getTime());
        po.setCreatedAt(LocalDateTime.now());
        boolean success = vehicleEventMapper.saveVehicleEvent(po);
        if (success) {
            log.info("车辆事件处理成功：{}", message.getMessageId());
        } else {
            FailEventPO failEventPO = new FailEventPO();
            failEventPO.setPlateNumber(message.getPlateNumber());
            failEventPO.setMessageId(message.getMessageId());
            failEventPO.setGateId(message.getGateId());
            failEventPO.setEventType(message.getEventType().name());
            failEventPO.setEventTime(message.getTime());
            failEventPO.setFailReason("车辆事件入库失败");
            failEventPO.setCreatedAt(LocalDateTime.now());
            failEventMapper.saveFailEvent(failEventPO);
            log.warn("车辆事件入库失败：{}", message.getMessageId());
        }
    }
}
