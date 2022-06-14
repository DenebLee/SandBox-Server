package kr.nanoit.socket;

import kr.nanoit.config.Crypt;
import kr.nanoit.config.QueueList;
import kr.nanoit.dto.login.DecoderLogin;
import kr.nanoit.dto.login.LoginMessageService;
import kr.nanoit.dto.login.Login_Packet_UserInfo;
import kr.nanoit.dto.send.MessageType;
import kr.nanoit.dto.send.SMSMessageService;
import kr.nanoit.main.Main;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.sql.Timestamp;

@Slf4j
public class Decoder {
    private Login_Packet_UserInfo login_packet_userInfo;
    private Crypt crypt;
    private DecoderLogin decoderLogin;
    private QueueList queueList;
    private DecoderSMSMessageService decoderSMSMessageService;

    public Decoder() {
        login_packet_userInfo = new Login_Packet_UserInfo();
        crypt = new Crypt();
        decoderLogin = new DecoderLogin();
        queueList = new QueueList();
        decoderSMSMessageService = new DecoderSMSMessageService();
    }

    public void decoderLogin(byte[] receiveBytes) throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        LoginMessageService loginMessageService = new LoginMessageService();
        loginMessageService.setProtocol("LOGIN_ACK");
        loginMessageService.setId(decoderLogin.id(receiveBytes));
        loginMessageService.setPassword(decoderLogin.password(receiveBytes));
        loginMessageService.setVersion(decoderLogin.version(receiveBytes));

        System.out.println("로그인 메시지 서비스 안 데이터 확인" + loginMessageService);

        // init UserLoginPacketUserinfo data
        login_packet_userInfo.setPacket_login_id(loginMessageService.getId());
        login_packet_userInfo.setPacket_login_password(loginMessageService.getPassword());
        login_packet_userInfo.setPacket_login_version(loginMessageService.getVersion());



        if (Main.valificationMap.containsKey(decoderLogin.id(receiveBytes))) {
            crypt.cryptInit(Main.valificationMap.get(loginMessageService.getId()).getEncryptKey());
            if (Main.valificationMap.get(decoderLogin.id(receiveBytes)).getPassword().contains(new String(crypt.deCrypt(decoderLogin.password(receiveBytes))))) {
                log.info("[응답] [로그인 성공] ID : {} PW : {} VERSION : {}", loginMessageService.getId(), loginMessageService.getPassword(), loginMessageService.getVersion());
            } else {
                log.info("[응답] [로그인 실패] ID : {} PW : {} VERSION : {}", loginMessageService.getId(), loginMessageService.getPassword(), loginMessageService.getVersion());
            }
        } else {
            log.info(String.format("[응답] [로그인 실패] ID : {} PW : {} VERSION : {}", loginMessageService.getId(), loginMessageService.getPassword(), loginMessageService.getVersion()));
        }
        queueList.getQueue_for_Send().offer(loginMessageService); // 로그인에 대한 응답 sendQueue에 쌓기
        // 여기선 queue에 데이터가 삽입되는거 확인됨
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

        System.out.println(String.format("[RECEIVE] [SUBMIT_REQUEST] [%s] TR_NUM:%s TR_SENDSTAT:%s %s", MessageType.SMS, smsMessageService.getTr_num(), smsMessageService.getTr_sendstat(), smsMessageService));
        queueList.getQueue_for_Send().offer(smsMessageService); // Client요청 데이터 대한 응답 sendQueue에 쌓기
        System.out.println(" 로그인 응답 패킷에 대한 확인"+ queueList.getQueue_for_Send().offer(smsMessageService));
    }

    
}
