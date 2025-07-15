package cn.ltynote.rocketmq.service.impl;

import cn.ltynote.rocketmq.model.dto.PaymentDTO;
import cn.ltynote.rocketmq.model.dto.VehicleDTO;
import cn.ltynote.rocketmq.model.message.OrderMessage;
import cn.ltynote.rocketmq.model.message.VehicleMessage;
import cn.ltynote.rocketmq.model.vo.DataVO;
import cn.ltynote.rocketmq.service.EventHandlingService;
import cn.ltynote.rocketmq.service.RocketMQProducerService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author litianyu
 * @version 1.0.0
 * @create 2025/7/11 15:38
 */
@Service
public class EventHandlingServiceImpl implements EventHandlingService {
    private static final BigDecimal MAXIMUM_FEE = new BigDecimal("150.00");
    private static final BigDecimal SMALL_MAXIMUM_FEE = new BigDecimal("100.00");
    private static final BigDecimal MEDIUM_MAXIMUM_FEE = new BigDecimal("150.00");
    private static final BigDecimal LARGE_MAXIMUM_FEE = new BigDecimal("200.00");

    private static final Map<String, BigDecimal> RATE_TABLE = Map.of(
            "small", new BigDecimal("5.00"),
            "medium", new BigDecimal("8.00"),
            "large", new BigDecimal("12.00")
    );

    @Autowired
    private RocketMQProducerService rocketMQProducerService;

    private VehicleMessage dtoToMessage(VehicleDTO dto, VehicleMessage.EventType eventType) {
        VehicleMessage message = new VehicleMessage();
        message.setEventType(eventType);
        message.setTime(dto.getTime());
        message.setGateId(dto.getGateId());
        message.setPlateNumber(dto.getPlateNumber());
        message.setCarType(dto.getCarType());
        message.setMessageId(dto.getMessageId());
        return message;
    }

    private BigDecimal calculateParkingFee(int durationMinutes, String carType) {
        // 获取基础费率
        BigDecimal hourlyRate = RATE_TABLE.getOrDefault(carType, BigDecimal.ZERO);
        // 计算费用
        BigDecimal fee = hourlyRate.multiply(
                new BigDecimal(durationMinutes).divide(BigDecimal.valueOf(60), 2, RoundingMode.UP)
        );
        // 根据车型设置封顶金额
        BigDecimal maxFee;
        switch (carType) {
            case "small":
                maxFee = SMALL_MAXIMUM_FEE;
                break;
            case "medium":
                maxFee = MEDIUM_MAXIMUM_FEE;
                break;
            case "large":
                maxFee = LARGE_MAXIMUM_FEE;
                break;
            default:
                maxFee = MAXIMUM_FEE;
        }
        return fee.min(maxFee);
    }

    @Override
    public DataVO entry(VehicleDTO dto) {
        VehicleMessage message = dtoToMessage(dto, VehicleMessage.EventType.ENTRY);
        rocketMQProducerService.publishEntryEvent(message);
        return null;
    }

    @Override
    public DataVO exit(VehicleDTO dto) {
        VehicleMessage message = dtoToMessage(dto, VehicleMessage.EventType.EXIT);
        rocketMQProducerService.publishExitEvent(message);
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataVO payment(PaymentDTO dto) {
        OrderMessage message = new OrderMessage();
        message.setMessageId(dto.getMessageId());
        message.setPlateNumber(dto.getPlateNumber());
        message.setOrderId(dto.getOrderId());
        message.setPaymentType(dto.getPaymentType());
        message.setDurationMinutes(dto.getDurationMinutes());
        message.setStatus(OrderMessage.OrderStatus.CREATED);
        message.setPaymentTime(LocalDateTime.now());
        BigDecimal amount = calculateParkingFee(dto.getDurationMinutes(), dto.getCarType());
        message.setAmount(amount);
        boolean status = rocketMQProducerService.publishPaymentEvent(message);
        if(status) {
            message.setStatus(OrderMessage.OrderStatus.PAID);
        }
        DataVO dataVO = new DataVO();
        dataVO.setAmount(amount);
        return dataVO;
    }
}
