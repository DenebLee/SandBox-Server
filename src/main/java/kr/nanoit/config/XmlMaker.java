package kr.nanoit.config;

import kr.nanoit.main.Main;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XmlMaker {

    private ServerInfo serverInfo;
    private String returnValue = null;


    public XmlMaker() {
        serverInfo = new ServerInfo();
    }

    public String XmlMake() {
        StringBuilder sb = new StringBuilder();
        serverInfo.setIp(Main.configuration.getString("tcp.server.host"));
        serverInfo.setPort(Main.configuration.getString("tcp.server.port"));
        returnValue = new XmlParser().write(serverInfo);
        System.out.println("--------------------------------------------------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------------------------------------------------");
        log.info("Client에게 보낸 XML {} ", returnValue);
        System.out.println("--------------------------------------------------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------------------------------------------------");
        sb.append(returnValue);
        return returnValue;
    }
}

