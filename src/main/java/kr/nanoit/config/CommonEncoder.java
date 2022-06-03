package kr.nanoit.config;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static kr.nanoit.config.IndexHeader.COMMON_INDEX_HEADER_BODY_LEN;
import static kr.nanoit.config.IndexHeader.COMMON_INDEX_HEADER_FULL_LENGTH;
import static kr.nanoit.config.LengthHeader.COMMON_LENGTH_HEADER_BODY_LEN;
import static kr.nanoit.config.LengthHeader.COMMON_LENGTH_HEADER_PACKET_TYPE_INDEX;
import static kr.nanoit.config.ProtocolCommonVariable.BYTE_SPACE;


import static kr.nanoit.config.ProtocolCommonVariable.INTEGER_ZERO;

public class CommonEncoder {
    public CommonEncoder(){}


    public byte[] alive(){
        byte[] data = new byte[COMMON_INDEX_HEADER_FULL_LENGTH + LengthLoginAck.COMMON_LENGTH_LOGIN_ACKNOWLEDGEMENT_RESULT_CODE];
        Arrays.fill(data, INTEGER_ZERO, data.length, BYTE_SPACE);
        System.arraycopy(PakcetType.ALIVE_ACKNOWLEDGEMENT.getBytes(), INTEGER_ZERO, data, COMMON_LENGTH_HEADER_PACKET_TYPE_INDEX, PacketType.ALIVE_ACKNOWLEDGEMENT.getBytes().length);
        System.arraycopy("5".getBytes(), INTEGER_ZERO, data, COMMON_INDEX_HEADER_BODY_LEN, "5".getBytes().length);
        System.arraycopy("80", INTEGER_ZERO, data, COMMON_INDEX_HEADER_BODY_LEN, "50".getBytes().length);

        return data;
    }

    @Override
    public byte[] encrypt(String encryptString) {
        return null;
    }

    @Override
    public byte[] login(){
        byte[] data = new byte[55];
        Arrays.fill(data, INTEGER_ZERO, data.length, BYTE_SPACE);
        System.arraycopy("LOGIN_ACK".getBytes(), INTEGER_ZERO, 10 , "35".getBytes().length);
        System.arraycopy("LOGIN_ACK".getBytes(), INTEGER_ZERO, 10 , "35".getBytes().length);
        System.arraycopy("LOGIN_ACK".getBytes(), INTEGER_ZERO, 10 , "35".getBytes().length);
        System.arraycopy("LOGIN_ACK".getBytes(), INTEGER_ZERO, 10 , "35".getBytes().length);
        System.arraycopy("LOGIN_ACK".getBytes().INTEGER_ZERO, 50,"50".getBytes().length);

    }
    return

}
