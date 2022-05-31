package kr.nanoit.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginMessageService implements MessageService {
    private String messageServicetype;
    private String protocol;
    private String id;
    private String password;
    private String version;

    @Override
    public String getMessageServiceType() {
        return null;
    }

    @Override
    public String getProtocol(){ return protocol;}
}
