import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

class Client_for_Test {

    String returnData = "";

    @Test
    @DisplayName("server Test")
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
    
    @Test
    @DisplayName("HttpServer Test")
    void HttpClient_Test() throws IOException {
        try {
            URL url = new URL("http://localhost:3000?id=3fefefe");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();


            conn.connect();
            System.out.println("[HTTP 요청 방식] : " + "POST");
            System.out.println("[HTTP 요청 주소] : " + url);


            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuffer sb = new StringBuffer();

            returnData = sb.toString();
            String responseData = "";

            while ((responseData = br.readLine()) != null) {
                sb.append(responseData); //StringBuffer에 응답받은 데이터 순차적으로 저장 실시
            }
            returnData = sb.toString();

            //http 요청 응답 코드 확인 실시
            String responseCode = String.valueOf(conn.getResponseCode());
            System.out.println("[http 응답 코드] : " + responseCode);
            System.out.println("[http 응답 데이터] : " + returnData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
