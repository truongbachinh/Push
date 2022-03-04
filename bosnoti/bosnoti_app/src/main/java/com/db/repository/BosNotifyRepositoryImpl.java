package com.db.repository;

import com.db.model.BosNotificationEntity;
import com.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Repository
public class BosNotifyRepositoryImpl implements BosNotifyRepository {

    private static Logger logger = LoggerFactory.getLogger(BosNotifyRepositoryImpl.class);

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<BosNotificationEntity> getBosNotification(String accountNo, String subNo, String status, String funcCD, int startSeq, int rowNum) {

        logger.info("calling NameQuery fxc_get_notif_list");

        TypedQuery<BosNotificationEntity> query = entityManager.createNamedQuery("fxc_get_notif_list", BosNotificationEntity.class);
        query.setParameter(1, accountNo);
        query.setParameter(2, subNo);
        query.setParameter(3, status);
        query.setParameter(4, funcCD);
        query.setParameter(5, startSeq);
        query.setParameter(6, rowNum);

        List<BosNotificationEntity> listMessages = query.getResultList();

        return listMessages;
    }
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

    @Override
    public int updateBosNotification(BosNotificationEntity notificationEntity) {
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("vn.pkg_notif.pxc_upd_notif_sent_st")
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, Date.class, ParameterMode.IN)
                .registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(8, Integer.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(9, Integer.class, ParameterMode.OUT)
                .setParameter(1, StringHelper.stringToLong(notificationEntity.getSeqNo(),0))
                .setParameter(2, notificationEntity.getStatus())
                .setParameter(3, new Date())
                .setParameter(4, notificationEntity.getSentErrCD())
                .setParameter(5, notificationEntity.getSentErrMsg())
                .setParameter(6, notificationEntity.getWorkMN())
                .setParameter(7, notificationEntity.getWorkMN())
                ;

        query.execute();

        int commentCount = (int) query.getOutputParameterValue(9);

        return commentCount;
    }

}
