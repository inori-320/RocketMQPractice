package cn.ltynote.rocketmq.service.impl;

import cn.ltynote.rocketmq.mapper.FailEventMapper;
import cn.ltynote.rocketmq.mapper.PaymentEventMapper;
import cn.ltynote.rocketmq.model.message.OrderMessage;
import cn.ltynote.rocketmq.model.message.VehicleMessage;
import cn.ltynote.rocketmq.model.po.FailEventPO;
import cn.ltynote.rocketmq.model.po.PaymentPO;
import cn.ltynote.rocketmq.model.po.VehiclePO;
import cn.ltynote.rocketmq.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author litianyu
 * @version 1.0.0
 * @title PaymentServiceImpl
 * @create 2025/7/14 13:38
 */
@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentEventMapper paymentEventMapper;
    @Autowired
    private FailEventMapper failEventMapper;

    @Override
    public boolean existsByMessageId(String messageId) {
        return paymentEventMapper.countByMessageId(messageId) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleMessage(OrderMessage message) {
        PaymentPO po = new PaymentPO();
        po.setMessageId(message.getMessageId());
        po.setOrderId(message.getOrderId());
        po.setPlateNumber(message.getPlateNumber());
        po.setPaymentType(message.getPaymentType());
        po.setDurationMinutes(message.getDurationMinutes());
        po.setAmount(message.getAmount());
        po.setStatus(message.getStatus().name());
        po.setPaymentTime(LocalDateTime.now());
        boolean success = paymentEventMapper.savePaymentEvent(po);
        if (success) {
            log.info("支付成功：{}", message.getMessageId());
        } else {
            FailEventPO failEventPO = new FailEventPO();
            failEventPO.setMessageId(message.getMessageId());
            failEventPO.setOrderId(message.getOrderId());
            failEventPO.setPlateNumber(message.getPlateNumber());
            failEventPO.setAmount(message.getAmount());
            failEventPO.setEventType(message.getStatus().name());
            failEventPO.setFailReason("支付失败");
            failEventPO.setCreatedAt(LocalDateTime.now());
            failEventPO.setDurationMinutes(message.getDurationMinutes());
            failEventPO.setEventTime(message.getPaymentTime());
            failEventMapper.saveFailEvent(failEventPO);
            log.warn("支付失败：{}", message.getMessageId());
        }
    }
}
