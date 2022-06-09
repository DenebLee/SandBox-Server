package kr.nanoit.config;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class XmlMaker {

    private String returnValue = null;

    public String XmlMake() {
        StringBuilder sb = new StringBuilder();
        Properties readProp = readProperties.read();
        ServerInfo serverinfo = new ServerInfo();
        serverinfo.setIp(readProp.getProperty("tcp.server.port"));
        serverinfo.setIp(readProp.getProperty("tcp.server.connect"));
        serverinfo.setPort(readProp.getProperty("tcp.server.port"));
        returnValue = new XmlParser().write(serverinfo);
        System.out.println(returnValue);
        sb.append(returnValue);
        return sb.toString();
    }
}

