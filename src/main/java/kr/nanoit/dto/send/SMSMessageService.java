package kr.nanoit.dto.send;

import kr.nanoit.dto.login.MessageService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class SMSMessageService implements MessageService {
    private static final long serialVersionUID = -6403618409502319022L;

    private MessageType messageServiceType;
    private String protocol;
    private String send_time;
    private String serverMessageId;

    private String tr_num;
    private Timestamp tr_senddate;
    private String tr_sendstat;
    private String tr_rsltstat;
    private String tr_msgtype;
    private String tr_phone;
    private String tr_callback;
    private String tr_rsltdate;
    private String tr_msg;
    private String tr_net;
    private String tr_realsenddate;
    private String tr_org_callback;
    private String tr_bill_id;

    @Override
    public String getMessageServiceType() {
        return messageServiceType.getProperty();
    }
}
