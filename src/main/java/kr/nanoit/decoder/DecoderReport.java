package kr.nanoit.decoder;

import kr.nanoit.dto.send.IndexSendAck;
import kr.nanoit.dto.send.LengthSendAck;

public class DecoderReport {

    public String resultCode(byte[] byteOfReceive) {
        return (new String(byteOfReceive, (IndexSendAck.BIZ_INDEX_SEND_ACKNOWLEDGEMENT_RESULT_CODE), LengthSendAck.BIZ_LENGTH_SEND_ACKNOWLEDGEMENT_RESULT_CODE)).trim();
    }

    public String messageKey(byte[] byteOfReceive) {
        return (new String(byteOfReceive, (IndexSendAck.BIZ_INDEX_SEND_ACKNOWLEDGEMENT_MESSAGE_ID), LengthSendAck.BIZ_LENGTH_SEND_ACKNOWLEDGEMENT_MESSAGE_ID)).trim();
    }

    public String messageType(byte[] byteOfReceiveData) {
        return (new String(byteOfReceiveData, (IndexSendAck.BIZ_INDEX_SEND_ACKNOWLEDGEMENT_MESSAGE_TYPE), LengthSendAck.BIZ_LENGTH_SEND_ACKNOWLEDGEMENT_MESSAGE_TYPE)).trim();
    }
}
