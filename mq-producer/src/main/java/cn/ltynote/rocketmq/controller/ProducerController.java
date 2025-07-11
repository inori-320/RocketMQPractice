package cn.ltynote.rocketmq.controller;

import cn.ltynote.rocketmq.model.dto.VehicleDTO;
import cn.ltynote.rocketmq.model.message.VehicleMessage;
import cn.ltynote.rocketmq.model.result.ResponseResult;
import cn.ltynote.rocketmq.service.EventHandlingService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

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
    public ResponseResult handleVehicleEntry(@RequestBody @Valid VehicleDTO dto) {

        return null;
    }
}
