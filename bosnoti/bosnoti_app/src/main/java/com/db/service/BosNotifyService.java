package com.db.service;

import com.db.model.BosNotificationEntity;

import java.util.List;

public interface BosNotifyService {
    List<BosNotificationEntity> getBosNotification(String accountNo, String subNo, String status, String funcCD, int startSeq, int rowNum);

    int updateBosNotification(BosNotificationEntity notificationEntity) ;
}
