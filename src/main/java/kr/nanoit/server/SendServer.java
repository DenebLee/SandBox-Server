package kr.nanoit.server;

import kr.nanoit.config.QueueList;
import kr.nanoit.dto.login.LoginMessageService;
import kr.nanoit.dto.login.Login_Packet_UserInfo;
import kr.nanoit.dto.messsage_Structure.MessageService;
import kr.nanoit.config.MakePacket;
import kr.nanoit.dto.messsage_Structure.SMSMessageService;
import kr.nanoit.socket.SocketUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SendServer implements Runnable {

    private QueueList queueList;

    private SocketUtil socketUtil;
    private Socket socket;
    private MakePacket makePacket;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
    private Login_Packet_UserInfo loginPacketUserInfo;


    public SendServer(Socket socket, QueueList queueList) throws IOException {
        this.socket = socket;
        this.queueList = queueList;
        socketUtil = new SocketUtil(socket);
        makePacket = new MakePacket();
        loginPacketUserInfo = new Login_Packet_UserInfo();
    }

    //  instanceof 객체가 어떤 클래스인지, 어떤 클래스를 상속받았는지 확인하는데 사용하는 연산자
    //   result T/F
    @Override
    public void run() {
        System.out.println("SenderServer is running right now");
        while (true) {
            try {
                // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                    MessageService messageService = queueList.getQueue_for_Send().poll(1000, TimeUnit.MICROSECONDS);
                    if (messageService != null) {
                        if (messageService instanceof LoginMessageService) {
                            LoginMessageService loginMessageService = (LoginMessageService) messageService;
                            if (socketUtil.write(makePacket.login())) {
                                log.info("[응답] [LOGIN_ACK] ID : {} , PASSWORD : {} VERSION : {} 성공", loginMessageService.getId(), loginMessageService.getPassword(), loginMessageService.getVersion());
                            } else {
                                log.info("[응답] [LOGIN_ACK] ID : {} , PASSWORD : {} VERSION : {} 오류", loginMessageService.getId(), loginMessageService.getPassword(), loginMessageService.getVersion());
                            }
                // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                        } else if (messageService instanceof SMSMessageService) {
                            SMSMessageService smsMessageService = (SMSMessageService) messageService;
                            if (smsMessageService.getProtocol().contains("SEND")) {
                                smsMessageService.setProtocol("SEND_ACK");
                                if (socketUtil.write(MakePacket.send_ack(smsMessageService))) {
                                    smsMessageService.setSend_time(formatter.format(System.currentTimeMillis()));
                                    String serverIdTest = "테스트 값";
                                    smsMessageService.setServerMessageId(serverIdTest);
                                    log.info("[응답] [SEND_ACK] [ID : {}] [메시지 타입 : {} ] TR_NUM : {} 보낸시간 : {} SERVER_MESSAGE_ID : {} SUCCESS", loginPacketUserInfo.getPacket_login_id(), smsMessageService.getMessageServiceType(), smsMessageService.getTr_num(), smsMessageService.getTr_rsltstat());
//                                    queueList.getQueue_for_Report().offer(smsMessageService);
                                }else{
                                    log.info("[응답] [SEND_ACK] [ID : {}] [메시지 타입 : {} ] TR_NUM : {} 보낸시간 : {} SERVER_MESSAGE_ID : {} FAIL", loginPacketUserInfo.getPacket_login_id(), smsMessageService.getMessageServiceType(), smsMessageService.getTr_num(), smsMessageService.getTr_rsltstat());

                                }
                            }
                        }
                // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

                    }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    socket.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
