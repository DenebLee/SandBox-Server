//package kr.nanoit.socket;
//
//import kr.nanoit.config.MessageService;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.io.IOException;
//import java.net.Socket;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.LinkedBlockingQueue;
//
//public class CommonSocketUtil extends SocketUtils {
//    private final Object receiveLock = new Object();
//    private final Object bindLock = new Object();
//    private final Object aliveLock = new Object();
//    private Map<Integer, String> tps = new HashMap<>();
//    private String ip;
//    private String port;
//
//    @Getter
//    @Setter
//    private String id;
//
//    @Getter
//    @Setter
//    private String password;
//    @Getter
//    @Setter
//    private String version;
//
//    private long pingLastTime = 0;
//    private long lastTime = 0;
//
//    @Getter
//    LinkedBlockingQueue<MessageService> sendQueue;
//
//    @Getter
//    LinkedBlockingQueue<MessageService> reportQueue;
//
//
//
//    public CommonSocketUtil(Socket socket) throws IOException {
//        super();
//        reportQueue = new LinkedBlockingQueue<>();
//        sendQueue = new LinkedBlockingQueue<>();
//        setSocket(socket);
//    }
//
//
//    public byte[] receiveByte() throws Exception {
//        synchronized (receiveLock) {
//            byte[] header;
//            byte[] body;
//
//
//
//        }
//    }
//
//}
