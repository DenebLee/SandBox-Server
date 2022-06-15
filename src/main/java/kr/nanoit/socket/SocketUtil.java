package kr.nanoit.socket;

import kr.nanoit.dto.PacketType;
import kr.nanoit.dto.login.IndexHeader;
import kr.nanoit.dto.login.LengthHeader;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketUtil {

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Socket socket;


    public SocketUtil(Socket socket) throws IOException {
        this.socket = socket;
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.dataInputStream = new DataInputStream(socket.getInputStream());
    }

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

    public byte[] read(final int wholeLength) throws Exception {
        byte[] readData = new byte[wholeLength];
        int sumOfBytesRead = 0;
        int numberOfBytesRead;

        while (wholeLength > sumOfBytesRead) {
            numberOfBytesRead = dataInputStream.read(readData, sumOfBytesRead, (wholeLength - sumOfBytesRead));
            if (numberOfBytesRead <= 0) {
                System.out.println(String.format("[$WARNNING$][read error] End Of The Stream has been reached! return null! - Whole Length:%s, Sum Of Bytes Read:%s, Number Of Bytes Read:%s", wholeLength, sumOfBytesRead, numberOfBytesRead));
                return null;
            } else {
                sumOfBytesRead += numberOfBytesRead;
            }
        }
        return readData;
    }

    public PacketType getPacketType(byte[] byteOfReceiveData) throws Exception {
        return PacketType.fromPropertyName(new String(byteOfReceiveData, LengthHeader.COMMON_LENGTH_HEADER_PACKET_TYPE_INDEX, LengthHeader.COMMON_LENGTH_HEADER_PACKET_TYPE).trim());
    }

    public boolean write(byte [] sendData) throws IOException {
        try {
            int first = 0;
            int len;
            int write;
            len = sendData.length;

            while (len > 0) {
                write = len;

                dataOutputStream.write(sendData, first, write);
                dataOutputStream.flush();

                len -= write;
                first += write;
            }
        } catch (Exception e) {
            e.printStackTrace();
            socket.close();
            return false;
        }
        return true;
    }





}
