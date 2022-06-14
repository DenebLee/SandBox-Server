package kr.nanoit.server;

import kr.nanoit.config.QueueList;
import kr.nanoit.dto.login.LoginMessageService;
import kr.nanoit.dto.login.MessageService;
import kr.nanoit.dto.send.ArrayList;
import kr.nanoit.dto.send.SMSMessageService;
import kr.nanoit.socket.SocketConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SendServer implements Runnable {
    private QueueList queueList;
    private SocketConfig socketUtil;
    private Socket socket;
    private ArrayList arrayList;



/**
* 다중 쓰레드가 사용중인 객체를 LOCK을 걸어 해당 작업을 진행하는 Thread가 작업을 마칠 때까지 다른 쓰레드가 작업을 못하게 하는 방법 : 
 * 메소드 선언에 synchronized를 붙이기 or (동기화 메소드) synchronized(공유객체) {작업}인 동기화 블록 사용하기
*/

    public SendServer(Socket socket) throws IOException {
        this.socket = socket;
        queueList = new QueueList();
        socketUtil = new SocketConfig(socket);
        arrayList = new ArrayList();
    }

    //  instanceof 객체가 어떤 클래스인지, 어떤 클래스를 상속받았는지 확인하는데 사용하는 연산자
    //   result T/F
    @Override
    public void run() {
        System.out.println("SenderServer is running right now");
        while (true) {
            try {
                MessageService messageService = queueList.getQueue_for_Send().poll(1000, TimeUnit.MICROSECONDS); // Queue안에 데이터도 null
                System.out.println("messageService "+  messageService);

                System.out.println("큐 데이터 테스트 " + queueList.getQueue_for_Send());

                if (messageService != null) { // 얘도 null 인데 어찌 돌아가는거지
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
//                            if(socketUtil.write(arrayList.report().))
                        }
                    }
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
