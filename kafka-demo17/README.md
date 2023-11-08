# 定时启动/停止监听器

默认情况下，当消费者项目启动的时候，监听器就开始工作，监听消费发送到指定topic的消息，那如果我们不想让监听器立即工作，想让它在我们指定的时间点开始工作，或者在我们指定的时间点停止工作，该怎么处理呢——使用KafkaListenerEndpointRegistry，下面我们就来实现：

1. 禁止监听器自启动；

2. 创建两个定时任务，一个用来在指定时间点启动定时器，另一个在指定时间点停止定时器；

新建一个定时任务类，用注解@EnableScheduling声明，KafkaListenerEndpointRegistry 在SpringIO中已经被注册为Bean，直接注入，设置禁止KafkaListener自启动。


**参考**
* [KafkaListener手动启动和停止](https://www.cnblogs.com/caoweixiong/p/11181386.html)
* [@KafkaListener 详解及消息消费启停控制](https://blog.csdn.net/justlpf/article/details/129091732)
* [SpringBoot-Kafka（生产者事务、手动提交offset、定时消费、消息转发、过滤消息内容、自定义分区器、提高吞吐量）](https://blog.51cto.com/u_14452299/6019881)
* [SpringBoot集成kafka全面实战](https://blog.csdn.net/yanluandai1985/article/details/122200826)