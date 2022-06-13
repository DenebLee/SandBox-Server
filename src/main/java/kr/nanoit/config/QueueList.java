package kr.nanoit.config;

import kr.nanoit.dto.login.MessageService;
import lombok.Getter;

import java.util.concurrent.LinkedBlockingQueue;

public class QueueList {

    @Getter
    public LinkedBlockingQueue<MessageService> queue_for_Send;

    @Getter
    public LinkedBlockingQueue<MessageService> queue_for_Receive;

    public QueueList() {
        queue_for_Send = new LinkedBlockingQueue<>();
        queue_for_Receive = new LinkedBlockingQueue<>();
    }


}
