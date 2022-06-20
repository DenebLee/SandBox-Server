package kr.nanoit.config;

import kr.nanoit.dto.messsage_Structure.IndexHeader;
import kr.nanoit.dto.messsage_Structure.MessageType;
import kr.nanoit.dto.messsage_Structure.PacketType;
import kr.nanoit.dto.messsage_Structure.SMSMessageService;
import kr.nanoit.dto.report.IndexReport;
import kr.nanoit.dto.send.IndexSendAck;

import java.util.Arrays;

import static kr.nanoit.dto.messsage_Structure.LengthHeader.COMMON_LENGTH_HEADER_PACKET_TYPE_INDEX;

public class MakePacket {
    public static final byte BYTE_SPACE = ' ';

    // SEND_ACK 제작
    public static byte[] send_ack(SMSMessageService smsMessageService) {
        byte[] data = new byte[IndexSendAck.BIZ_INDEX_SEND_ACKNOWLEDGEMENT_LENGTH];

        Arrays.fill(data, 0, data.length, BYTE_SPACE);
        String bodySize = Integer.toString(data.length - IndexHeader.COMMON_INDEX_HEADER_FULL_LENGTH);

        /*
          Header (packetType + bodyLength)
         */

        System.arraycopy(PacketType.SEND_ACKNOWLEDGEMENT.getBytes(), 0, data, COMMON_LENGTH_HEADER_PACKET_TYPE_INDEX, PacketType.SEND_ACKNOWLEDGEMENT.getBytes().length);
        System.arraycopy(bodySize.getBytes(), 0, data, IndexHeader.COMMON_INDEX_HEADER_BODY_LEN, bodySize.getBytes().length);

        /*
          Body (reusltCode + messageType + messageId)
         */

        System.arraycopy(MessageType.SMS.getBytes(), 0, data, IndexSendAck.BIZ_INDEX_SEND_ACKNOWLEDGEMENT_MESSAGE_TYPE, MessageType.SMS.getBytes().length);
        System.arraycopy(smsMessageService.getTr_num().getBytes(), 0, data, IndexSendAck.BIZ_INDEX_SEND_ACKNOWLEDGEMENT_MESSAGE_ID, smsMessageService.getTr_num().getBytes().length);
        System.arraycopy("0".getBytes(), 0, data, IndexSendAck.BIZ_INDEX_SEND_ACKNOWLEDGEMENT_RESULT_CODE, "0".getBytes().length);
        return data;
    }

    /**
     * System.arraycopy Param
     * src - 전송원 배열
     * srcPos - 소스 배열의 개시 위치
     * dest - 전송처 배열
     * destPos - 전송처 데이터내의 개시 위치
     * length - 카피되는 배열 요소의 수
     */

    //  Login_ACK 제작
    public byte[] login() {
        byte[] data = new byte[55];
        Arrays.fill(data, 0, data.length, BYTE_SPACE); // 배열 내 모든 값 초기화
        System.arraycopy("LOGIN_ACK".getBytes(), 0, data, 0, "LOGIN_ACK".getBytes().length);
        System.arraycopy("35".getBytes(), 0, data, 10, "35".getBytes().length);
        System.arraycopy("0".getBytes(), 0, data, 20, "0".getBytes().length);
        System.arraycopy("80".getBytes(), 0, data, 25, "80".getBytes().length);
        System.arraycopy("50".getBytes(), 0, data, 30, "50".getBytes().length);
        System.arraycopy("10".getBytes(), 0, data, 35, "10".getBytes().length);
        System.arraycopy("80".getBytes(), 0, data, 40, "80".getBytes().length);
        System.arraycopy("80".getBytes(), 0, data, 45, "80".getBytes().length);
        System.arraycopy("80".getBytes(), 0, data, 50, "80".getBytes().length);

        return data;
    }

    public byte[] report(SMSMessageService smsMessageService) {
        byte[] data = new byte[IndexReport.INDEX_REPORT_FULL_LENGTH];

        Arrays.fill(data, 0, data.length, BYTE_SPACE);
        String bodySize = Integer.toString(data.length - IndexHeader.COMMON_INDEX_HEADER_FULL_LENGTH);

        /*
          Header (packetType + bodyLength)
         */

        System.arraycopy(PacketType.REPORT.getBytes(), 0, data, COMMON_LENGTH_HEADER_PACKET_TYPE_INDEX, PacketType.REPORT.getBytes().length);
        System.arraycopy(bodySize.getBytes(), 0, data, IndexHeader.COMMON_INDEX_HEADER_BODY_LEN, bodySize.getBytes().length);

        /*
          Body (resultCode + messageType + messageId + serverMessageid + sendTime + telecomId)
         */

        System.arraycopy(smsMessageService.getTr_num().getBytes(), 0, data, IndexReport.INDEX_REPORT_RESULT_CODE, smsMessageService.getTr_num().getBytes().length);
        System.arraycopy(smsMessageService.getMessageServiceType().getBytes(), 0, data, IndexReport.INDEX_REPORT_MESSAGE_TYPE, smsMessageService.getMessageServiceType().getBytes().length);
        System.arraycopy(smsMessageService.getTr_rsltstat().getBytes(), 0, data, IndexReport.INDEX_REPORT_MESSAGE_ID, smsMessageService.getTr_rsltstat().getBytes().length);
        System.arraycopy(smsMessageService.getServerMessageId().getBytes(), 0, data, IndexReport.INDEX_REPORT_SERVER_MESSAGE_ID, smsMessageService.getServerMessageId().getBytes().length);
        System.arraycopy(smsMessageService.getSend_time().getBytes(), 0, data, IndexReport.INDEX_REPORT_SEND_TIME, smsMessageService.getSend_time().getBytes().length);
        System.arraycopy("KT".getBytes(), 0, data, IndexReport.INDEX_REPORT_TELCO_ID, "KT".getBytes().length);
        return data;
    }

}
