package cn.ltynote.rocketmq.model.po;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author litianyu
 * @version 1.0.0
 * @title FailEventPO
 * @create 2025/7/14 14:36
 */
@Data
public class FailEventPO {
    private Long id;
    // 订单id
    private String orderId;
    // 车牌号
    private String plateNumber;
    // 消息id
    private String messageId;
    // 闸机id
    private String gateId;
    // 事件类型
    private String eventType;
    // 事件时间
    private LocalDateTime eventTime;
    // 失败原因
    private String failReason;
    // 创建时间
    private LocalDateTime createdAt;
    // 金额
    private BigDecimal amount;
    // 停放时间
    private Integer durationMinutes;
}
