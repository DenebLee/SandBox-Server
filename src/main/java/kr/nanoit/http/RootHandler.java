package kr.nanoit.http;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Stringbuiler는 String과 문자열을 더할 때 새로운 객체를 생성하는 것이 아니라 기존의 데이터에 더하는 방식을
 * 사용하기 때문에 속도도 빠르고 부하가 적다
 *
 * 여기 부분에 클라이언트가 요청한 쿼리스트링에 암호화된 비번과 아이디가 일치한다면 respBody에 xml형식으로
 * socket연결 할 엔드포인트 제공
 * 아닐 시 접속 종료
 *
 * response에는 헤더값과 바디값
 * 헤더값은 서버에서 설정한 자릿수에 맞춰 작성후 클라이언트가 요청한 리퀘스트에서 자리값이 틀릴 시 연결종료
 * 버퍼 리더에 데이터가 쌓이면 한줄씩 빼야된다
 */
@Slf4j
public class RootHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        OutputStream respBody = exchange.getResponseBody();
        String id_check = "123123";
        try {
            System.out.println(exchange.getRequestURI());
            Map<String, String> result = getQueryParameters(exchange.getRequestURI().getQuery());
            String id = result.get("id");
            String pw = result.get("pw");
            System.out.println(id);
            System.out.println(pw);

            StringBuilder sb = new StringBuilder();

            try {
                if (id.equals(id_check)) {
                    sb.append("<ServerInfo>");
                    sb.append(System.getProperty("line.separator"));
                    sb.append("  <IP>192.168.0.1</IP>");
                    sb.append(System.getProperty("line.separator"));
                    sb.append("  <Port>9001</Port>");
                    sb.append(System.getProperty("line.separator"));
                    sb.append("</ServerInfo>");
                } else if (!id.equals(id_check)) {
                    sb.append("The requested ID and password do not match");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            byte[] body = sb.toString().getBytes(StandardCharsets.UTF_8);

            Headers headers = exchange.getResponseHeaders();
            headers.add("Content-Type", "application/xml");
            System.out.println();
            System.out.println();
            System.out.println(new String(body));
            exchange.getRequestURI();

            exchange.sendResponseHeaders(200, body.length);
            respBody.write(body);
            respBody.close();
        } catch (IOException e) {
            e.printStackTrace();

            if (respBody != null) {
                respBody.close();
            }
        } finally {
            exchange.close();
        }
    }

    public Map<String, String> getQueryParameters(String query) {
        Map<String, String> result = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length > 1) {
                result.put(pair[0], pair[1]);
            } else {
                result.put(pair[0], "");
            }
        }
        return result;
    }
}
