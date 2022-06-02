package kr.nanoit.config;

import java.io.Serializable;

/**
 * The interface Message service.
 *
 * @author cho_jeong_ha
 * @project Nanoit -Integration-Agent
 * @update 2019 -07-03
 */
public interface MessageService extends Serializable {
    /**
     * Gets message service type.
     *
     * @return the message service type
     */
    String getMessageServiceType();

    /**
     * Gets protocol.
     *
     * @return the protocol
     */
    String getProtocol();
}
