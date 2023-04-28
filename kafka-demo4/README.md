# 发送事务消息



默认情况下，Spring-kafka自动生成的 KafkaTemplate 实例是不具有事务消息发送能力的。

需要使用如下配置激活事务特性：

```
spring.kafka.producer.transaction-id-prefix=tx.
```

当我们在生产者方配置了属性 transaction-id-prefix 后，Spring 会自动帮我们开启事务。所有的消息发送只能在发生事务的方法内执行了，不然就会抛一个没有事务交易的异常。

不过开启事务之后，retries 属性需要设置为大于 0，acks 属性需要设置为 all 或 -1。

另外，我们还需要将消费者方的 isolation.level 设置为 read_committed，这样对于未提交事务的消息，消费者就不会读取到。

<br>
<br>
<br>


## 使用事务

Spring 提供了两种方式使用事务：

* 调用 KafkaTemplate 的 executeInTransaction 方法
* 使用 @Transactional 事务注解

<br>

需要注意的是，在生产者开启事务之后，所有发送消息的地方都必须放在事务中执行。