package kr.nanoit.socket;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

@Slf4j
public class SocketConfig {


    
    /*
    각 Thread에서 작업할때 값을 Sync하기 위해
     */

    private final Object writeLock = new Object();
    private final Object readLock = new Object();
    private final Object closeLock = new Object();


    /*
    Stream 선언
     */

    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    @Getter
    private Socket socket;

    public void setSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.socket.setTcpNoDelay(true);
        this.socket.setKeepAlive(true);
        this.socket.setSoTimeout(60000);

        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataInputStream = new DataInputStream(socket.getInputStream());

    }

    public void socketClose() {
        synchronized (closeLock) {
            try {
                log.info("[NETWORK][SOCKET CLOSE]");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (socket != null) {
                    log.info("[NETWORK][SOCKET START]");

                    closeBufferedInputStream();
                    closeBufferedOutputStream();
                    closeSocket();

                    socket = null;
                    log.info("[NETWORK][SOCKET CLOSE]");
                }
            } catch (Exception e) {
                log.info("info Socket Close Fail... \n");
                e.printStackTrace();
            }
        }
    }

    private void closeSocket() {
        try {
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeBufferedOutputStream() {
        try {
            dataOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeBufferedInputStream() {
        try {
            dataInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
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

                    dataOutputStream.write(sendData, first, write);
                    dataOutputStream.flush();

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

    public byte[] read(final int wholeLength) throws Exception {
        synchronized (readLock) {
            byte[] readData = new byte[wholeLength];
            int sumOfBytesRead = 0;
            int numberOfBytesRead;

            while (wholeLength > sumOfBytesRead) {
                numberOfBytesRead = dataInputStream.read(readData, sumOfBytesRead, (wholeLength - sumOfBytesRead));
                if (numberOfBytesRead <= 0) {
                    log.info(String.format("[$WARNNING$][read error] End Of The Stream has been reached! return null! - Whole Length:%s, Sum Of Bytes Read:%s, Number Of Bytes Read:%s", wholeLength, sumOfBytesRead, numberOfBytesRead));
                    return null;
                } else {
                    sumOfBytesRead += numberOfBytesRead;
                }
            }
            return readData;
        }
    }
}
