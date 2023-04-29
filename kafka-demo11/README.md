# 序列化/反序列化

## Kafka使用JSON序列化及反序列化，大致是两种方式。


* 使用 StringSerializer
* 使用 JsonSerializer


StringSerializer 方式比 JsonSerializer 方式更具有通用性。StringSerializer 方式可以接收任意字符串（包括 JSON 字符串）。但需要在业务代码中手动执行 JSON 序列化、反序列化的操作。

JsonSerializer 方式则减少了在业务侧手动执行 JSON 序列化、反序列化的操作，但需要在框架中指定要执行 JSON 序列化、反序列化的 Java 类。

综上，如果业务代码较少，则采用 StringSerializer；如果业务代码较多，则采用 JsonSerializer。