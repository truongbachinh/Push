package com.db.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="TRANSACTION_LOG")
public class TransactionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HTTP_TRANS_SEQ")
    @SequenceGenerator(sequenceName = "transaction_seq", allocationSize = 1, name = "HTTP_TRANS_SEQ")
    Long id;

    @Column(name = "REQUEST_BODY")
    String requestBody;

    @Column(name = "RESPONSE_BODY")
    String responseBody;

    @Column(name = "CREATED_DATE")
    Date createDate;

    @Column(name = "RESPONSE_DATE")
    Date responseDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
    }
}
