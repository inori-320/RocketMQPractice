package cn.ltynote.rocketmq.service;

import cn.ltynote.rocketmq.model.message.OrderMessage;

/**
 * @author litianyu
 * @version 1.0.0
 * @title PaymentService
 * @create 2025/7/14 13:38
 */
public interface PaymentService {
    boolean existsByMessageId(String messageId);

    void handleMessage(OrderMessage message);
}
