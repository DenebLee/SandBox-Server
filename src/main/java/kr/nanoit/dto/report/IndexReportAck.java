package kr.nanoit.dto.report;

import kr.nanoit.dto.messsage_Structure.IndexHeader;

import static kr.nanoit.dto.report.LengthReportAck.*;

public class IndexReportAck {


    public static int INDEX_REPORT_ACK_RESULT_CODE = IndexHeader.COMMON_INDEX_HEADER_FULL_LENGTH;

    public static int INDEX_REPORT_ACK_MESSAGE_TYPE = INDEX_REPORT_ACK_RESULT_CODE + REPORT_RESULT_CODE_ACK;

    public static int INDEX_REPORT_ACK_SERVER_MESSAGE_ID = INDEX_REPORT_ACK_MESSAGE_TYPE + REPORT_MESSAGE_TYPE_ACK;

    public static int INDEX_REPORT_ACK_FULL_LENGTH = INDEX_REPORT_ACK_SERVER_MESSAGE_ID + REPORT_SERVER_MESSAGE_ID_ACK;

}
