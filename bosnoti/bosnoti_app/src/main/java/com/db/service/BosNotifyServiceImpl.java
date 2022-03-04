package com.db.service;

import com.db.model.BosNotificationEntity;
import com.db.repository.BosNotifyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BosNotifyServiceImpl implements BosNotifyService {
    private static final Logger logger = LoggerFactory.getLogger(BosNotifyServiceImpl.class);

    @Autowired
    private BosNotifyRepository elementObjRepository;

    @Override
    public List<BosNotificationEntity> getBosNotification(String accountNo, String subNo, String status, String funcCD, int startSeq, int rowNum) {
        try {
            return elementObjRepository.getBosNotification(accountNo, subNo, status, funcCD, startSeq, rowNum);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }



    @Override
    public int updateBosNotification(BosNotificationEntity notificationEntity) {
        try {
            return elementObjRepository.updateBosNotification(notificationEntity);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return -1;
        }
    }
}
