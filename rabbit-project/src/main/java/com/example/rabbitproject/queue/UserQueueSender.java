package com.example.rabbitproject.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.example.rabbitproject.controller.dto.UserSendDTO;

@Component
public class UserQueueSender {

    private RabbitTemplate rabbitTemplate;
    private Queue queue;

    public UserQueueSender(RabbitTemplate rabbitTemplate, Queue queue) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }

    public void send(UserSendDTO userSendDTO) {
        rabbitTemplate.convertAndSend(this.queue.getName(), userSendDTO);
    }
}
