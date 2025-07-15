package cn.ltynote.rocketmq.mapper;

import cn.ltynote.rocketmq.model.po.FailEventPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author litianyu
 * @version 1.0.0
 * @title FailEventMapper
 * @create 2025/7/14 14:37
 */
@Mapper
public interface FailEventMapper {
    void saveFailEvent(FailEventPO failEventPO);
}
