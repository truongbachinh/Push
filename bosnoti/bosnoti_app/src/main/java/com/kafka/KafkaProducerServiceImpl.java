package com.kafka;

import com.db.model.BosNotificationEntity;
import com.db.service.BosNotifyService;
import com.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerServiceImpl.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private BosNotifyService bosNotifyService;


    @Override
    public void sendMessage(String topicName, String message, BosNotificationEntity notificationEntity) {

        notificationEntity.setWorkMN(Constant.NOTICE_BOS_WORK_PROCESS_NAME);

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);
        //kafkaTemplate.flush();

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");

                notificationEntity.setStatus(Constant.NOTICE_BOS_STATUS_PROCESS);
                notificationEntity.setSentErrCD(Constant.NOTICE_BOS_ERROR_CODE_SUCCESS);
                notificationEntity.setSentErrMsg("");

                bosNotifyService.updateBosNotification(notificationEntity);
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error("Unable to send message=[" + message + "] due to : " + ex.getMessage());

                notificationEntity.setStatus(Constant.NOTICE_BOS_STATUS_FAILURE);
                notificationEntity.setSentErrCD(Constant.NOTICE_BOS_ERROR_CODE_FAILED_100);
                notificationEntity.setSentErrMsg(ex.getMessage());

                bosNotifyService.updateBosNotification(notificationEntity);

            }
        });

    }
}
