# 简单发送/接收消息

监听多个消息


## @KafkaListener的使用

示例中简单演示了 @KafkaListener 接收消息的能力，但是 @KafkaListener 的功能不止如此，其他的比较常见的，使用场景比较多的功能点如下：

* 显示的指定消费哪些Topic和分区的消息，
* 设置每个Topic以及分区初始化的偏移量，
* 设置消费线程并发度
* 设置消息异常处理器