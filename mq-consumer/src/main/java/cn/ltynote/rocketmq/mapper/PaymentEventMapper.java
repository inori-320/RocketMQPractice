package cn.ltynote.rocketmq.mapper;

import cn.ltynote.rocketmq.model.po.PaymentPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author litianyu
 * @version 1.0.0
 * @title PaymentEventMapper
 * @create 2025/7/14 14:29
 */
@Mapper
@Repository
public interface PaymentEventMapper {

    int countByMessageId(String messageId);

    boolean savePaymentEvent(PaymentPO po);
}
