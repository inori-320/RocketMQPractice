package cn.ltynote.rocketmq.consumer;

import cn.ltynote.rocketmq.model.message.VehicleMessage;
import org.apache.rocketmq.spring.core.RocketMQListener;

/**
 * @author litianyu
 * @version 1.0.0
 * @title EventConsumer
 * @create 2025/7/14 13:31
 */
public class VehicleConsumer implements RocketMQListener<VehicleMessage> {
    @Override
    public void onMessage(VehicleMessage vehicleMessage) {

    }
}
