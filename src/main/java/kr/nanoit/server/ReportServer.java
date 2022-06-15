//package kr.nanoit.server;
//
//import kr.nanoit.config.QueueList;
//import kr.nanoit.dto.messsage_Structure.MessageService;
//import kr.nanoit.dto.messsage_Structure.SMSMessageService;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import java.net.Socket;
//import java.util.concurrent.TimeUnit;
//import java.sql.Timestamp;
//
//@AllArgsConstructor
//@Slf4j
//public class ReportServer implements Runnable{
//
//    private Socket socket;
//    private QueueList queueList;
//
//    public void run(){
//        while (true) {
//            try {
//                MessageService messageService = queueList.getQueue_for_Report().poll(1000, TimeUnit.MICROSECONDS);
//                SMSMessageService smsMessageService =(SMSMessageService) messageService;
//                if (messageService != null) {
//                    if (messageService instanceof SMSMessageService) {
//                        log.info("[레포트] Agent에서 보낸 Packet 받음 받은시간 : {} DIFF? : {}" , smsMessageService.getTr_senddate(), ((System.currentTimeMillis() - smsMessageService.getTr_rsltdate().getTime() / 1000.0)));
//                    }
//                }
//
//
//
//
//
//
//
//
//
//
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//}
