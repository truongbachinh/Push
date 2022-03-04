package com.app;


import com.db.model.BosNotificationEntity;
import com.db.service.BosNotifyService;
import com.util.Constant;
import com.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class NotificationController {
    private static Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    private BosNotifyService bosNotifyService;

    @PostMapping("/notice-status-update")
    public ResponseEntity<Map<String,String>> noticeUpdateStatus(@RequestParam(name = "noticeSeq") String noticeSeq,
                                                                 @RequestParam(name = "status") String status,
                                                                 @RequestParam(name = "description") String description) {
        Map<String,String> response = new HashMap<>();
        int result = 0;
        logger.info(String.format("noticeSeq: %s, status: %s", noticeSeq, status));

        if (!StringHelper.isNullOrEmpty(noticeSeq) && !StringHelper.isNullOrEmpty(status)) {

            BosNotificationEntity notificationEntity = new BosNotificationEntity();
            notificationEntity.setSeqNo(noticeSeq);
            notificationEntity.setWorkMN(Constant.NOTICE_BOS_WORK_PROCESS_NAME);

            if (Constant.NOTICE_BOS_STATUS_SUCCESS.equals(status)) {
                notificationEntity.setStatus(Constant.NOTICE_BOS_STATUS_SUCCESS);
                notificationEntity.setSentErrCD(Constant.NOTICE_BOS_ERROR_CODE_SUCCESS);
                notificationEntity.setSentErrMsg(description);
            } else {
                notificationEntity.setStatus(Constant.NOTICE_BOS_STATUS_PENDING);
                notificationEntity.setSentErrCD(Constant.NOTICE_BOS_ERROR_CODE_FAILED_200);
                notificationEntity.getSentErrCD();
                notificationEntity.setSentErrMsg(description);
            }
             result = bosNotifyService.updateBosNotification(notificationEntity);
        }
        if (result > 0) {
            response.put("Ok","Notification Update status success Notice_Seq [" + noticeSeq + "]");
            return ResponseEntity.accepted().body(response);
        } else {
            response.put("Error", "Can't update status of Notice_Seq [" + noticeSeq + "]");
            return ResponseEntity.badRequest().body(response);

        }
    }

    @GetMapping("/getListNotify")
    public String sayHello() {
        BosNotificationEntity notificationEntity = new BosNotificationEntity();
        return String.format(notificationEntity.toString());
    }

}
