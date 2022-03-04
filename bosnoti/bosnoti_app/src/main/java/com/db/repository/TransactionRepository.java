package com.db.repository;

import com.db.model.TransactionLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionLog, Long> {
    List<TransactionLog> findByCreateDate(Date createDate);
}
