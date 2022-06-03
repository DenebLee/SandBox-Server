package kr.nanoit.socket;

import lombok.Getter;
import lombok.Setter;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

@Slf4j
public class SocketUtils {

    private final Object writeLock = new Object();
    private final Object readLock = new Object();
    private final Object closeLock = new Object();

    private BufferedInputStream bufferedInputStream;
    private BufferedOutputStream bufferedOutputStream;

    @Getter
    private Socket socket;

    @Getter
    @Setter
    private boolean isBind = false;

    @Getter
    private String localIp;

    @Getter
    private int localPort;


    public SocketUtils() {

    }

    public void setSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.socket.setTcpNoDelay(true);
        this.socket.setKeepAlive(true);
        this.socket.setSoTimeout(600000);

        bufferedInputStream = new BufferedInputStream(socket.getInputStream());
        bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());

        localIp = socket.getLocalAddress().toString();
        localPort = socket.getLocalPort();
    }

    public boolean write(byte[] sendData) {
        synchronized (writeLock) {
            int unit = 512;

            try {
                int startLen = 0, len, write;
                len = sendData.length;

                while (len > 0) {
                    if (len < unit) {
                        write = len;
                    } else {
                        write = unit;

                        bufferedOutputStream.write(sendData, startLen, write);
                        bufferedOutputStream.flush();

                        len -= write;
                        startLen += write;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                socketClose();
                return false;
            }
            return true;
        }
    }

    public byte[] read(final int wholeLength) throws Exception {
        synchronized (readLock) {
            byte[] readData = new byte[wholeLength];
            int sumOfBytesRead = 0;
            int numberOfBytesRead;
            while (wholeLength > sumOfBytesRead) {
                numberOfBytesRead = bufferedInputStream.read(readData, sumOfBytesRead, (wholeLength - sumOfBytesRead));
                if (numberOfBytesRead <= 0) {
                    log.info("[$WARNNING$][read error] End Of The Stream has been reached! return null! - Whole Length:{}, Sum Of Bytes Read:{}, Number Of Bytes Read:{}", wholeLength, sumOfBytesRead, numberOfBytesRead);
                    return null;
                }else{
                    sumOfBytesRead += numberOfBytesRead;
                }
            }
            return readData;
        }
    }

    public void socketClose(){
        synchronized (closeLock) {
            try {
                log.info("[NETWORK][SOCKET CLOSE]");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (socket != null) {
                    log.info("[NETWORK][SOCKET START]");

                    retryShutDownIO();
                    closeBufferedInputStream();
                    closeBufferedOutputStream();
                    closeSocket();

                    socket = null;
                    setBind(false);
                    log.info("[NETWORK][SOCKET CLOSE");
                }

                localIp = "";
                localPort = 0;
            } catch (Exception e) {
                log.info("info Socket Close Fail...\n");
                e.printStackTrace();
            }
        }
    }
    private void closeSocket(){
        try {
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeBufferedOutputStream() {
        try {
            bufferedOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void closeBufferedInputStream(){
        try {
            bufferedInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void retryShutDownIO() throws InterruptedException{
        int loop = 0;
        while (((!socket.isOutputShutdown()) || (!socket.isInputShutdown() && (loop++ < 3)))) {
            try{
                if (!socket.isInputShutdown())
                    socket.shutdownInput();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (!socket.isOutputShutdown())
                    socket.shutdownOutput();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.sleep(200);
        }
    }



}
