package com.db.service;

import com.db.model.TransactionLog;
import com.db.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TransactionLogServiceImpl implements TransactionLogService {

    @Autowired
    private TransactionRepository transactionRepository;

    public void addTransactionLog(String requestBody) {

        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setRequestBody(requestBody);
        transactionLog.setCreateDate(new Date());

        transactionRepository.save(transactionLog);
    }
}
