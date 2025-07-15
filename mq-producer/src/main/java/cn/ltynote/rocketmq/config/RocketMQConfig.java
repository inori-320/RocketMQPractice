package cn.ltynote.rocketmq.config;

import cn.ltynote.rocketmq.listener.PaymentTransactionListener;
import jakarta.annotation.PostConstruct;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author litianyu
 * @version 1.0.0
 * @title RocketMQConfig
 * @create 2025/7/15 11:26
 */
@Configuration
public class RocketMQConfig {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private PaymentTransactionListener transactionListener;

    // TODO 初始化事务监听器
}

