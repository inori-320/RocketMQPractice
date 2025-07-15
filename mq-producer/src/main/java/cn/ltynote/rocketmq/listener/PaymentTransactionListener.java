package cn.ltynote.rocketmq.listener;

import cn.ltynote.rocketmq.model.message.OrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author litianyu
 * @version 1.0.0
 * @title PaymentTransactionListener
 * @create 2025/7/15 11:25
 */
@Slf4j
@Component
public class PaymentTransactionListener implements RocketMQLocalTransactionListener {
    // TODO 支付过程收到消费者回调，完成事务过程
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        return null;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        return null;
    }

}


