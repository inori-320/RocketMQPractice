package cn.ltynote.rocketmq.controller;

import cn.ltynote.rocketmq.model.dto.PaymentDTO;
import cn.ltynote.rocketmq.model.dto.VehicleDTO;
import cn.ltynote.rocketmq.model.vo.DataVO;
import cn.ltynote.rocketmq.model.vo.ResponseDataVO;
import cn.ltynote.rocketmq.service.EventHandlingService;
import cn.ltynote.rocketmq.utils.ResponseUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author litianyu
 * @version 1.0.0
 * @title ProducerController
 * @create 2025/7/11 14:44
 */

@Slf4j
@RestController
@RequestMapping("/producer")
public class ProducerController {
    @Autowired
    private EventHandlingService eventHandlingService;

    @PostMapping("/entry")
    public ResponseDataVO<DataVO> handleVehicleEntry(@RequestBody VehicleDTO dto) {
        try {
            return ResponseUtil.success(eventHandlingService.entry(dto));
        } catch (Exception e){
            return ResponseUtil.fail(500, e.getMessage());
        }

    }

    @PostMapping("/exit")
    public ResponseDataVO<DataVO> handleVehicleExit(@RequestBody VehicleDTO dto) {
        try {
            return ResponseUtil.success(eventHandlingService.exit(dto));
        } catch (Exception e){
            return ResponseUtil.fail(500, e.getMessage());
        }
    }

    @PostMapping("/payment")
    public ResponseDataVO<DataVO> handlePayment(@RequestBody PaymentDTO dto) {
        try {
            return ResponseUtil.success(eventHandlingService.payment(dto));
        } catch (Exception e){
            return ResponseUtil.fail(500, e.getMessage());
        }
    }
}
