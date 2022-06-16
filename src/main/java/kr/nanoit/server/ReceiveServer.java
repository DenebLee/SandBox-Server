package kr.nanoit.server;

import kr.nanoit.config.Crypt;
import kr.nanoit.config.QueueList;
import kr.nanoit.decoder.DecoderLogin;
import kr.nanoit.dto.login.LoginMessageService;
import kr.nanoit.dto.login.Login_Packet_UserInfo;
import kr.nanoit.dto.messsage_Structure.MessageType;
import kr.nanoit.dto.messsage_Structure.SMSMessageService;
import kr.nanoit.main.Main;
import kr.nanoit.decoder.DecoderSMSMessageService;
import kr.nanoit.socket.SocketUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.sql.Timestamp;

@Slf4j
public class ReceiveServer implements Runnable {

    private final QueueList queueList;
    private final Socket socket;
    private final SocketUtil socketUtil;
    private final DecoderLogin decoderLogin;
    private final Crypt crypt;
    private final Login_Packet_UserInfo loginPacketUserInfo;
    private final DecoderSMSMessageService decoderSMSMessageService;


    public ReceiveServer( Socket socket, QueueList queueList, Login_Packet_UserInfo loginPacketUserInfo) throws IOException {

        socketUtil = new SocketUtil(socket);
        decoderLogin = new DecoderLogin();
        crypt = new Crypt();
        this.loginPacketUserInfo = loginPacketUserInfo;
        decoderSMSMessageService = new DecoderSMSMessageService();

        this.socket = socket;
        this.queueList = queueList;
    }

    @SneakyThrows
    @Override
    public void run() {
        try {
            while (true) {
                byte[] receiveByte = socketUtil.receiveByte();
                if (receiveByte != null) {
                    switch (socketUtil.getPacketType(receiveByte)) {
                        case LOGIN:
                            decoderLogin(receiveByte);
                            break;
                        case SEND:
                            decoderSend(receiveByte);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            log.error("IOException occurred", e);
            socket.close();
        }
    }

    public void decoderLogin(byte[] receiveBytes) throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        LoginMessageService loginMessageService = new LoginMessageService();
        loginMessageService.setProtocol("LOGIN_ACK");
        loginMessageService.setId(decoderLogin.id(receiveBytes));
        loginMessageService.setPassword(decoderLogin.password(receiveBytes));
        loginMessageService.setVersion(decoderLogin.version(receiveBytes));


        // init UserLoginPacketUserinfo data
        loginPacketUserInfo.setPacket_login_id(loginMessageService.getId());
        loginPacketUserInfo.setPacket_login_password(loginMessageService.getPassword());
        loginPacketUserInfo.setPacket_login_version(loginMessageService.getVersion());


        if (Main.valificationMap.containsKey(decoderLogin.id(receiveBytes))) {
            crypt.cryptInit(Main.valificationMap.get(loginMessageService.getId()).getEncryptKey());
            if (Main.valificationMap.get(decoderLogin.id(receiveBytes)).getPassword().contains(new String(crypt.deCrypt(decoderLogin.password(receiveBytes))))) {
                log.info("[응답] [로그인 성공] ID : {} PW : {} VERSION : {}", loginMessageService.getId(), loginMessageService.getPassword(), loginMessageService.getVersion());
            } else {
                log.info("[응답] [로그인 실패] ID : {} PW : {} VERSION : {}", loginMessageService.getId(), loginMessageService.getPassword(), loginMessageService.getVersion());
            }
        } else {
            log.info("[응답] [로그인 실패] ID : {} PW : {} VERSION : {}", loginMessageService.getId(), loginMessageService.getPassword(), loginMessageService.getVersion());
        }
        queueList.getQueue_for_Send().offer(loginMessageService); // 로그인에 대한 응답 sendQueue에 쌓기
    }

    public void decoderSend(byte[] receiveBytes) throws Exception {
        SMSMessageService smsMessageService = new SMSMessageService();
        smsMessageService.setMessageServiceType(MessageType.SMS);
        smsMessageService.setProtocol("SEND_ACK");

        smsMessageService.setTr_rsltstat("0");
        smsMessageService.setTr_num(decoderSMSMessageService.messageKey(receiveBytes));
        smsMessageService.setTr_phone(new String(crypt.deCrypt(decoderSMSMessageService.phone(receiveBytes))));
        smsMessageService.setTr_callback(new String(crypt.deCrypt(decoderSMSMessageService.callback(receiveBytes))));
        smsMessageService.setTr_msg(new String(crypt.deCrypt(decoderSMSMessageService.content(receiveBytes)), "MS949"));

        smsMessageService.setTr_bill_id(new String(crypt.deCrypt(decoderSMSMessageService.billId(receiveBytes))));
        smsMessageService.setTr_org_callback(new String(crypt.deCrypt(decoderSMSMessageService.orgCallback(receiveBytes))));
        smsMessageService.setTr_senddate(new Timestamp(System.currentTimeMillis()));

        log.info("[받음] [요청 제출] [메세지 타입 : {}] TR_NUM : {}  TR_SENDSTAT : {} {} ", MessageType.SMS, smsMessageService.getTr_num(), smsMessageService.getTr_sendstat(), smsMessageService);
        queueList.getQueue_for_Send().offer(smsMessageService); // Client 요청 데이터 대한 응답 sendQueue에 쌓기
    }
}


