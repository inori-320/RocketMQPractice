package cn.ltynote.rocketmq.model.message;

import cn.ltynote.rocketmq.core.BaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author litianyu
 * @version 1.0.0
 * @title VehicleMessage
 * @create 2025/7/14 13:32
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VehicleMessage extends BaseMessage {
    // 车牌号
    private String plateNumber;
    // 车辆类型
    private String carType;
    // 闸机编号
    private String gateId;
    // 时间
    private LocalDateTime Time;
    // 事件类型
    private EventType eventType;

    public enum EventType {
        ENTRY, EXIT, PAYMENT
    }
}
