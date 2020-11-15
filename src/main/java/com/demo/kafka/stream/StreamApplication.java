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

	public static Logger logger = LoggerFactory.getLogger(StreamApplication.class);
	private final CountDownLatch latch = new CountDownLatch(3);

	public StreamApplication(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplateBuilder = restTemplateBuilder.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(StreamApplication.class, args);
	}

	@StreamListener(UserBinder.TARGET_IN)
	public void handler(User user) {
		System.out.println("Received : " + user.toString());
		User user1 =restTemplateBuilder.postForObject("http://localhost:8080/api/user", user, User.class);
		assert user1 != null;
		System.out.println(user1.toString());
	}
}
