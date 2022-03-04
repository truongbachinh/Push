package com.db.repository;

import com.db.model.BosNotificationEntity;
import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.SqlResultSetMapping;
import java.util.List;

public interface BosNotifyRepository{
    List<BosNotificationEntity> getBosNotification(String accountNo, String subNo, String status, String funcCD, int startSeq, int rowNum);

    int updateBosNotification(BosNotificationEntity notificationEntity);
}
