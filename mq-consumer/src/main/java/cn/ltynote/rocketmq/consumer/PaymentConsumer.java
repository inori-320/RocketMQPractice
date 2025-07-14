package cn.ltynote.rocketmq.consumer;

import cn.ltynote.rocketmq.model.message.OrderMessage;
import org.apache.rocketmq.spring.core.RocketMQListener;

/**
 * @author litianyu
 * @version 1.0.0
 * @title PaymentConsumer
 * @create 2025/7/14 13:34
 */
public class PaymentConsumer implements RocketMQListener<OrderMessage> {
    @Override
    public void onMessage(OrderMessage orderMessage) {

    }
}
