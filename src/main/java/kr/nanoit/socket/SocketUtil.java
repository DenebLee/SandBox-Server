package kr.nanoit.socket;

import kr.nanoit.dto.PacketType;
import kr.nanoit.dto.login.IndexHeader;
import kr.nanoit.dto.login.LengthHeader;

public class SocketUtil extends SocketConfig {

    public byte[] receiveByte() throws Exception {
        byte[] header;
        byte[] body;
        header = read(IndexHeader.COMMON_INDEX_HEADER_FULL_LENGTH);
        if (header == null)
            return null;
//            System.out.println(new String(header));
        String bodyLen = new String(header, IndexHeader.COMMON_INDEX_HEADER_BODY_LEN, LengthHeader.COMMON_LENGTH_HEADER_BODY_LEN).trim();

        byte[] receiveData; // byte[] 선언
        if (bodyLen != null && !bodyLen.equals("")) {
            int bodyLength = Integer.parseInt(bodyLen); //
            receiveData = new byte[header.length + bodyLength];
            System.arraycopy(header, 0, receiveData, 0, header.length);

            if (bodyLength > 0) {
                body = read(bodyLength);
                if (body == null)
                    return null;

                System.arraycopy(body, 0, receiveData, header.length, body.length);
            }
        } else {
            receiveData = new byte[header.length];
            System.arraycopy(header, 0, receiveData, 0, header.length);
        }
        return receiveData;
    }

    public PacketType getPacketType(byte[] byteOfReceiveData) throws Exception {
        return PacketType.fromPropertyName(new String(byteOfReceiveData, LengthHeader.COMMON_LENGTH_HEADER_PACKET_TYPE_INDEX, LengthHeader.COMMON_LENGTH_HEADER_PACKET_TYPE).trim());
    }

}
