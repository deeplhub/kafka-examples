# 获取消息回复

生产者在发送消息之后，可以同时等待获取消费者接收并处理消息之后的回复，就像传统的 RPC 交互那样，要实现这个功能，我们需要使用 ReplyingKafkaTemplate。

ReplyingKafkaTemplate 是 KafkaTemplate 的一个子类，它除了继承父类的方法，还新增了方法 sendAndReceive ，该方法实现了消息发送/回复的语义。

Spring Boot 没有提供开箱即用的 ReplyingKafkaTemplate，我们需要做些额外的配置。



