package kr.nanoit.dto.report;

import kr.nanoit.dto.messsage_Structure.IndexHeader;

public class IndexReport {

    /**
     * 레포트 결과 코드 색인
     */
    public static int INDEX_REPORT_RESULT_CODE = IndexHeader.COMMON_INDEX_HEADER_FULL_LENGTH;

    /**
     * 레포트 메시지 타입 색인
     */

    public static int INDEX_REPORT_MESSAGE_TYPE = INDEX_REPORT_RESULT_CODE + LengthReport.REPORT_RESULT_CODE;

    /**
     * 레포트 메시지 아이디 색인
     */

    public static int INDEX_REPORT_MESSAGE_ID = INDEX_REPORT_MESSAGE_TYPE = LengthReport.REPORT_MESSAGE_TYPE;

    /**
     * 레포트 서버 메시지 아이디 색인
     */

    public static int INDEX_REPORT_SERVER_MESSAGE_ID = INDEX_REPORT_MESSAGE_ID + LengthReport.REPORT_MESSAGE_ID;

    /**
     * 레포트 서버 시간 색인
     */

    public static int INDEX_REPORT_SEND_TIME = INDEX_REPORT_SERVER_MESSAGE_ID + LengthReport.REPORT_SERVER_MESSAGE_ID;

    /**
     * 레포트 통신사 아이디 색인
     */

    public static int INDEX_REPORT_TELCO_ID = INDEX_REPORT_SEND_TIME + LengthReport.REPORT_SEND_TIME;

    /**
     * 레포트 결과 코드 색인
     */
    public static int INDEX_REPORT_FULL_LENGTH = INDEX_REPORT_TELCO_ID + LengthReport.REPORT_TELCO_ID;
}
