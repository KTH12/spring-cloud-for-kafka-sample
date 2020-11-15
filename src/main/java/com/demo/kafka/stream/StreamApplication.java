package com.demo.kafka.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@RestController
@EnableBinding(value = {UserBinder.class})
public class StreamApplication {

	final RestTemplate restTemplateBuilder;
	private final MessageChannel output;

	public static Logger logger = LoggerFactory.getLogger(StreamApplication.class);
	private final CountDownLatch latch = new CountDownLatch(3);

	public StreamApplication(@Qualifier(UserBinder.TARGET_OUT) MessageChannel output, RestTemplateBuilder restTemplateBuilder) {
		this.output = output;
		this.restTemplateBuilder = restTemplateBuilder.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(StreamApplication.class, args);
	}

	@PostMapping("/publish")
	public User publishEvent(@RequestBody User user) {
		System.out.println("Publish : " + user.toString());
		output.send(MessageBuilder.withPayload(user).build());
		return user;
	}
}
