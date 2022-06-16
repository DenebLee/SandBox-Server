package kr.nanoit.server;

import kr.nanoit.dto.messsage_Structure.MessageService;
import kr.nanoit.dto.messsage_Structure.SMSMessageService;
import kr.nanoit.socket.SocketUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ReportServer implements Runnable{

    private SocketUtil socketUtil;

        public ReportServer(SocketUtil socketUtil) {this.socketUtil = socketUtil;}

    public void run(){
        while (true) {
            try {
                MessageService messageService = socketUtil.getQueue_for_Report().poll(1000, TimeUnit.MICROSECONDS);
                SMSMessageService smsMessageService =(SMSMessageService) messageService;
                if (messageService != null) {
//                        (System.currentTimeMillis() - smsMessageService.getTr_rsltdate().getTime() / 1000.0))
                        log.info("[레포트] Agent에서 보낸 Packet 받음 받은시간 : {} DIFF : {}", smsMessageService.getTr_senddate(), ((System.currentTimeMillis() - smsMessageService.getTr_senddate().getTime()) / 1000.0));
                        smsMessageService.setProtocol("REPORT");
                        smsMessageService.setTr_rsltdate(new Timestamp(System.currentTimeMillis()).toString());
                        Thread.sleep(3000);
                    socketUtil.getQueue_for_Send().offer(smsMessageService);

                }

            } catch (Exception e) {
                e.printStackTrace();
                socketUtil.socketClose();
            }
        }

    }
}
