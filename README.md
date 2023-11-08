<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">Kafka 学习用例</h1>
<p align="center">
	<a href="#"><img src="https://img.shields.io/badge/Springboot-2.3.12-blue"></a>
	<a href="#"><img src="https://img.shields.io/badge/license%20-MIT-green"></a>

## 项目

| #   | 目录                             | 说明                     |
|-----|--------------------------------|------------------------|
| 1   | [kafka-demo1](./kafka-demo1)   | 原生用例                   |
| 2   | [kafka-demo2](./kafka-demo2)   | 简单发送/接收消息              |
| 3   | [kafka-demo3](./kafka-demo3)   | 发送消息时获取发送结果(同步/异步)     |
| 4   | [kafka-demo4](./kafka-demo4)   | 发送事务消息                 |
| 5   | [kafka-demo5](./kafka-demo5)   | 单记录消费自动提交 offset       |
| 6   | [kafka-demo6](./kafka-demo6)   | 单记录消费手动提交 offset （ack） |
| 7   | [kafka-demo7](./kafka-demo7)   | 批量消费自动提交 offset （ack）  |
| 8   | [kafka-demo8](./kafka-demo8)   | 批量消费手动提交 offset （ack）  |
| 9   | [kafka-demo9](./kafka-demo9)   | 转发消息                   |
| 10  | [kafka-demo10](./kafka-demo10) | 获取消息回复                 |
| 11  | [kafka-demo11](./kafka-demo11) | 序列化/反序列化               |
| 12  | [kafka-demo12](./kafka-demo12) | 多方法处理消息                |
| 13  | [kafka-demo13](./kafka-demo13) | 异常处理                   |
| 14  | [kafka-demo14](./kafka-demo14) | 消息重试与死信队列(@Bean)       |
| 15  | [kafka-demo15](./kafka-demo15) | 消息重试与死信队列（注解）          |
| 16  | [kafka-demo16](./kafka-demo16) | 消息过滤器                  |
| 17  | [kafka-demo17](./kafka-demo17) | 定时启动/停止监听器             |
| 16  | 待完成                            | 二次封装                   |
| 16  | 待完成                            | stream                 |
| 16  | 待完成                            | 架构封装                   |
| 16  | 待完成                            | 定时/延时消息                |

<br>
<br>
<br>

## 生产者如何提高吞吐量

增加分区

```yaml
# 批次大小，默认 16K
batch-size: 16384
# 等待时间，默认 0
linger.ms: 5
# 缓冲区大小，默认 32M
buffer-memory: 33554432
# 压缩，默认 none，可配置值 gzip、snappy、lz4 和 zstd 
compression-type: "snappy"
```

<br>
<br>

## 生产者数据可靠

数据完全可靠条件 = ACK级别设置为-1 + 分区副本大于等于2 + ISR里应答的最小副本数量大于等于2

幂等性（参数 enable.idempotence 默认为 true）、事务


<br>
<br>

## 消费者如何提高吞吐量

增加分区消费，消费者数 = 分区数。同一个消费组下一个分区只能由一个消费者消费

提高每批次拉取的数量，批次拉取数据过少（拉取数据/处理时间 < 生产速度），使处理的数据小于生产的数据，也会造成数据积压。



<br>
<br>

## 重复消费和漏消费

如果想完成Consumer端的精准一次性消费，那么需要Kafka消费端将消费过程和提交offset（手动提交）过程做原子绑定。此时我们需要将Kafka的offset保存到支持事务的自定义介质（比如MySQL）

## 参考地址：

* [kafka单节点的安装，部署，使用](https://www.cnblogs.com/biehongli/p/10216309.html)
* [kafka 单机部署](https://www.jianshu.com/p/a743712beda5)
* [Kafka单机版部署](https://www.cnblogs.com/szx666/p/14635910.html)
* [linux系统下kafka单机部署](https://www.modb.pro/db/185916)
* [Kafka单机版安装](https://developer.aliyun.com/article/1164146)
* [Kafka发送消息和消费消息的方式](https://blog.csdn.net/qq_37958845/article/details/105675131)
* [Kafka重试机制](https://www.51cto.com/article/722619.html)
* [Spring Kafka：Retry Topic、DLT 的使用与原理](https://zhuanlan.zhihu.com/p/554967177)
* [Spring-retry 使用指南](https://bbs.huaweicloud.com/blogs/360085)
* [kafka 安装使用 /springboot整合kafka /消息投递机制以及存储策略 /副本处理机制](https://blog.csdn.net/qq_41463655/article/details/125180597)
* [Spring Kafka Retry Topic DLT 的理解](https://juejin.cn/post/7208771469928038459)
* [spring kafka简介及使用参考（四）](https://shiker.tech/archives/39#4.2.-%E9%9D%9E%E9%98%BB%E5%A1%9E%E9%87%8D%E8%AF%95)
* [Kafka生产者原理与流程_带实操代码](https://blog.csdn.net/manformer/article/details/129963130)
* [Spring Boot 集成 Kafka](https://codearea.cc/post/Spring%20Boot%20%E9%9B%86%E6%88%90%20Kafka)