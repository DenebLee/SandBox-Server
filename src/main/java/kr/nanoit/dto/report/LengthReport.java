package kr.nanoit.dto.report;

public class LengthReport {

    /**
    * Report 결과코드
    */

    public static int REPORT_RESULT_CODE = 5;

    /**
     * Report SMS : SMS 전송 , LMS : LMS 전송 MMS : MMS 전송
     */

    public static int REPORT_MESSAGE_TYPE = 5;

    /**
     * Report 고객사의 message 고유 id
     */

    public static int REPORT_MESSAGE_ID = 20;

    /**
     * Report Server의 message 고유 id
     */

    public static int REPORT_SERVER_MESSAGE_ID = 20;

    /**
     * Report 통신사로 메세지를 보낸 시간 (YYYYMMDDHHISS)
     */

    public static int REPORT_SEND_TIME = 15;

    /**
     * Report 수신자 전화번호 이동통신사 (SKT,KT,LG)
     */

    public static int REPORT_TELCO_ID = 4;
}
