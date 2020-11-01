package com.demo.kafka.stream;

import javafx.application.Application;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@RestController
@EnableBinding(Processor.class)
public class StreamApplication {
	@Autowired
	private MessageChannel output;

	public static Logger logger = LoggerFactory.getLogger(Application.class);
	private final CountDownLatch latch = new CountDownLatch(3);

	public static void main(String[] args) {
		SpringApplication.run(StreamApplication.class, args);
	}

	@PostMapping("/publish")
	public User publishEvent(@RequestBody User user) {
		output.send(MessageBuilder.withPayload(user).build());
		return user;
	}

	@StreamListener(Processor.INPUT)
	public void handler(String person) {
		System.out.println("Received : " + person);
	}
}
