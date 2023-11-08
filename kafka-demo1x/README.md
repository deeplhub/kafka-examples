spring-kafka 2.8.2使用


注解属性说明：

* attempts：重试次数，默认为3。
* @Backoff delay：消费延迟时间，单位为毫秒。
* @Backoff multiplier：延迟时间系数，此例中 attempts = 4， delay = 5000， multiplier = 2 ，则间隔时间依次为5s、10s、20s、40s，最大延迟时间受 maxDelay 限制。
* fixedDelayTopicStrategy：可选策略包括：SINGLE_TOPIC 、MULTIPLE_TOPICS