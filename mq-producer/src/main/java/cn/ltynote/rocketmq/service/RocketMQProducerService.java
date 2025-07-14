package cn.ltynote.rocketmq.service;

import cn.ltynote.rocketmq.model.message.OrderMessage;
import cn.ltynote.rocketmq.model.message.VehicleMessage;

/**
 * @author litianyu
 * @version 1.0.0
 * @title RocketMQProducerService
 * @create 2025/7/11 14:43
 */

public interface RocketMQProducerService {
    void publishEntryEvent(VehicleMessage message);

    void publishExitEvent(VehicleMessage message);

    boolean publishPaymentEvent(OrderMessage message);
}
