package cn.ltynote.rocketmq.model.message;

import cn.ltynote.rocketmq.core.BaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author litianyu
 * @version 1.0.0
 * @title OrderMessage
 * @create 2025/7/11 14:47
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderMessage extends BaseMessage {
    // 车牌号
    private String plateNumber;
    // 订单id
    private String orderId;
    // 金额
    private BigDecimal amount;
    // 支付方式
    private String paymentType;
    // 停放时长
    private Integer durationMinutes;
    // 订单状态
    private OrderStatus status;

    // 状态枚举
    public enum OrderStatus {
        CREATED, PAID
    }
}
