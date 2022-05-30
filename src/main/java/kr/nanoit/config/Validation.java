package kr.nanoit.config;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Slf4j
public class Validation {

    private String fileName = "NanoitServer.properties";

    public String validationValue(String id , String pw) throws IOException {
        StringBuilder sb = new StringBuilder();

        Properties prop = new Properties();
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        prop.load(is);
        Pattern patt = Pattern.compile(id);
        Pattern patt2 = Pattern.compile(pw);

        for (Map.Entry<Object, Object> each : prop.entrySet()) {
            final Matcher m = patt.matcher((String) each.getValue());
            final Matcher m2 = patt2.matcher((String) each.getValue());

            if (m2.find() ) {
                ServerInfo serverInfo = new ServerInfo();
                serverInfo.setPort(prop.getProperty("auth.server.port"));
                serverInfo.setIp(prop.getProperty("auth.id.2"));
                log.info("아이디, 비밀번호 검증 완료");
                sb.append("<ServerInfo>");
                sb.append(System.getProperty("line.separator"));
                sb.append("  <IP>192.168.0.1</IP>");
                sb.append(System.getProperty("line.separator"));
                sb.append("  <Port>9001</Port>");
                sb.append(System.getProperty("line.separator"));
                sb.append("</ServerInfo>");
                log.info("Responses to be sent to clients XML");
            }else {
                sb.append("Data is not correct");
            }
        }

        return sb.toString();
    }
}
