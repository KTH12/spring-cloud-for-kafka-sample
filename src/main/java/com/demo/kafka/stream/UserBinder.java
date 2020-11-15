package com.demo.kafka.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface UserBinder {

    String TARGET_IN = "customTopic01";

    @Input(TARGET_IN)
    SubscribableChannel targetFilter();
}

