package kr.nanoit.dto.login;

import lombok.Getter;
import lombok.Setter;

public class Login_Packet_UserInfo {

    @Getter
    @Setter
    private String packet_login_id;

    @Getter
    @Setter
    private String packet_login_password;

    @Getter
    @Setter
    private String packet_login_version;


}
