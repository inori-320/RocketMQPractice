package cn.ltynote.rocketmq.consumer;

import cn.ltynote.rocketmq.model.constant.TopicConstant;
import cn.ltynote.rocketmq.model.dto.VehicleDTO;
import cn.ltynote.rocketmq.model.message.VehicleMessage;
import cn.ltynote.rocketmq.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author litianyu
 * @version 1.0.0
 * @title EventConsumer
 * @create 2025/7/14 13:31
 */
@Slf4j
@Service
@RocketMQMessageListener(
        topic = TopicConstant.VEHICLE_TOPIC,
        selectorExpression = TopicConstant.TAG_ENTRY + " || " + TopicConstant.TAG_EXIT,
        consumerGroup = "vehicle_consumer_group"
)
public class VehicleConsumer implements RocketMQListener<VehicleMessage> {
    @Autowired
    private VehicleService vehicleService;

    @Override
    public void onMessage(VehicleMessage message) {
        log.info("收到车辆消息：{}", message);
        // 幂等校验,messageId是否已处理
        if (vehicleService.existsByMessageId(message.getMessageId())) {
            return;
        }
        try {
            vehicleService.handleMessage(message);
        } catch (Exception e) {
            log.error("处理车辆消息失败，消息ID={}，错误信息：{}", message.getMessageId(), e.getMessage(), e);
        }
    }
}
