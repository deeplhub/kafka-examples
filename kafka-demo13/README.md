# 异常处理

对于消费者在处理消息过程中抛出的异常，我们可以设置 errorHandler，然后在 errorHandler 中统一处理。


KafkaListenerErrorHandler 和 ErrorHandler 的区别：


- KafkaListenerErrorHandler 侦听器错误处理程序，当在带有 @KafkaListener 注释的方法中发生异常时，就会调用这个处理程序。

- ErrorHandler 容器错误处理程序，每当在容器级别引发异常时就会调用此处理程序。这将在处理错误（这只是一条日志消息）后提交偏移量（因为默认情况下 isAckAfterHandle() 返回 true）。


默认情况下没有侦听器错误处理程序，因此侦听器抛出的任何异常都会抛给容器。


## 自定义逻辑处理消费异常

支持自定义 ErrorHandler 或 BatchErrorHandler 实现类，实现对消费异常的自定义的逻辑


<br>
<br>
<br>

[Kafka - 异常处理](https://blog.csdn.net/qq_34561892/article/details/104055095)
