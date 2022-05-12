package kr.nanoit.socket;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    public static void main(String[] args) {

        ServerSocket server = null;

        try {

            server = new ServerSocket(8888);
            System.out.println("\n클라이언트 접속 대기중 ....");

            while (true) {

                Socket socket = server.accept();
                System.out.println("[접속]접속 IP : " + socket.getInetAddress().getHostAddress());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                writer.write("안녕하세요");
                writer.newLine();
                writer.flush();

                socket.close();

            } // while end

        } catch (Exception e) {
            System.out.println("서버문제발생 : " + e);
        }finally {
            try {

            } catch (Exception e) {
                System.out.println("서버 프로그램 실행 종료!!");
            }
        }
    }
}
