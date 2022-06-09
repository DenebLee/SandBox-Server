package kr.nanoit.config;

import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

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
        return returnValue;
    }
}

