package kr.nanoit.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The type Old server info.
 *
 * @author cho_jeong_ha
 * @project mock -server
 * @update 2019 -07-08
 */
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