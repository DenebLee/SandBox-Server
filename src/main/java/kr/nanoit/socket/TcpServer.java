package kr.nanoit.socket;

import kr.nanoit.dto.Packet;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class TcpServer implements Runnable {
    private final LinkedBlockingQueue<Packet> readStream;
    private final ServerSocket serverSocket;
    private final Map<String, Thread> threadList;

    public TcpServer(LinkedBlockingQueue<Packet> readStream, int port) throws IOException {
        this.readStream = readStream;
        this.serverSocket = new ServerSocket(port);
        this.threadList = new HashMap<>();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket socket = serverSocket.accept(); // block
                log.info("[{}]<+[{}] connect", serverSocket.getLocalSocketAddress(), serverSocket.getInetAddress());
                Thread thread = new Thread(new SocketHandler(readStream, socket));
                threadList.put(serverSocket.getInetAddress().toString(), thread);
                thread.start();
            }
        } catch (IOException e) {
            log.error("socket server error occurred", e);
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
        threadList.forEach((s, thread) -> {
            log.info("Socket Key:{}", s);
            thread.interrupt();
        });
    }
}
