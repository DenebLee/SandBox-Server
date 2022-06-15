package kr.nanoit.dto.send;

import kr.nanoit.dto.PacketType;
import kr.nanoit.dto.login.IndexHeader;

import java.util.Arrays;

import static kr.nanoit.dto.login.LengthHeader.COMMON_LENGTH_HEADER_PACKET_TYPE_INDEX;
import static kr.nanoit.dto.send.ArrayList.BYTE_SPACE;

public class EncoderMessageService {

    public byte[] send_ack(SMSMessageService smsMessageService) {
        byte[] data = new byte[IndexSendAck.BIZ_INDEX_SEND_ACKNOWLEDGEMENT_LENGTH];

        Arrays.fill(data, 0, data.length, BYTE_SPACE);
        String bodySize = Integer.toString(data.length - IndexHeader.COMMON_INDEX_HEADER_FULL_LENGTH);

        System.arraycopy(PacketType.SEND_ACKNOWLEDGEMENT.getBytes(), 0, data, COMMON_LENGTH_HEADER_PACKET_TYPE_INDEX, PacketType.SEND_ACKNOWLEDGEMENT.getBytes().length);
        System.arraycopy(bodySize.getBytes(), 0, data, IndexHeader.COMMON_INDEX_HEADER_BODY_LEN, bodySize.getBytes().length);

        System.arraycopy(MessageType.SMS.getBytes(), 0, data, IndexSendAck.BIZ_INDEX_SEND_ACKNOWLEDGEMENT_MESSAGE_TYPE, MessageType.SMS.getBytes().length);
        System.arraycopy(smsMessageService.getTr_num().getBytes(), 0, data, IndexSendAck.BIZ_INDEX_SEND_ACKNOWLEDGEMENT_MESSAGE_ID, smsMessageService.getTr_num().getBytes().length);
        System.arraycopy("0".getBytes(), 0, data, IndexSendAck.BIZ_INDEX_SEND_ACKNOWLEDGEMENT_RESULT_CODE, "0".getBytes().length);
        return data;
    }

}
