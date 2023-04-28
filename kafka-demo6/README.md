# Ack模式 - 单记录消费手动提交 offset （ack）

默认情况下，Kafka 会自动帮我们提交 offset，但是这样做容易导致消息重复消费或消失丢失：

在消费者收到消息之后，且 kafka 未自动提交 offset 之前，broker 宕机了，然后重启 broker，此时消费者会从原来的 offset 开始消费，于是出现了重复消费； 

在消费者收到消息之后，且消费者还没有处理完消息时，由于自动提交的间隔时间到了，于是 kafka 自动提交了 offset，但是之后消费者又挂掉了，那么当消费者重启之后，会从下一个 offset 开始消费，这样前面的消息就丢失了。 

我们可以改为使用手动提交 offset。

<br>


Spring Kafka消费消息的模式分为2种模式（对应spring.kafka.listener.type配置）：

* single - 每次消费单条记录
* batch - 批量消费消息列表


<br>


每种模式都分为2种提交已消费消息offset的ack模式：

* 自动确认
* 手动确认



<br>
<br>



## ack-mode模式

| AckMode模式        | 模式               | 作用                                                                                                          | 
|-------------------|--------------------|-------------------------------------------------------------------------------------------------------------|
| RECORD           | 单记录              | 当每一条记录被消费者监听器（ListenerConsumer）处理之后提交                                                                       |
| BATCH            | 批量（默认）           | 当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后提交                                                                |
| TIME             | 超时               | 当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后，距离上次提交时间大于TIME时提交(通过spring.kafka.listener.ack-time设置触发时间)        |
| COUNT            | 超过消费数量           | 当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后，被处理record数量大于等于COUNT时提交(通过spring.kafka.listener.ack-count设置触发数量) |
| COUNT_TIME       | 超时或超数量           | TIME或COUNT　有一个条件满足时提交                                                                                       |
| MANUAL           | 手动提交（ack）后同BATCH | 当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后, 手动调用Acknowledgment.acknowledge()后提交，需要手动使用 Acknowledgment参数提交   |
| MANUAL_IMMEDIATE | 手动立即提交           | 手动调用Acknowledgment.acknowledge()后立即提交，需要手动使用 Acknowledgment参数提交                                             |



<br>
<br>


# ack-mode设置

自动提交 ack-mode 模式包括：RECORD | BATCH | TIME | COUNT | COUNT_TIME ， 且使用相关自动模式不可在 @KafkaListener 标注方法中使用 Acknowledgment 参数。


手动提交 ack-mode 模式包括：MANUAL | MANUAL_IMMEDIATE， 且使用相关手动模式需在 @KafkaListener 标注方法中使用 Acknowledgment 参数。


<br>
<br>


## 关于消费者提交已消费消息offset的相关配置说明：

spring.kafka.consumer.enbable-auto-commit

- true 自动提交已消费消息offset

auto-commit-interval 设置自动提交间隔

- fasle 由程序控制已消费消息offset提交

spring.kafka.listener.ack-mode 已消费offset提交模式



<br>
<br>
<br>



> 注意

不能再配置中既配置 kafka.consumer.enable-auto-commit=true 自动提交，然后又在监听器中使用手动提交

