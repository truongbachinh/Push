package com.util;

public class Constant {

    public static final String NOTICE_STATUS_WAITING = "W";  //Waiting: Chờ xử lý
    public static final String NOTICE_STATUS_PENDING = "P";  //Pending: Chưa xử lý
    public static final String NOTICE_STATUS_SUCCESS = "S";  //Success: Xử lý thành công
    public static final String NOTICE_STATUS_FAILURE = "F";  //Failure: Xử lý thất bại

    //Trang thai gui thong bao: N: Chua gui; I: Khong gui; Y: Da gui; E: Gui loi
    public static final String NOTICE_BOS_STATUS_WAITING = "N";  //Chua gui
    public static final String NOTICE_BOS_STATUS_PROCESS = "P";  //Dang gui
    public static final String NOTICE_BOS_STATUS_PENDING = "I";  //Khong gui
    public static final String NOTICE_BOS_STATUS_SUCCESS = "Y";  //Da gui
    public static final String NOTICE_BOS_STATUS_FAILURE = "E";  //Gui loi

    public static final String NOTICE_BOS_WORK_PROCESS_NAME = "AUTO_PUSH";  //Gui loi
    public static final String NOTICE_BOS_ERROR_CODE_SUCCESS = "000";  //Ma loi gui thanh cong
    public static final String NOTICE_BOS_ERROR_CODE_FAILED_100 = "100";  //Ma loi gui that bai sang PUSH process
    public static final String NOTICE_BOS_ERROR_CODE_FAILED_200 = "200";  //Ma loi PUSH process xu ly failed


}
