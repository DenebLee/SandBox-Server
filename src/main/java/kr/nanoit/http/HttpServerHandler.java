package kr.nanoit.http;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import kr.nanoit.config.Crypt;
import kr.nanoit.config.XmlMaker;
import kr.nanoit.main.Main;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;

/**
 * Stringbuiler는 String과 문자열을 더할 때 새로운 객체를 생성하는 것이 아니라 기존의 데이터에 더하는 방식을
 * 사용하기 때문에 속도도 빠르고 부하가 적다
 * <p>
 * 여기 부분에 클라이언트가 요청한 쿼리스트링에 암호화된 비번과 아이디가 일치한다면 respBody에 xml형식으로
 * socket연결 할 엔드포인트 제공
 * 아닐 시 접속 종료
 * <p>
 * response에는 헤더값과 바디값
 * 헤더값은 서버에서 설정한 자릿수에 맞춰 작성후 클라이언트가 요청한 리퀘스트에서 자리값이 틀릴 시 연결종료
 * 버퍼 리더에 데이터가 쌓이면 한줄씩 빼야된다
 */
@Slf4j
public class HttpServerHandler implements HttpHandler {

    private XmlMaker xmlMaker;

    public HttpServerHandler() {
        xmlMaker = new XmlMaker();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        OutputStream respBody = exchange.getResponseBody();

        try {
            log.info("Addresses accessed by clients [{}]", exchange.getLocalAddress());
            Map<String, String> result = getQueryParameters(exchange.getRequestURI().getQuery());
            String id = result.get("id");
            String password = result.get("password");

            Crypt crypt = new Crypt();
            try {
                if (id != null && password != null) {
                    if (Main.valificationMap.containsKey(id)) {
                        crypt.cryptInit(Main.valificationMap.get(id).getEncryptKey());
                        try {
                            password = new String(crypt.deCrypt(password));
                        } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
                            e.printStackTrace();
                        }
                    }
                    if (password.equals(Main.valificationMap.get(id).getPassword())) {
                        log.info("REQUESTED PASSWORD IS CORRECT");
                        log.info("REQUESTED ID : [{}] , PASSWORD : [{}]" , id, password);
                        byte[] body = xmlMaker.XmlMake().getBytes(StandardCharsets.UTF_8);
                        Headers headers = exchange.getResponseHeaders();
                        headers.add("Content-Type", "application/xml");
                        exchange.getRequestURI();
                        exchange.sendResponseHeaders(200, body.length);
                        respBody.write(body);
                        respBody.close();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            exchange.close();
        }
    }

    public Map<String, String> getQueryParameters(String query) {
        Map<String, String> result = new HashMap<>();
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
