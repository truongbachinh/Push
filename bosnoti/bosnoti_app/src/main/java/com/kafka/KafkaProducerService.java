package com.kafka;

import com.db.model.BosNotificationEntity;

import java.util.List;

public interface KafkaProducerService {
    public void sendMessage(String topicName, String message, BosNotificationEntity notificationEntity);
}
