package kr.nanoit.socket;

import lombok.Getter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class SocketUtil {

    private BufferedInputStream bufferedInputStream;
    private BufferedOutputStream bufferedOutputStream;

    @Getter
    private Socket socket;

    public void setSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.socket.setTcpNoDelay(true);
        this.socket.setKeepAlive(true);
        this.socket.setSoTimeout(60000);

        bufferedInputStream = new BufferedInputStream(socket.getInputStream());
        bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
    }

}
