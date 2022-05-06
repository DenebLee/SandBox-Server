package kr.nanoit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public static void main(String[] args) throws IOException {
        System.out.println("서버 실행중");

        // 서버 소켓 만들기
        ServerSocket serverSocket = new ServerSocket(8888);
        
        // 대기열의 첫 번째 클라이언트의 접속을 승인
        // -> 만약 대기 중인 클라이언트가 없다면, 리턴하지 않고 접속할 때까지 기다린다.
        // -> 여러 클라이언트가 대기 중이라면 그 중 첫 번째 클라이언트와 연결
        // -> 리턴 값 : 대기열에 등록된 첫 번째 클라이언트의 연결 정보를 가지고 만든 소켓 객체

        Socket socket = serverSocket.accept();

        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();
        // 소켓으로 클라이언트와 연결되면 데이터를 주고 받는 순서는 상관없다.
        // 그러나 일반적으로 클라이언트에서 먼저 데이터를 전송
        // 서버는 클라이언트에서 데이터를 먼저 읽고 그에 대한 응답을 한다
        int read = is.read();

        System.out.printf("%x\n", read);

        os.write(0x01);


        is.close();
        os.close();
        socket.close();
        serverSocket.close();
    }
}
