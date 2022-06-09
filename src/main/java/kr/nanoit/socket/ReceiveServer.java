package kr.nanoit.socket;

import kr.nanoit.dto.Body;
import kr.nanoit.dto.Header;
import kr.nanoit.dto.NanoItPacket;
import kr.nanoit.dto.Packet;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class ReceiveServer implements Runnable {

    private final LinkedBlockingQueue<Packet> readStream;
    private final Socket socket;
    private final DataOutputStream dataOutputStream;
    private final DataInputStream dataInputStream;

    public ReceiveServer(LinkedBlockingQueue<Packet> readStream, Socket socket)throws IOException {
        this.readStream = readStream;
        this.socket = socket;
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.dataInputStream = new DataInputStream(socket.getInputStream());
    }
    @SneakyThrows
    @Override
    public void run() {
        try {
            while (true) {
                log.trace("received");
                // header
                byte[] byteOfHeaders = new byte[20];
                int countOfHeader = dataInputStream.read(byteOfHeaders);

                if (countOfHeader != 20) {
                    log.error("[{}] {} count, header is not received 20 bytes", new String(byteOfHeaders), countOfHeader);
                }

                byte[] type = new byte[10];
                System.arraycopy(byteOfHeaders, 0, type, 0, 10);
                byte[] bodySize = new byte[10];
                System.arraycopy(byteOfHeaders, 10, bodySize, 0, 10);

                int intOfBodySize = Integer.parseInt(new String(bodySize).trim());

                Header header = Header.builder()
                        .type(new String(type).trim())
                        .bodySize(intOfBodySize)
                        .build();

                // body
                byte[] bytesOfBody = new byte[intOfBodySize];
                int countOfBody = dataInputStream.read(bytesOfBody);

                if (countOfBody != intOfBodySize) {
                    log.error("[{}] {} count, header is not received 20 bytes", new String(byteOfHeaders), countOfHeader);
                }

                Packet packet = NanoItPacket.builder()
                        .header(header)
                        .body(Body.builder().body(new String(bytesOfBody).trim()).build())
                        .build();

                if (readStream.offer(packet)) {
                    log.info("readStream put success {}", new String(byteOfHeaders));
                } else {
                    log.error("readStream put fail {}", new String(byteOfHeaders));
                }
            }
        } catch (Exception e) {
            log.error("IOException occurred", e);
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        }
    }
}
