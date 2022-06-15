package kr.nanoit.config;

import kr.nanoit.dto.messsage_Structure.MessageService;
import lombok.Getter;

import java.util.concurrent.LinkedBlockingQueue;

public class QueueList{
    
    /**
    * 보내기 위한 큐
    */
    @Getter
    LinkedBlockingQueue<MessageService> queue_for_Send;

    /**
     * 응답 받기 위한 큐
     */

    @Getter
    LinkedBlockingQueue<MessageService> queue_for_Receive;

    /**
     * 응답 받기 위한 큐
     */

    @Getter
    LinkedBlockingQueue<MessageService> queue_for_Report;


    public QueueList() {
        queue_for_Send = new LinkedBlockingQueue<>();
        queue_for_Receive = new LinkedBlockingQueue<>();
    }
}
