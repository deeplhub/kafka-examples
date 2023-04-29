# 多方法处理消息

组合使用 @KafkaListener 和 @KafkaHandler，能够让我们在传递消息时，根据转换后的消息有效负载类型来确定调用哪个方法。