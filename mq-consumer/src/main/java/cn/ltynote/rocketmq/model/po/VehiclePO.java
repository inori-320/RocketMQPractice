package cn.ltynote.rocketmq.model.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author litianyu
 * @version 1.0.0
 * @title VehiclePO
 * @create 2025/7/14 13:51
 */
@Data
public class VehiclePO {
    private Long id;
    private String messageId;
    private String plateNumber;
    // small / medium / large
    private String carType;
    private String gateId;
    // ENTRY / EXIT
    private String eventType;
    private LocalDateTime eventTime;
    private LocalDateTime createdAt;
}
