package cn.ltynote.rocketmq.model.po;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author litianyu
 * @version 1.0.0
 * @title PaymentPO
 * @create 2025/7/14 13:51
 */
@Data
public class PaymentPO {
    private Long id;
    private String messageId;
    private String orderId;
    private String plateNumber;
    private String paymentType;
    private Integer durationMinutes;
    private BigDecimal amount;
    // CREATED / PAID
    private String status;
    private LocalDateTime paymentTime;
}
