package com.service;


import com.db.model.BosNotificationEntity;
import com.db.service.BosNotifyService;
import com.kafka.KafkaProducerService;
import com.util.Constant;
import com.util.FileHelper;
import com.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class BOSNotifySchedulerTask {
	private static final Logger logger = LoggerFactory.getLogger(BOSNotifySchedulerTask.class);

	@Autowired
	Environment env;

	@Autowired
	private BosNotifyService bosNotifyService;

	@Autowired
	private KafkaProducerService kafkaProducerService;


	public void notifyProcess() {
		logger.info("BOS Notification is executing (retrieve message from BOS DB and push to KAFKA topic)");

		String lastSeqFile = env.getProperty("bos.notify.file.last_seq");
		String lastSeqNo = getBOSNotifyLastSeq(lastSeqFile, 0);

		int notifyStartSeq = StringHelper.stringToInt(lastSeqNo, 0);
		int rowNumber = StringHelper.stringToInt(env.getProperty("bos.notify.max.row.number"), 100);

		String kafkaTopic = env.getProperty("akp.topic");

		List<BosNotificationEntity> bosNotificationList = bosNotifyService
				.getBosNotification
				(
				"039C021377",
				"00",
				"K",
				"F09101", notifyStartSeq,
				rowNumber);
		if (bosNotificationList != null) {
			logger.info("BOS notification count: " + bosNotificationList.size());
			try {
				for (BosNotificationEntity bosNotify : bosNotificationList) {

					String message = buildNotify(bosNotify);
					System.out.println("kafkaTopic ->" + kafkaTopic);
					System.out.println("message ->" + message);
					System.out.println("bosNotify ->" + bosNotify);
					kafkaProducerService.sendMessage(kafkaTopic, message, bosNotify);

					notifyStartSeq++;
					logger.info("BOS notification notifyStartSeq: " + notifyStartSeq);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}

			saveLastSeqToFile(lastSeqFile, notifyStartSeq);
		}
	}


	private  String buildNotify(BosNotificationEntity bosNotify) {
		StringBuilder resultStringBuilder = new StringBuilder();

		String akifMsgType = env.getProperty("bos.notify.akif.msg.type");
		String notifyType = env.getProperty("bos.notify.akif.notify.type");
		String notifySender = env.getProperty("bos.notify.akif.notify.sender");
		String notifyToUserType = env.getProperty("bos.notify.akif.notify.send.user");
		String notifyToTopicType = env.getProperty("bos.notify.akif.notify.send.topic");

		/*int[][] psNotify = {
		 1 : "Message Type": {0, 5}                         default: "PS002"
		 2 : "Transaction Unique Number":, {5, 12}         default: ""
		 3 : "Title":, {17, 100}
		 4 : "Body", {117, 200}
		 5 : "Click Action":, {317, 100}                   default: ""
		 6 : "Administrator ID":, {417, 15}
		 7 : "Notification Type":, {432, 1}                default: "F" firebase
		 8 : "Target Type":, {433, 1}                      default: ""
		 9 : "Count":, {434, 4}                            default: "1"
		 10: "Target":, {438}
		 };
		*/
		int[] psNotifyFields = {5, 12, 100, 200, 100, 15, 1, 1, 4, 15};
		String[] psNotifyFieldData = new String[10];

		int idxField = 0;
		psNotifyFieldData[idxField++] = akifMsgType;
		psNotifyFieldData[idxField++] = bosNotify.getSeqNo();
		psNotifyFieldData[idxField++] = bosNotify.getNotifyTitle();
		psNotifyFieldData[idxField++] = bosNotify.getNotifyMsg();
		psNotifyFieldData[idxField++] = "";
		psNotifyFieldData[idxField++] = notifySender;
		psNotifyFieldData[idxField++] = notifyType;
		psNotifyFieldData[idxField++] = notifyToUserType;
		psNotifyFieldData[idxField++] = "0001";
		psNotifyFieldData[idxField++] = bosNotify.getAccountNo();

		for(int i = 0; i < psNotifyFields.length;i++) {
			buildNotifyField(resultStringBuilder, psNotifyFields[i], psNotifyFieldData[i]);
		}

		return resultStringBuilder.toString();
	}

	private static void buildNotifyField(StringBuilder resultStringBuilder, int fieldLen, String fValue) {
		String originStr = fValue == null ? "" : fValue;

		byte[] bytes = originStr.getBytes(StandardCharsets.UTF_8);
		//String utf8EncodedStr = new String(bytes, StandardCharsets.UTF_8);

		int uft8Len = bytes.length;
		if (fieldLen > 0) {
			originStr = String.format("%" + (-(fieldLen - (uft8Len - originStr.length()))) + "s", originStr);
		}

		resultStringBuilder.append(originStr);
	}


	private String getBOSNotifyLastSeq(String lastSeqFile, int row) {
		try {
			FileInputStream inputStream = new FileInputStream(new File(lastSeqFile));

			List<String> allLine = FileHelper.readFromInputStream(inputStream);

			if(allLine.size()>0)
				return allLine.get(row);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	private void saveLastSeqToFile(String lastSeqFile, int lastSeq) {
		try {
			FileOutputStream outputStream = new FileOutputStream(new File(lastSeqFile));

			String content = String.valueOf(lastSeq);
			FileHelper.writeStringToFile(outputStream, content);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}
