//package kr.nanoit.config;
//
//import com.sun.xml.internal.ws.api.message.Message;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Locale;
//import java.util.concurrent.TimeUnit;
//
//@Slf4j
//public class ServerThreadSend implements Runnable {
//    private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
//    private CommonSocketUtil commonSocketUtil;
//    private CommonEncoder commonEncoder;
//    private EncoderMessageService encoderMessageService;
//
//    public ServerThreadSend() {
//
//
//    }
//
//    @Override
//    public void run() {
//        log.info("SERVER SOCKET THREAD SEND CREATE");
//        while (true) {
//            try {
//                MessageService messageService = new commonSocketUtill.getSendQueue().poll(1000, TimeUnit.MILLISECONDS);
//                if (messageService != null) {
//                    LoginMessageService loginMessageService = (LoginMessageService) messageService;
//                    if (commonSocketUtil.writeByte(commonSocketUtil.login)) {
//                        log.info("[SENDER]  [LOGIN_ACK] ID:{} PW :{} VER : {} SUCCESS", loginMessageService.getId(), "LOGIN", loginMessageService.getPassword(), loginMessageService.getVersion());
//                    }else{
//                        log.info("[SENDER]  [LOGIN_ACK] ID:{} PW :{} VER : {} error", loginMessageService.getId(), "LOGIN", loginMessageService.getPassword(), loginMessageService.getVersion());
//                    }
//                } else if (messageService instanceof A)
//            }catch(IOException e){
//
//            }
//        }
//    }
//}
