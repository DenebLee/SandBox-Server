package kr.nanoit.socket;

import kr.nanoit.config.Crypt;
import kr.nanoit.dto.Packet;
import kr.nanoit.login.DecoderLogin;
import kr.nanoit.login.IndexHeader;
import kr.nanoit.login.LengthHeader;
import kr.nanoit.login.LoginMessageService;
import kr.nanoit.main.Main;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class ReceiveServer implements Runnable {


    private final LinkedBlockingQueue<Packet> readStream;
    private final Socket socket;
    private final DataOutputStream dataOutputStream;
    private final DataInputStream dataInputStream;
    private  DecoderLogin decoderLogin;
    private Crypt crypt;
    private GetterSetter getterSetter;



    public ReceiveServer(LinkedBlockingQueue<Packet> readStream, Socket socket)throws IOException {
        this.readStream = readStream;
        this.socket = socket;
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        decoderLogin = new DecoderLogin();
        crypt = new Crypt();
        getterSetter = new GetterSetter();
    }
    @SneakyThrows
    @Override
    public void run() {
        try {
            while (true) {
                byte[] receiveByte = receiveByte();
                if (receiveByte != null) {
                    decoderLogin(receiveByte);
                }
            }

        } catch (Exception e) {
            log.error("IOException occurred", e);
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        }
    }

    private void decoderLogin(byte[] receiveBytes) throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        LoginMessageService loginMessageService = new LoginMessageService();
        loginMessageService.setProtocol("LOGIN_ACK");
        loginMessageService.setId(decoderLogin.id(receiveBytes));
        loginMessageService.setPassword(decoderLogin.password(receiveBytes));
        loginMessageService.setVersion(decoderLogin.version(receiveBytes));
//
//        getterSetter.setId(loginMessageService.getId());
//        getterSetter.setPassword(loginMessageService.getPassword());
//        getterSetter.setVersion(loginMessageService.getVersion());

        if (Main.identifyMap.containsKey(decoderLogin.id(receiveBytes))) {
            crypt.cryptInit(Main.identifyMap.get(loginMessageService.getId()).getEncryptKey());
            if (Main.identifyMap.get(decoderLogin.id(receiveBytes)).getPassword().contains(new String(crypt.deCrypt(decoderLogin.password(receiveBytes))))) {
                System.out.println(String.format("[RECEIVE] [LOGIN] ID:%s PW:%s VERSION:%s", loginMessageService.getId(), loginMessageService.getPassword(), loginMessageService.getVersion()));
            } else {
                System.out.println(String.format("[RECEIVE] [LOGIN FAIL] ID:%s PW:%s VERSION:%s", loginMessageService.getId(), loginMessageService.getPassword(), loginMessageService.getVersion()));
            }
        } else {
            System.out.println(String.format("[RECEIVE] [LOGIN FAIL] ID:%s PW:%s VERSION:%s", loginMessageService.getId(), loginMessageService.getPassword(), loginMessageService.getVersion()));
        }

    }

    public byte[] receiveByte() throws Exception {
            byte[] header;
            byte[] body;
            header = read(IndexHeader.COMMON_INDEX_HEADER_FULL_LENGTH);
            if (header == null)
                return null;
//            System.out.println(new String(header));
            String bodyLen = new String(header, IndexHeader.COMMON_INDEX_HEADER_BODY_LEN, LengthHeader.COMMON_LENGTH_HEADER_BODY_LEN).trim();

            byte[] receiveData;
            if (bodyLen != null && !bodyLen.equals("")) {
                int bodyLength = Integer.parseInt(bodyLen);
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
}
