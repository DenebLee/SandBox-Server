package kr.nanoit.dto.login;

import java.io.Serializable;

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
