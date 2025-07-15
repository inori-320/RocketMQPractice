package cn.ltynote.rocketmq.mapper;

import cn.ltynote.rocketmq.model.po.FailEventPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author litianyu
 * @version 1.0.0
 * @title FailEventMapper
 * @create 2025/7/14 14:37
 */
@Mapper
@Repository
public interface FailEventMapper {
    void saveFailEvent(FailEventPO failEventPO);
}
