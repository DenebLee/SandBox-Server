package kr.nanoit.server;

import kr.nanoit.config.Crypt;
import kr.nanoit.config.QueueList;
import kr.nanoit.dto.login.DecoderLogin;
import kr.nanoit.dto.login.Login_Packet_UserInfo;
import kr.nanoit.socket.Decoder;
import kr.nanoit.socket.SocketUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;

@Slf4j
public class ReceiveServer implements Runnable {

    private final Socket socket;
    private SocketUtil socketUtil;
    private QueueList queueList;
    private DecoderLogin decoderLogin;
    private Crypt crypt;
    private Login_Packet_UserInfo loginPacketUserInfo;
    private Decoder decorder;


    public ReceiveServer( Socket socket) throws IOException {
        socketUtil = new SocketUtil(socket);
        this.socket = socket;
        decoderLogin = new DecoderLogin();
        crypt = new Crypt();
        loginPacketUserInfo = new Login_Packet_UserInfo();
        queueList = new QueueList();
        decorder = new Decoder();

    }

    @SneakyThrows
    @Override
    public void run() {
        try {
            while (true) {
                byte[] receiveByte = socketUtil.receiveByte();
                if (receiveByte != null) {
                    switch (socketUtil.getPacketType(receiveByte)) {
                        case LOGIN:
                            decorder.decoderLogin(receiveByte);
                            break;
                        case SEND:
                            decorder.decoderSend(receiveByte);
                            break;
                    }
                    decorder.decoderLogin(receiveByte);
                }
            }

        } catch (Exception e) {
            log.error("IOException occurred", e);
            socket.close();
        }
    }
}


