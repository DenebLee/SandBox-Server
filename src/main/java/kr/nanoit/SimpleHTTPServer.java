package kr.nanoit;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleHTTPServer {

    private static final Logger LOGGER = Logger.getLogger("SimpleHTTPServer");

    private final byte[] content; // String data를 받아 인코딩에 맞게 byte[]배열된 데이터
    private final byte[] header; // String header가 인코딩에 맞게 byte[]배열로 변환
    private final int port;

    private final String encoding;

    public SimpleHTTPServer(String data, String encoding, String mimeType, int port) throws UnsupportedEncodingException{
        this(data.getBytes(encoding), encoding, mimeType, port);
    }


    public SimpleHTTPServer(byte[] data, String encoding, String mimeType, int port) {
        this.content = data;
        this.port = port;
        this.encoding = encoding;
        // Response Header:
        // 모든 데이터는 줄 끝마다 2바이트 CR LF ('\r\n')를 사용하여 플레인 텍스르 인코딩을 통해
        String header = "HTTP/1.0 200 OK\r\n"
                + "Server: SimpleHTTPServer 1.0\r\n"
                + "Content-length: " + this.content.length + "\r\n"
                + "Content-type: " + mimeType + "; charset=" + encoding + "\r\n\r\n";
        this.header = header.getBytes(Charset.forName("US-ASCII"));
    }

    public void start() throws IOException {
        try (ServerSocket server = new ServerSocket(this.port)) {
            // 로그
            LOGGER.setLevel(Level.INFO);
            LOGGER.info("Accepting connections on port " + server.getLocalPort());
            LOGGER.info("Data to be sent:");
            LOGGER.info(new String(this.content, encoding));


            while (true) {
                try {

                    Socket socket = server.accept(); // 사용자가 f5눌러서 요청할때까지 block중
                    HttpHandler handler = new HttpHandler(socket);
                    new Thread(handler).start();

                } catch (IOException ex) {
                    LOGGER.log(Level.WARNING, "Exception accepting connection", ex);
                } catch (RuntimeException  ex) {
                    LOGGER.log(Level.SEVERE, "Unexpected error", ex);
                }
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Could not start server", ex);
        }
    } // start


    // 쓰레드를 이용하기 위해 Runnabled을 구현한 내부 클래스 정의
    // http의 사용자가 요청 처리 부분이 별도의 스레드로 실행
    private class HttpHandler implements Runnable{
        private final Socket socket;

        public HttpHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {

                OutputStream os = new BufferedOutputStream(socket.getOutputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader((socket.getInputStream())));

                StringBuilder request = new StringBuilder();
                while (true) {
                    String str = br.readLine();
                    if (str.equals("")) break; // 16-2. 빈줄이 포함되었으면 while문 벗어남 -> empty line
                    request.append(str + "\n"); // 16-3. 읽는 족족 request 헤더에 추가중
                }
                // 17. 브라우저(사용자)가 보낸 request 헤더 출력
                // GET / HTTP/1.1
                //     └─▶ /: 우리 서버는 localhost/ddd.aaaa 해도 무조건 test.html만 보내주게 되어있음 --> Run Configuration에서 설정해둠
                System.out.println("요청헤더:\n" + request.toString());

                // HTTP/1.0 이나 그 이후의 버전을 지원할 경우 MIME 헤더를 전송한다.
                if (request.toString().indexOf("HTTP/") != -1) {
                    os.write(header);
                    // GET / HTTP/1.1
                    //     └─▶ /: 우리 서버는 localhost/ddd.aaaa 해도 무조건 test.html만 보내주게 되어있음
                }
                System.out.println("응답헤더:\n" + new String(header));

                os.write(content);
                os.flush();
            } catch (IOException ex) {
                LOGGER.log(Level.WARNING, "Error writing to client", ex);
            } finally {
                try { // 여기 왜 주석한거지?
                socket.close(); // 소켓 닫기(연결 끊기)
                } catch (IOException e) {
                e.printStackTrace();
            }
            }
        } // run()
    }// HTTPHandler

    public static void main(String[] args) {
        int port;
        try {
            port = Integer.parseInt(args[1]);
            if (port < 1 || port > 65535) port = 80;
        } catch (RuntimeException ex) {
            port = 80;
        }

        FileInputStream fs = null;
        try {
            // 파일 출력을 위해 파일 읽기
            // Path path = Paths.get(args[0]);
            // byte[] data = Files.readAllBytes(path);
            File file = new File(args[0]);
            byte[] data = new byte[(int) file.length()];
            fs = new FileInputStream(file);
            fs.read(data);

            // 해당 파일 이름을 이용해 Content-type 정보 추출하기 -> Content Type = MIME Type = 파일 타입
            String contentType = URLConnection.getFileNameMap().getContentTypeFor(args[0]); //
            System.out.println(" contentType => " + contentType);

//            서버 객체를 생성하여 start
            SimpleHTTPServer server = new SimpleHTTPServer(data, encoding, contentType, port);
            server.start();

        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Usage: java SimpleHTTPServer filename port encoding");
        } catch (IOException ex) {
            LOGGER.severe("IO Error: " + ex.getMessage());
        } finally {
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }// main()


} // class
