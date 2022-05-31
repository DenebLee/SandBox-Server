package kr.nanoit.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@ToString
@XmlRootElement(name = "ServerInfo")
public class ServerInfo {
    /**
     * The Ip.
     */
    @XmlElement(name = "ip")
    String ip;
    /**
     * The Port.
     */
    @XmlElement(name = "port")
    String port;
}
