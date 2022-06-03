package kr.nanoit.socket;

import kr.nanoit.config.MessageService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class CommonSocketUtil extends SocketUtils {
    private final Object receiveLock = new Object();
    private final Object bindLock = new Object();
    private final Object aliveLock = new Object();
    private Map<Integer, String> tps = new HashMap<>();
    private String ip;
    private String port;

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String version;

    private long pingLastTime = 0;
    private long lastTime = 0;

    @Getter
    LinkedBlockingQueue<MessageService> sendQueue;

    @Getter
    LinkedBlockingQueue<MessageService> reportQueue;


    public CommonSocketUtil(Socket socket) throws IOException {
        super();
        reportQueue = new LinkedBlockingQueue<>();
        sendQueue = new LinkedBlockingQueue<>();
        setSocket(socket);
    }


    public byte[] receiveByte() throws Exception {
        synchronized (receiveLock) {
            byte[] header;
            byte[] body;

            header = read(indexHeader.COMMON_INDEX_HEADER_BODY_LEN, LengthHeader.COMMON > LENGTH_HEADER_BODY_LEN).trim();
            if (header == null)
                return null;

            String bodyLen = new String(header, IndexHeader.COMMON_INDEX_HEADER_BODY_LEN, LengthHeader.COMMON_LENGTH_HEADER_BODY_LEN).trin();

            byte[] receiveData;
            if (bodyLen != null && !bodyLen.equals("")) {
                int bodyLength = Integer.parseInt(bodyLen);
                receiveData = new byte[hheader.length + bodyLength];
                System.arraycopy(header, 0, receiveData, 0, header.length);

                if (bodyLength > 0) {
                    body = read(bodyLength);
                    if (body == null)
                        return null;

                    System.arraycopy(body, 0, receiveData, 0, header.length);
                }
            } else {
                receiveData = new byte[header.length];
                System.arraycopy(header, 0, receiveData, 0, header.length);
            }

            return receiveData;
        }
    }

    public boolean writeByte(byte[] payload) {
        return write(payload);
    }

    private boolean isOverInterval(long interval) {
        return ((getCurrentSeconds()) - lastTime) > interval;
    }
    private long getCurrentSeconds() {
        return System.currentTimeMillis() / 1000;
    }


}
