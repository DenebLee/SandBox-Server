package kr.nanoit.login;

public class DecoderLogin {

    public String id(byte[] byteOfReceive) {
        return (new String(byteOfReceive, (IndexLogin.COMMON_INDEX_LOGIN_ID), 100)).trim();
    }


    public String password(byte[] byteOfReceive) {
        return (new String(byteOfReceive, (IndexLogin.COMMON_INDEX_LOGIN_PASSWORD), 100)).trim();
    }

    public String version(byte[] byteOfReceive) {
        return (new String(byteOfReceive, (IndexLogin.COMMON_INDEX_LOGIN_VERSION), 100)).trim();
    }
}