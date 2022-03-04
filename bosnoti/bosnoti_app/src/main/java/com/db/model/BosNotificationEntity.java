package com.db.model;

import javax.persistence.*;
import java.util.Date;

/*
    xcs01m10.dt,
    xcs01m10.seq_no,
    xcs01m10.acnt_no,
    xcs01m10.sub_no,
    xcs01m10.func_cd,
    xcs01m10.notif_title,
    xcs01m10.notif_msg,
    xcs01m10.work_mn,
    xcs01m10.sent_dtm,
    xcs01m10.sent_err_cd,
    xcs01m10.sent_err_msg
 */

@Entity

/* input
        i_acnt_no       in  varchar2,   -- Tai khoan
        i_sub_no        in  varchar2,   -- Tieu khoan
        i_sent_st       in  varchar2,   -- Trang thai gui thong bao
        i_func_cd       in  varchar2,   -- Ma nghiep vu
        i_st_seq_no     in  number,     -- Start sequence number
        i_row_number    in  number     -- So luong ket qua tra ra: null khong gioi han so luong
 */
/* output
                    x1.dt,
                    x1.seq_no,
                    x1.acnt_no,
                    x1.sub_no,
                    x1.func_cd,
                    x1.notif_title,
                    x1.notif_msg,
                    x1.work_mn,
                    x1.sent_dtm,
                    x1.sent_err_cd,
                    x1.sent_err_msg
 */

@org.hibernate.annotations.NamedNativeQuery(
        name = "fxc_get_notif_list",
        query = "{ ? = call vn.pkg_notif.fxc_get_notif_list(?,?,?,?,?,?) }",
        callable = true,
        //resultClass = BosNotificationEntity.class
        resultSetMapping = "bos_notification"
)

@SqlResultSetMapping(
        name = "bos_notification",
        entities = {
                @EntityResult(
                        entityClass = BosNotificationEntity.class,
                        fields = {
                                @FieldResult(name = "dt", column = "dt"),
                                @FieldResult(name = "seqNo", column = "seq_no"),
                                @FieldResult(name = "accountNo", column = "acnt_no"),
                                @FieldResult(name = "subNo", column = "sub_no"),
                                @FieldResult(name = "funcCD", column = "func_cd"),
                                @FieldResult(name = "notifyTitle", column = "notif_title"),
                                @FieldResult(name = "notifyMsg", column = "notif_msg"),
                                @FieldResult(name = "workMN", column = "work_mn"),
                                @FieldResult(name = "sentDtm", column = "sent_dtm"),
                                @FieldResult(name = "sentErrCD", column = "sent_err_cd"),
                                @FieldResult(name = "sentErrMsg", column = "sent_err_msg")
                        }
                )
        }
)

/*
    procedure pxc_upd_notif_sent_st(
        i_seq_no        in  number,         -- Message ID
        i_sent_st       in  varchar2,       -- Trang thai gui thong bao
        i_sent_dtm      in  date,           -- Thoi gian gui thong bao
        i_sent_err_cd   in  varchar2,       -- Ma loi (neu co)
        i_sent_err_msg  in  varchar2,       -- Noi dung loi (neu co)
        i_work_mn       in  varchar2,       -- Nguoi cap nhat
        i_work_trm      in  varchar2,       -- IP cap nhat
        o_upd_row       out number,         -- So luong ban ghi duoc update
        o_ret           out number          -- Ket qua process: < 0: loi;   0: Khong co row nao duoc update;    1: Update thanh cong
    );
 */
@NamedStoredProcedureQuery(
        name = "pxc_upd_notif_sent_st",
        procedureName = "vn.pkg_notif.pxc_upd_notif_sent_st",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "i_seq_no", type = Long.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "i_sent_st", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "i_sent_dtm", type = Date.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "i_sent_err_cd", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "i_sent_err_msg", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "i_work_mn", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "i_work_trm", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "o_upd_row", type = Long.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "o_ret", type = Long.class)
        }
)

public class BosNotificationEntity {
    @Id
    private String seqNo;

    private String dt;
    private String accountNo;
    private String subNo;
    private String funcCD;
    private String notifyTitle;
    private String notifyMsg;
    private String workMN;
    private String sentDtm;
    private String sentErrCD;
    private String sentErrMsg;

    @Transient
    private String status;

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getSubNo() {
        return subNo;
    }

    public void setSubNo(String subNo) {
        this.subNo = subNo;
    }

    public String getFuncCD() {
        return funcCD;
    }

    public void setFuncCD(String funcCD) {
        this.funcCD = funcCD;
    }

    public String getNotifyTitle() {
        return notifyTitle;
    }

    public void setNotifyTitle(String notifyTitle) {
        this.notifyTitle = notifyTitle;
    }

    public String getNotifyMsg() {
        return notifyMsg;
    }

    public void setNotifyMsg(String notifyMsg) {
        this.notifyMsg = notifyMsg;
    }

    public String getWorkMN() {
        return workMN;
    }

    public void setWorkMN(String workMN) {
        this.workMN = workMN;
    }

    public String getSentDtm() {
        return sentDtm;
    }

    public void setSentDtm(String sentDtm) {
        this.sentDtm = sentDtm;
    }

    public String getSentErrCD() {
        return sentErrCD;
    }

    public void setSentErrCD(String sentErrCD) {
        this.sentErrCD = sentErrCD;
    }

    public String getSentErrMsg() {
        return sentErrMsg;
    }

    public void setSentErrMsg(String sentErrMsg) {
        this.sentErrMsg = sentErrMsg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BosNotificationEntity{" +
                "dt=" + dt +
                ", seqNo='" + seqNo + '\'' +
                ", accountNo='" + accountNo + '\'' +
                ", notifyTitle='" + notifyTitle +
                '}';
    }
}
