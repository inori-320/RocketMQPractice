# 停车事件处理系统后端

基于Spring Boot + RocketMQ的停车事件处理系统，模拟了停车场的入场、出场与支付事件的异步解耦处理流程。通过生产者-消费者模型，将业务处理逻辑与事件触发解耦。

## 技术栈

| 层级       | 技术选型                    |
| ---------- | --------------------------- |
| 基础框架   | Spring Boot 3.4.0           |
| 消息中间件 | RocketMQ 5.1.4              |
| 持久化层   | MyBatis-Plus + MySQL 8.0.33 |
| 连接池     | HikariCP                    |
| 工具       | Lombok、SLF4J               |

## 运行前提环境

在运行本项目之前，请确保以下组件正确安装并已启动：

| 组件     | 要求说明                              |
| -------- | ------------------------------------- |
| MySQL    | 推荐 8.x，创建名为parking的数据库     |
| RocketMQ | 推荐 5.x，包含 namesrv 和 broker 服务 |
| Java     | JDK 17 或以上                         |
| Maven    | 用于项目构建                          |

## 亮点

- **事件驱动架构**：生产者将入场、出场、支付等事件发送至不同的Topic，消费者异步接收处理
- **Topic+Tag 分发机制**：使用 Topic + Tag 精准路由，入场/出场统一归类为 `VEHICLE_TOPIC`，支付事件为 `PAYMENT_TOPIC`
- **幂等校验机制**：基于 `messageId` 实现入场/支付消息的幂等处理，防止重复入库
- **事务控制（TODO）**：消费者处理关键业务时采用事务控制，确保数据一致性

## 消息流程

### 生产者发送事件

接口由用户系统触发：

| 事件类型  | 请求路径                        | 描述                       |
| --------- | ------------------------------- | -------------------------- |
| 入场/出场 | `POST /producer/entry`、`/exit` | 统一发送至 `VEHICLE_TOPIC` |
| 支付      | `POST /producer/payment`        | 发送至 `PAYMENT_TOPIC`     |

### 消费者接收并处理

消费者模块监听：

- `VEHICLE_TOPIC`：统一处理入场/出场事件
- `PAYMENT_TOPIC`：处理支付相关逻辑并入库

消费成功后打印日志

## 数据库表设计

```sql
CREATE TABLE vehicle_event (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    message_id VARCHAR(64) NOT NULL UNIQUE COMMENT '消息ID',
    plate_number VARCHAR(20) NOT NULL COMMENT '车牌号',
    car_type VARCHAR(10) NOT NULL COMMENT '车辆类型 small/medium/large',
    gate_id VARCHAR(32) NOT NULL COMMENT '闸机ID',
    event_type VARCHAR(10) NOT NULL COMMENT '事件类型 ENTRY/EXIT',
    event_time DATETIME NOT NULL COMMENT '事件时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- message_id 唯一索引，防止重复消费
CREATE UNIQUE INDEX idx_vehicle_message_id ON vehicle_event(message_id);

-- plate_number 普通索引，用于查询某车进出记录
CREATE INDEX idx_vehicle_plate ON vehicle_event(plate_number);

-- event_time 时间范围检索
CREATE INDEX idx_vehicle_time ON vehicle_event(event_time);

CREATE TABLE payment_event (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    message_id VARCHAR(64) NOT NULL UNIQUE COMMENT '消息ID',
    order_id VARCHAR(64) NOT NULL COMMENT '订单ID',
    plate_number VARCHAR(20) NOT NULL COMMENT '车牌号',
    payment_type VARCHAR(20) NOT NULL COMMENT '支付方式，如Alipay/WeChat',
    duration_minutes INT NOT NULL COMMENT '停放时间（分钟）',
    amount DECIMAL(10,2) NOT NULL COMMENT '收费金额',
    status VARCHAR(10) NOT NULL COMMENT '订单状态 CREATED/PAID',
    payment_time DATETIME NOT NULL COMMENT '支付时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- message_id 唯一索引，防止重复消息处理
CREATE UNIQUE INDEX idx_payment_message_id ON payment_event(message_id);

-- order_id 唯一索引，用于查订单详情
CREATE UNIQUE INDEX idx_payment_order_id ON payment_event(order_id);

-- plate_number + payment_time 联合索引，适合查某车近期支付记录
CREATE INDEX idx_payment_plate_time ON payment_event(plate_number, payment_time);

CREATE TABLE fail_event (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    order_id VARCHAR(64) DEFAULT NULL COMMENT '订单ID',
    plate_number VARCHAR(20) NOT NULL COMMENT '车牌号',
    message_id VARCHAR(64) NOT NULL COMMENT '消息ID',
    gate_id VARCHAR(32) DEFAULT NULL COMMENT '闸机ID',
    event_type VARCHAR(10) DEFAULT NULL COMMENT '事件类型',
    event_time DATETIME DEFAULT NULL COMMENT '事件时间',
    fail_reason VARCHAR(255) NOT NULL COMMENT '失败原因',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    amount DECIMAL(10,2) DEFAULT NULL COMMENT '金额',
    duration_minutes INT DEFAULT NULL COMMENT '停放时间（分钟）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- message_id 索引，排查失败消息
CREATE INDEX idx_fail_message_id ON fail_event(message_id);

-- order_id 和 plate_number 联合索引，用于定位失败订单
CREATE INDEX idx_fail_order_plate ON fail_event(order_id, plate_number);

-- fail_reason 可选：如果你需要按失败类型分组统计
CREATE INDEX idx_fail_reason ON fail_event(fail_reason);
```

## 配置说明

`application.yml` 示例：

```
spring:
  datasource:
    url: jdbc:mysql://服务器IP:3306/parking
    username: user
    password: 123456
  rocketmq:
    name-server: 服务器IP:9876
    producer:
      group: producer_group
```

## 测试说明

你可以使用 Postman 或 CURL 发送以下测试数据：

```json
POST /producer/entry
{
  "messageId": "msg-001",
  "plateNumber": "沪A12345",
  "carType": "small",
  "gateId": "gate-01",
  "time": "2025-07-15T10:00:00"
}

POST /producer/payment
{
  "orderId": "order-001",
  "messageId": "msg-002",
  "plateNumber": "沪A12345",
  "carType": "small",
  "gateId": "gate-01",
  "paymentType": "WeChat",
  "durationMinutes": 120
}
```

## TODO

-  使用 RocketMQ 的事务消息机制（带 TransactionListener）
-  支持支付回调后的状态确认机制
-  加入 WebSocket / Kafka / Redis 进行进一步系统解耦
-  完善消费者幂等策略

