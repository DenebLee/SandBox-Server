import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

class Client_for_Test {

    @Test
    @DisplayName("Server Test")
        // 첫 번째 파라미터 : 접속할려는 상대편 프로그램의 컴퓨터 주소

    void TestClient() {
        Socket socket = null;
        try {
            //>java Client1 172.16.83.100
            socket = new Socket();
            SocketAddress address = new InetSocketAddress(8888);
            socket.connect(address);
            System.out.println("[접속] 서버 :" + socket.getInetAddress().getHostAddress());
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = reader.readLine();
            String[] movie = line.split("/");
            for (int idx = 0; idx < movie.length; idx++) {
                System.out.println(movie[idx]);
            }
        } catch (Exception e) {
            System.out.println("서버문제발생: " + e);
        } finally {
            try {
                socket.close();
            } catch (Exception e) {

            }
            System.out.println("클라이언트 프로그램 실행 중: ");
        }
    }
}
