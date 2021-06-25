package com.example.springbootpostgresql;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@EnableRabbit
@SpringBootApplication
@EntityScan(basePackages = {
    "com.example.springbootpostgresql.domain"
})
@EnableJpaRepositories(basePackages = {
    "com.example.springbootpostgresql.repository"
})
public class SpringBootPostgresqlApplication {

    @Value("${queue.user.name}")
    private String queueName;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootPostgresqlApplication.class, args);
	}

    @Bean
    public Queue queue() {
        return new Queue(queueName, true);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
