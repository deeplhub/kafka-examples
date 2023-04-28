发送事务消息



默认情况下，Spring-kafka自动生成的KafkaTemplate实例是不具有事务消息发送能力的。

需要使用如下配置激活事务特性：

```
spring.kafka.producer.transaction-id-prefix=kafka_tx.
```

事务激活后，所有的消息发送只能在发生事务的方法内执行了，不然就会抛一个没有事务交易的异常。
