package kr.nanoit;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;
import javax.xml.ws.spi.http.HttpHandler;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;

public class HttpServerModule {
    public HttpServerModule(){

        HttpServer server = null;
        Integer listenPort = 12339; //동작시킬 포트 번호

        try{
            server = HttpServer.create(new InetSocketAddress(listenPort), 0);
            server.createContext("/test", new testHandler()); //컨텍스트 명
            server.start(); //httpServer 시작
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public class testHandler implements HttpHandler {
        public void handle(HttpExchange t){ //test 컨텍스트로 입력될 경우 처리
            String inputLine = "";
            String resultStr = "";

            try{
                System.out.println("testHandler");
                String reqMethod = t.getRequestMethod();
                InputStream in = t.getRequestBody();
                BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

                StringBuffer response = new StringBuffer();
                while((inputLine = br.readLine()) != null){
                    response.append(inputLine);
                }
                String resStr = response.toString();

                System.out.println(resStr);

                Headers h = t.getResponseHeaders();
                h.add("Content-Type", "application/json;charset=UTF-8");
                h.add("Access-Control-Allow-Origin", "*");

                t.sendResponseHeaders(200, 0);`
                OutputStreamWriter osw = new OutputStreamWriter(t.getResponseBody(), "UTF-8");
                osw.write("Http response");

                try{
                    if(osw != null)
                        osw.close();

                    t.close();
                }catch(Exception e){
                    e.printStackTrace();
                }finally{

                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}