package cn.ltynote.rocketmq.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author litianyu
 * @version 1.0.0
 * @title VehicleDTO
 * @create 2025/7/11 15:41
 */
@Data
public class VehicleDTO {
    @NotBlank
    private String messageId;

    @Pattern(regexp = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4,5}[A-Z0-9挂学警港澳]{1}$")
    private String plateNumber;

    @NotBlank
    private String gateId;

    @NotBlank
    private String carType;

    private LocalDateTime Time;
}
