package kr.nanoit.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

@Slf4j
public class SocketConfig {

    private final Object writeLock = new Object();
    private final Object readLock = new Object();
    private final Object closeLock = new Object();

    private BufferedOutputStream bufferedOutputStream;
    private BufferedInputStream bufferedInputStream;
    private Socket socket;


    public SocketConfig() {
    }

    public void setSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.socket.setSoTimeout(60000);
        bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
        bufferedInputStream = new BufferedInputStream(socket.getInputStream());
    }


    public byte[] read(final int wholeLength) throws Exception {
        synchronized (readLock) {
            byte[] readData = new byte[wholeLength];
            int sumOfBytesRead = 0;
            int numberOfBytesRead;

            while (wholeLength > sumOfBytesRead) {
                numberOfBytesRead = bufferedInputStream.read(readData, sumOfBytesRead, (wholeLength - sumOfBytesRead));
                if (numberOfBytesRead <= 0) {
                    System.out.println(String.format("[$WARNNING$][read error] End Of The Stream has been reached! return null! - Whole Length:%s, Sum Of Bytes Read:%s, Number Of Bytes Read:%s", wholeLength, sumOfBytesRead, numberOfBytesRead));
                    return null;
                } else {
                    sumOfBytesRead += numberOfBytesRead;
                }
            }
            return readData;
        }
    }


    public boolean write(byte[] sendData) throws IOException {
        synchronized (writeLock) {
            try {
                int first = 0;
                int len;
                int write;
                len = sendData.length;

                while (len > 0) {
                    write = len;

                    bufferedOutputStream.write(sendData, first, write);
                    bufferedOutputStream.flush();
                    len -= write;
                    first += write;
                }
            } catch (Exception e) {
                e.printStackTrace();
                socket.close();
                return false;
            }
            return true;
        }
    }

    public void socketClose() {
        synchronized (closeLock) {
            try {
                log.info("소켓 연결 닫기");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
