package kr.nanoit.dto.send;


import kr.nanoit.dto.messsage_Structure.IndexHeader;

/**
 * The type Index send ack.
 */
public class IndexSendAck {
    /**
     * The constant BIZ_INDEX_SEND_ACKNOWLEDGEMENT_RESULT_CODE.
     */
    public static int BIZ_INDEX_SEND_ACKNOWLEDGEMENT_RESULT_CODE = IndexHeader.COMMON_INDEX_HEADER_FULL_LENGTH;
    /**
     * The constant BIZ_INDEX_SEND_ACKNOWLEDGEMENT_MESSAGE_TYPE.
     */
    public static int BIZ_INDEX_SEND_ACKNOWLEDGEMENT_MESSAGE_TYPE = BIZ_INDEX_SEND_ACKNOWLEDGEMENT_RESULT_CODE + LengthSendAck.BIZ_LENGTH_SEND_ACKNOWLEDGEMENT_RESULT_CODE;
    /**
     * The constant BIZ_INDEX_SEND_ACKNOWLEDGEMENT_MESSAGE_ID.
     */
    public static int BIZ_INDEX_SEND_ACKNOWLEDGEMENT_MESSAGE_ID = BIZ_INDEX_SEND_ACKNOWLEDGEMENT_MESSAGE_TYPE + LengthSendAck.BIZ_LENGTH_SEND_ACKNOWLEDGEMENT_MESSAGE_TYPE;
    /**
     * The constant BIZ_INDEX_SEND_ACKNOWLEDGEMENT_LENGTH.
     */
    public static int BIZ_INDEX_SEND_ACKNOWLEDGEMENT_LENGTH = BIZ_INDEX_SEND_ACKNOWLEDGEMENT_MESSAGE_ID + LengthSendAck.BIZ_LENGTH_SEND_ACKNOWLEDGEMENT_MESSAGE_ID;
}

//    public void login_Ack_Receive() throws Exception {
//        log.info("[HTTPCLIENT] Login SUCCESS! PACKET_TYPE : {} RESULT_CODE : {} SMS_TPS : {} LMS_TPS : {} MMS_TPS : {} KAT_TPS : {} KFT_TPS : {} GMS_TPS : {}",
//                socketUtil.getPacketType(receiveByte), decodeLoginAck.resultCode(receiveByte), decodeLoginAck.smsTps(receiveByte), decodeLoginAck.lmsTps(receiveByte),
//                decodeLoginAck.mmsTps(receiveByte), decodeLoginAck.katTps(receiveByte), decodeLoginAck.kftTps(receiveByte), decodeLoginAck.gmsTps(receiveByte));
//    }
//
