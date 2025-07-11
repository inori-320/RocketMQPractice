package cn.ltynote.rocketmq.model.message;

import cn.ltynote.rocketmq.core.BaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author 71947
 * @version 1.0.0
 * @title ParkingMessage
 * @create 2025/7/11 14:50
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class VehicleMessage extends BaseMessage {
    // 车牌号
    private String plateNumber;
    // 车辆类型
    private String carType;
    // 入口闸机编号
    private String entryGate;
    // 入场时间
    private LocalDateTime entryTime;
    // 事件类型
    private EventType eventType;

    public enum EventType {
        ENTRY, EXIT, PAYMENT
    }
}
