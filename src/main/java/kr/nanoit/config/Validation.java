package kr.nanoit.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.Configuration;

import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Validation {

    private String pw;

    public boolean Validation_PW(String pw) {
        Properties readProp = readProperties.read();
        Pattern patt = Pattern.compile(pw);

        for (Map.Entry<Object, Object> each : readProp.entrySet()) {
//            final Matcher m = patt.matcher((String) each.getValue());
            final Matcher matcher = patt.matcher((String) each.getValue());
            ServerInfo serverinfo = new ServerInfo();
            serverinfo.setIp(readProp.getProperty("tcp.server.port"));

            if (matcher.find()) {
                log.info("[Validation] validation password success");
                return true;
            }else{
                log.info("[Validation] validation password false");
            }
        }
        return false;
    }
}


