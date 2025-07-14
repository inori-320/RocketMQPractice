package cn.ltynote.rocketmq.utils;

import cn.ltynote.rocketmq.model.vo.DataVO;
import cn.ltynote.rocketmq.model.vo.ResponseDataVO;

/**
 * @author litianyu
 * @version 1.0.0
 * @title ResponseUtil
 * @create 2025/7/14 10:09
 */
public class ResponseUtil {

    // 成功响应（无数据）
    public static ResponseDataVO<DataVO> success() {
        return new ResponseDataVO<>();
    }

    // 成功响应（带数据）
    public static <T extends DataVO> ResponseDataVO<T> success(T data) {
        return new ResponseDataVO<>(data);
    }

    // 业务失败响应
    public static ResponseDataVO<DataVO> fail(int code, String message) {
        ResponseDataVO<DataVO> responseDataVO = new ResponseDataVO<>();
        responseDataVO.setCode(String.valueOf(code));
        responseDataVO.setMsg(message);
        return responseDataVO;
    }
}
