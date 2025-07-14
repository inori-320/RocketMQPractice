package cn.ltynote.rocketmq.model.vo;

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
public class ResponseDataVO<T> {
    // 状态码
    private String code;
    // 提示信息
    private String msg;
    // 响应数据
    private T data;
    // 服务器时间戳
    private long timestamp;

    public ResponseDataVO() {
        this.code = "200";
        this.msg = "成功";
        this.timestamp = System.currentTimeMillis();
    }

    public ResponseDataVO(T rtnData) {
        this.code = "200";
        this.msg = "成功";
        this.data = rtnData;
        this.timestamp = System.currentTimeMillis();
    }
}