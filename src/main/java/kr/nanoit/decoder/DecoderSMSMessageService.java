package kr.nanoit.decoder;


import kr.nanoit.dto.send.IndexSend;
import kr.nanoit.dto.send.LengthSend;

/**
 * The type Send ack decoder.
 *

 */
public class DecoderSMSMessageService {
    /*    *//**
     * Result code string.
     *
     * @param byteOfReceive the byte of receive
     * @return the string
     *//*
    public String resultCode(byte[] byteOfReceive) {
        return (new String(byteOfReceive, (IndexSend), LengthSendAck.BIZ_LENGTH_SEND_ACKNOWLEDGEMENT_RESULT_CODE)).trim();
    }*/

    /**
     * Message key string.
     *
     * @param byteOfReceive the byte of receive
     * @return the string
     */
    public String messageKey(byte[] byteOfReceive) {
        return (new String(byteOfReceive, (IndexSend.BIZ_INDEX_SEND_MESSAGE_ID), LengthSend.BIZ_LENGTH_SEND_MESSAGE_ID)).trim();
    }

    /**
     * Message type string.
     *
     * @param byteOfReceiveData the byte of receive data
     * @return the string
     */
    public String messageType(byte[] byteOfReceiveData) {
        return (new String(byteOfReceiveData, (IndexSend.BIZ_INDEX_SEND_MESSAGE_TYPE), LengthSend.BIZ_LENGTH_SEND_MESSAGE_TYPE)).trim();
    }

    /**
     * Phone string.
     *
     * @param byteOfReceiveData the byte of receive data
     * @return the string
     */
    public String phone(byte[] byteOfReceiveData) {
        return (new String(byteOfReceiveData, (IndexSend.BIZ_INDEX_SEND_RECEIVE_NUMBER), LengthSend.BIZ_LENGTH_SEND_RECEIVE_NUMBER)).trim();
    }

    /**
     * Callback string.
     *
     * @param byteOfReceiveData the byte of receive data
     * @return the string
     */
    public String callback(byte[] byteOfReceiveData) {
        return (new String(byteOfReceiveData, (IndexSend.BIZ_INDEX_SEND_CALLBACK_NUMBER), LengthSend.BIZ_LENGTH_SEND_CALLBACK_NUMBER)).trim();
    }

    /**
     * Content string.
     *
     * @param byteOfReceiveData the byte of receive data
     * @return the string
     */
    public String content(byte[] byteOfReceiveData) {
        return (new String(byteOfReceiveData, (IndexSend.BIZ_INDEX_SEND_MESSAGE), LengthSend.BIZ_LENGTH_SEND_MESSAGE)).trim();
    }

    /**
     * File cnt string.
     *
     * @param byteOfReceiveData the byte of receive data
     * @return the string
     */
    public String file_cnt(byte[] byteOfReceiveData) {
        return (new String(byteOfReceiveData, (IndexSend.BIZ_INDEX_SEND_DATA_COUNT), LengthSend.BIZ_LENGTH_SEND_DATA_COUNT)).trim();
    }

    /**
     * Data type string.
     *
     * @param byteOfReceiveData the byte of receive data
     * @return the string
     */
    public String dataType(byte[] byteOfReceiveData) {
        return (new String(byteOfReceiveData, (IndexSend.BIZ_INDEX_SEND_ATTACHMENT_DATA_TYPE), LengthSend.BIZ_LENGTH_SEND_ATTACHMENT_DATA_TYPE)).trim();
    }

    /**
     * Data size string.
     *
     * @param byteOfReceiveData the byte of receive data
     * @return the string
     */
    public String dataSize(byte[] byteOfReceiveData) {
        return (new String(byteOfReceiveData, (IndexSend.BIZ_INDEX_SEND_ATTACHMENT_DATA_SIZE), LengthSend.BIZ_LENGTH_SEND_ATTACHMENT_DATA_SIZE)).trim();
    }

    /**
     * Subject string.
     *
     * @param byteOfReceiveData the byte of receive data
     * @return the string
     */
    public String subject(byte[] byteOfReceiveData) {
        return (new String(byteOfReceiveData, (IndexSend.BIZ_INDEX_SEND_MESSAGE), LengthSend.BIZ_LENGTH_SEND_MESSAGE)).trim();
    }

    /**
     * Bill id string.
     *
     * @param byteOfReceiveData the byte of receive data
     * @return the string
     */
    public String billId(byte[] byteOfReceiveData) {
        return (new String(byteOfReceiveData, (IndexSend.BIZ_INDEX_SEND_ORG_CALLBACK_NUMBER_SIZE), LengthSend.BIZ_LENGTH_SEND_BILL_ID)).trim();
    }

    /**
     * Org callback string.
     *
     * @param byteOfReceiveData the byte of receive data
     * @return the string
     */
    public String orgCallback(byte[] byteOfReceiveData) {
        return (new String(byteOfReceiveData, (IndexSend.BIZ_INDEX_SEND_ATTACHMENT_DATAB_SIZE), LengthSend.BIZ_LENGTH_SEND_ORG_CALLBACK_NUMBER)).trim();
    }
}
