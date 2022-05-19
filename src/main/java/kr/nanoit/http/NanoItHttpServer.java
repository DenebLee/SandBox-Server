package kr.nanoit.http;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class NanoItHttpServer {
    private final HttpServer httpServer;

    public NanoItHttpServer(String host, int port) throws IOException {
        this.httpServer = HttpServer.create(new InetSocketAddress(host, port), 0);
        httpServer.createContext("/", new RootHandler());
    }

    public void start() {
        httpServer.start();
    }

    public void stop(int delay) {
        httpServer.stop(delay);
    }
}
