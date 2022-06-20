package kr.nanoit.config;

import kr.nanoit.main.SandBoxServer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XmlMaker {

    private final ServerInfo serverInfo;
    private final StringBuilder stringBuilder;
    private String returnValue = null;


    public XmlMaker() {
        serverInfo = new ServerInfo();
        this.stringBuilder = new StringBuilder();
    }

    public String XmlMake() {
        serverInfo.setIp(SandBoxServer.configuration.getString("tcp.server.host"));
        serverInfo.setPort(SandBoxServer.configuration.getString("tcp.server.port"));
        returnValue = new XmlParser().write(serverInfo);
        System.out.println("--------------------------------------------------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------------------------------------------------");
        log.info("Client에게 보낸 XML {} ", returnValue);
        System.out.println("--------------------------------------------------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------------------------------------------------");
        stringBuilder.append(returnValue);
        return returnValue;
    }
}

