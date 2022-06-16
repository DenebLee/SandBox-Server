package kr.nanoit.config;

import lombok.extern.slf4j.Slf4j;

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
            final Matcher matcher = patt.matcher((String) each.getValue());
            if (matcher.find()) {
                return true;
            }
        }
        return false;
    }
}


