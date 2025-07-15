package cn.ltynote.rocketmq.service;

import cn.ltynote.rocketmq.model.dto.VehicleDTO;
import cn.ltynote.rocketmq.model.message.VehicleMessage;

/**
 * @author litianyu
 * @version 1.0.0
 * @title VehicleService
 * @create 2025/7/14 13:38
 */
public interface VehicleService {
    boolean existsByMessageId(String messageId);
    void handleMessage(VehicleMessage message);
}
