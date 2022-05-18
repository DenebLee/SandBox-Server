package kr.nanoit.main;

import kr.nanoit.dto.Packet;
import kr.nanoit.http.NanoItHttpServer;
import kr.nanoit.socket.TcpServer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 클라이언트가 http통신으로 서버에 아이디와 비번을 통해 xml을 요청하면 서버는 해당 요청 쿼리스트링을 검증후 xml을 보냄
 * xml을 파싱해서 소켓 접속 엔드포이트를 얻은 클라이언트는 서버와 socket연결을 시도
 * 서버는 socket Thread와 http용 Thread를 따로 두어 지속적인 요청을 받음
 */
@Slf4j
public class Main {

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");

    public static void main(String[] args) throws IOException {
        try {
            log.info("{}", String.format("[%s][HTTP SERVER][START]", SIMPLE_DATE_FORMAT.format(new Date())));

            NanoItHttpServer nanoItHttpServer = new NanoItHttpServer("0.0.0.0", 3000);
            nanoItHttpServer.start();

            LinkedBlockingQueue<Packet> readStream = new LinkedBlockingQueue<>();

            TcpServer tcpServer = new TcpServer(readStream, 9001);
            Thread thread = new Thread(tcpServer);
            thread.start();


            Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println(String.format("[%s][HTTP SERVER][STOP]", SIMPLE_DATE_FORMAT.format(new Date())))));
        } catch (IOException e) {
            log.error("IOException error occurred", e);
        }
    }
}
