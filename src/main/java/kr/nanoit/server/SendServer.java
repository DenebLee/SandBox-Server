package kr.nanoit.server;

import kr.nanoit.config.QueueList;
import kr.nanoit.dto.login.LoginMessageService;
import kr.nanoit.dto.login.MessageService;
import kr.nanoit.dto.send.ArrayList;
import kr.nanoit.dto.send.SMSMessageService;
import kr.nanoit.socket.SocketUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SendServer implements Runnable {

    private QueueList queueList;
    private SocketUtil socketUtil;
    private Socket socket;
    private ArrayList arrayList;





    public SendServer(Socket socket) throws IOException {
        this.socket = socket;
        queueList = new QueueList();
        socketUtil = new SocketUtil(socket);
        arrayList = new ArrayList();
    }

    //  instanceof 객체가 어떤 클래스인지, 어떤 클래스를 상속받았는지 확인하는데 사용하는 연산자
    //   result T/F
    @Override
    public void run() {
        System.out.println("SenderServer is running right now");
        while (true) {
            try {
                MessageService messageService = queueList.getQueue_for_Send().poll(1000, TimeUnit.MICROSECONDS);
                if (messageService != null) {
                    if (messageService instanceof LoginMessageService) {
                        LoginMessageService loginMessageService = (LoginMessageService) messageService;
                        if (socketUtil.write(arrayList.login())) {
                            log.info("[응답] [LOGIN_ACK] ID : {} , PASSWORD : {} VERSION : {} 성공", loginMessageService.getId(), loginMessageService.getPassword(), loginMessageService.getVersion());
                        } else {
                            log.info("[응답] [LOGIN_ACK] ID : {} , PASSWORD : {} VERSION : {} 오류", loginMessageService.getId(), loginMessageService.getPassword(), loginMessageService.getVersion());
                        }
                    } else if (messageService instanceof SMSMessageService) {
                        SMSMessageService smsMessageService = (SMSMessageService) messageService;
                        if (smsMessageService.getProtocol().contains("SEND")) {
                            smsMessageService.setProtocol("SEND_ACK");

                            if(socketUtil.write(arrayList.report().))
                        }
                    }
                }else {
                    log.info("[응답] [messageSerivce] null임 ", messageService);
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
