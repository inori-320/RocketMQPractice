package cn.ltynote.rocketmq.service.impl;

import cn.ltynote.rocketmq.model.constant.TopicConstant;
import cn.ltynote.rocketmq.model.message.OrderMessage;
import cn.ltynote.rocketmq.model.message.VehicleMessage;
import cn.ltynote.rocketmq.service.RocketMQProducerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author litianyu
 * @version 1.0.0
 * @title RocketMQProducerServiceImpl
 * @create 2025/7/11 14:51
 */
@Slf4j
@Service
public class RocketMQProducerServiceImpl implements RocketMQProducerService {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void publishEntryEvent(VehicleMessage message) {
        rocketMQTemplate.syncSendOrderly(
                TopicConstant.VEHICLE_TOPIC + ":" + TopicConstant.TAG_ENTRY,
                MessageBuilder.withPayload(message).build(),
                message.getMessageId()
        );
        log.info("[入场事件] 消息发送成功：{}", message);
    }

    @Override
    public void publishExitEvent(VehicleMessage message) {
        rocketMQTemplate.syncSendOrderly(
                TopicConstant.VEHICLE_TOPIC + ":" + TopicConstant.TAG_EXIT,
                MessageBuilder.withPayload(message).build(),
                message.getMessageId()
        );
        log.info("[离场事件] 消息发送成功：{}", message);
    }

    @Override
    public boolean publishPaymentEvent(OrderMessage message) {
        SendResult sendResult = rocketMQTemplate.syncSendOrderly(
                TopicConstant.PAYMENT_TOPIC + ":" + TopicConstant.TAG_PAYMENT,
                MessageBuilder.withPayload(message)
                        .setHeader("order_id", message.getOrderId())
                        .setHeader("payment_type", message.getPaymentType())
                        .build(),
                message.getMessageId()
        );
        if (!Objects.equals(sendResult.getSendStatus(), SendStatus.SEND_OK)) {
            throw new RuntimeException("支付消息发送失败: " + sendResult);
        }
        log.info("[支付事件] 已发送, orderId={}, status={}",
                message.getOrderId(), sendResult.getSendStatus());
        return true;
    }
}
