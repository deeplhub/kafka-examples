# 消息过滤器

## 原理

消息过滤器在消息抵达 consumer 之前被拦截，过滤器根据系统业务逻辑去筛选出需要的数据再交由 KafkaListener 处理。

配置消息过滤只需要为监听器工厂 配置一个 RecordFilterStrategy（消息过滤策略），返回true的时候消息将会被抛弃，返回false时，消息能正常抵达监听容器。

