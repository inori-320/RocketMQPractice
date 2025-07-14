package cn.ltynote.rocketmq.model.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author litianyu
 * @version 1.0.0
 * @title MQMessagePO
 * @create 2025/7/14 13:52
 */
@Data
public class MQMessagePO {
    private Long id;
    private String messageId;
    private String topic;
    private String tag;
    // SUCCESS / FAIL
    private String status;
    private String errorReason;
    private LocalDateTime consumedAt;
}
