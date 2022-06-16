package kr.nanoit.server;

import kr.nanoit.config.MakePacket;
import kr.nanoit.dto.login.LoginMessageService;
import kr.nanoit.dto.messsage_Structure.MessageService;
import kr.nanoit.dto.messsage_Structure.SMSMessageService;
import kr.nanoit.socket.SocketUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SendServer implements Runnable {


    private final SocketUtil socketUtil;
    private final MakePacket makePacket;
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);


    public SendServer(SocketUtil socketUtil) throws IOException {
        this.socketUtil = socketUtil;
        makePacket = new MakePacket();
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

                MessageService messageService = socketUtil.getQueue_for_Send().poll(1000, TimeUnit.MICROSECONDS);
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
                                String serverIdTest = "안녕하세요";
                                smsMessageService.setServerMessageId(serverIdTest);

                                log.info("[응답] [SEND_ACK] [ID : {}] [메시지 타입 : {} ] TR_NUM : {} 보낸시간 : {} SERVER_MESSAGE_ID : {} SUCCESS", socketUtil.getPacket_login_id(), smsMessageService.getMessageServiceType(), smsMessageService.getTr_num(), smsMessageService.getTr_rsltstat(), smsMessageService.getServerMessageId());

                                socketUtil.getQueue_for_Report().offer(smsMessageService);
                            } else {
                                log.info("[응답] [SEND_ACK] [ID : {}] [메시지 타입 : {} ] TR_NUM : {} 보낸시간 : {} SERVER_MESSAGE_ID : {} FAIL", socketUtil.getPacket_login_id(), smsMessageService.getMessageServiceType(), smsMessageService.getTr_num(), smsMessageService.getTr_rsltstat(), smsMessageService.getServerMessageId());
                            }

                            // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                            // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

                        } else if (smsMessageService.getProtocol().contains("REPORT")) {
                            if (socketUtil.write(makePacket.report(smsMessageService))) {
                                System.out.println("--------------------------------------------------------------------------------------------------------");
                                System.out.println("--------------------------------------------------------------------------------------------------------");
                                log.info("[응답] [REPORT] [ID : {}] [TR_NUM : {}] [TR_RSLTSTAT : {}] SUCCESS", socketUtil.getPacket_login_id(), smsMessageService.getMessageServiceType(), smsMessageService.getTr_num());
                                System.out.println("--------------------------------------------------------------------------------------------------------");
                                System.out.println("--------------------------------------------------------------------------------------------------------");
                            } else {
                                System.out.println("--------------------------------------------------------------------------------------------------------");
                                System.out.println("--------------------------------------------------------------------------------------------------------");
                                log.info("[응답] [REPORT] [ID : {}] [TR_NUM : {}] FAIL", socketUtil.getPacket_login_id(), smsMessageService.getTr_num());
                                System.out.println("--------------------------------------------------------------------------------------------------------");
                                System.out.println("--------------------------------------------------------------------------------------------------------");

                            }
                        }
                    }

                    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

                }
            } catch (Exception e) {
                e.printStackTrace();
                socketUtil.socketClose();
            }
        }
    }
}
