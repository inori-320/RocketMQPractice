package cn.ltynote.rocketmq.consumer;

import cn.ltynote.rocketmq.model.constant.TopicConstant;
import cn.ltynote.rocketmq.model.message.OrderMessage;
import cn.ltynote.rocketmq.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author litianyu
 * @version 1.0.0
 * @title PaymentConsumer
 * @create 2025/7/14 13:34
 */
@Slf4j
@Service
@RocketMQMessageListener(
        topic = TopicConstant.PAYMENT_TOPIC,
        selectorExpression = TopicConstant.TAG_PAYMENT,
        consumerGroup = "payment_group"
)
public class PaymentConsumer implements RocketMQListener<OrderMessage> {
    @Autowired
    private PaymentService paymentService;

    @Override
    public void onMessage(OrderMessage message) {
        log.info("收到支付消息：{}", message);
        // 幂等校验,messageId是否已处理
        if (paymentService.existsByMessageId(message.getMessageId())) {
            return;
        }
        try {
            paymentService.handleMessage(message);
        } catch (Exception e) {
            log.error("处理支付消息失败，消息ID={}，错误信息：{}", message.getMessageId(), e.getMessage(), e);
        }
    }
}
