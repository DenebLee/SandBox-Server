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
    private String returnValue = null;

    public String validationValue(String id, String pw) throws IOException {
        StringBuilder sb = new StringBuilder();
        Properties prop = new Properties();
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        prop.load(is);
//        Pattern patt = Pattern.compile(id);
        Pattern patt2 = Pattern.compile(pw);

        for (Map.Entry<Object, Object> each : prop.entrySet()) {
//            final Matcher m = patt.matcher((String) each.getValue());
            final Matcher m2 = patt2.matcher((String) each.getValue());
            Properties readProp = readProperties.read("NanoitServer.properties");
            ServerInfo serverinfo = new ServerInfo();
            serverinfo.setIp(readProp.getProperty("tcp.server.port"));

            if (m2.find()) {
                serverinfo.setIp(readProp.getProperty("tcp.server.connect"));
                serverinfo.setPort(readProp.getProperty("tcp.server.port"));
                returnValue = new XmlParser().write(serverinfo);
                System.out.println(returnValue);
                sb.append(returnValue);
            }
        }
        return sb.toString();
    }
}

