package cn.ltynote.rocketmq.service;

import cn.ltynote.rocketmq.model.dto.PaymentDTO;
import cn.ltynote.rocketmq.model.dto.VehicleDTO;
import cn.ltynote.rocketmq.model.message.OrderMessage;
import cn.ltynote.rocketmq.model.message.VehicleMessage;
import cn.ltynote.rocketmq.model.vo.DataVO;

/**
 * @author litianyu
 * @version 1.0.0
 * @create 2025/7/11 15:38
 */
public interface EventHandlingService {
    DataVO entry(VehicleDTO dto);

    DataVO exit(VehicleDTO dto);

    DataVO payment(PaymentDTO dto);
}
