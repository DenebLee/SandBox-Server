package kr.nanoit.dto.send;


import kr.nanoit.dto.login.IndexHeader;

/**
 * The type Index report.
 */
public class IndexReport {
    /**
     * The constant BIZ_INDEX_REPORT_RESULT_CODE.
     */
    public static int BIZ_INDEX_REPORT_RESULT_CODE = IndexHeader.COMMON_INDEX_HEADER_FULL_LENGTH;
    /**
     * The constant BIZ_INDEX_REPORT_MESSAGE_TYPE.
     */
    public static int BIZ_INDEX_REPORT_MESSAGE_TYPE = BIZ_INDEX_REPORT_RESULT_CODE + LengthReport.BIZ_LENGTH_REPORT_RESULT_CODE;
    /**
     * The constant BIZ_INDEX_REPORT_MESSAGE_ID.
     */
    public static int BIZ_INDEX_REPORT_MESSAGE_ID = BIZ_INDEX_REPORT_MESSAGE_TYPE + LengthReport.BIZ_LENGTH_REPORT_MESSAGE_TYPE;
    /**
     * The constant BIZ_INDEX_REPORT_SERVER_MSG_ID.
     */
    public static int BIZ_INDEX_REPORT_SERVER_MSG_ID = BIZ_INDEX_REPORT_MESSAGE_ID + LengthReport.BIZ_LENGTH_REPORT_MESSAGE_ID;
    /**
     * The constant BIZ_INDEX_REPORT_SEND_TIME.
     */
    public static int BIZ_INDEX_REPORT_SEND_TIME = BIZ_INDEX_REPORT_SERVER_MSG_ID + LengthReport.BIZ_LENGTH_REPORT_SERVER_MSG_ID;
    /**
     * The constant BIZ_INDEX_REPORT_TELCO_ID.
     */
    public static int BIZ_INDEX_REPORT_TELCO_ID = BIZ_INDEX_REPORT_SEND_TIME + LengthReport.BIZ_LENGTH_REPORT_SEND_TIME;
    /**
     * The constant BIZ_INDEX_REPORT_FULL_LENGTH.
     */
    public static int BIZ_INDEX_REPORT_FULL_LENGTH = BIZ_INDEX_REPORT_TELCO_ID + LengthReport.BIZ_LENGTH_REPORT_TELCO_ID;
}