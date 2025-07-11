package cn.ltynote.rocketmq.core;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 71947
 * @version 1.0.0
 * @title BaseMessage
 * @create 2025/7/11 14:47
 */

@Data
public class BaseMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    // 消息唯一标识
    private String messageId;
    // 发送时间
    private Date sendTime;
}
