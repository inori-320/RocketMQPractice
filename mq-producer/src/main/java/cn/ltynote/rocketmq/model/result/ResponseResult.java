package cn.ltynote.rocketmq.model.result;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author litianyu
 * @version 1.0.0
 * @title ResponseResult
 * @create 2025/7/11 15:47
 */
@Data
@Accessors(chain = true) // 启用链式调用
public class ResponseResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code;    // 状态码
    private String msg;  // 提示信息
    private T data;      // 响应数据
    private long timestamp = System.currentTimeMillis(); // 服务器时间戳

    // 构造器私有化
    private ResponseResult() {}
}