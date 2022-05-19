package kr.nanoit.socket;

import kr.nanoit.dto.Packet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class TcpServerTest {
    private int port;
    private LinkedBlockingQueue<Packet> readStream;
    private TcpServer tcpServer;
    private Thread thread;

    @BeforeEach
    void setUp() throws IOException {
        port = getPort();
        readStream = new LinkedBlockingQueue<>();
        tcpServer = new TcpServer(readStream, port);
        thread = new Thread(tcpServer);
        thread.start();
    }

    private int getPort() {
        return new SecureRandom().nextInt(64000) + 1024;
    }

    @AfterEach
    void tearDown() throws IOException {
        tcpServer.stop();
    }

    @Test
    void Should_Parse_Header() throws IOException, InterruptedException {
        byte[] testByte = getTestBytes();

        Socket socket = new Socket("127.0.0.1", port);
        try (OutputStream outputStream = socket.getOutputStream()) {
            outputStream.write(testByte);
            outputStream.flush();
        }

        Packet packet = readStream.poll(5000L, TimeUnit.MILLISECONDS);

        assertTrue(packet.getHeader().getType().equals("SMS"));
        assertTrue(packet.getHeader().getBodySize() == 20);

        assertTrue(packet.getBody().getBody().equals("BODYYYYYY"));
    }

    private byte[] getTestBytes() {

        byte[] testByte = new byte[40];
        inputWhiteSpace(testByte);
        System.arraycopy("SMS".getBytes(StandardCharsets.UTF_8), 0, testByte, 0, "SMS".getBytes(StandardCharsets.UTF_8).length);
        System.arraycopy("20".getBytes(StandardCharsets.UTF_8), 0, testByte, 10, "10".getBytes(StandardCharsets.UTF_8).length);
        System.arraycopy("BODYYYYYY".getBytes(StandardCharsets.UTF_8), 0, testByte, 20, "BODYYYYYY".getBytes(StandardCharsets.UTF_8).length);
        return testByte;
    }

    private void inputWhiteSpace(byte[] testByte) {
        for (int i = 0; i < testByte.length; i++) {
            testByte[i] = ' ';
        }
    }
}