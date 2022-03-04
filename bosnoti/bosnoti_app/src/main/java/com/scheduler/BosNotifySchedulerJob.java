package com.scheduler;

import com.service.BOSNotifySchedulerTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;


public class BosNotifySchedulerJob extends QuartzJobBean {

	private static final Logger logger = LogManager.getLogger(BosNotifySchedulerJob.class);
	private BOSNotifySchedulerTask bosNotifySchedulerTask;

	public void setBosNotifySchedulerTask(BOSNotifySchedulerTask bosNotifySchedulerTask) {
		this.bosNotifySchedulerTask = bosNotifySchedulerTask;
	}

	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			bosNotifySchedulerTask.notifyProcess();
		} catch (Exception e) {
			logger.error(BosNotifySchedulerJob.class.getSimpleName()+ " Job Exception --->" + e.getMessage());
		}
	}
}
