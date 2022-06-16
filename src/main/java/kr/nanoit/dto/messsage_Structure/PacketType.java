package kr.nanoit.dto.messsage_Structure;

public enum PacketType {

    LOGIN("LOGIN"),

    LOGIN_ACKNOWLEDGEMENT("LOGIN_ACK"),

    SEND("SEND"),

    SEND_ACKNOWLEDGEMENT("SEND_ACK"),

    REPORT("REPORT"),

    REPORT_ACKNOWLEDGEMENT("REPORT_ACK"),

    ALIVE("ALIVE"),

    ALIVE_ACKNOWLEDGEMENT("ALIVE_ACK");

    private final String property;

    PacketType(String property) {
        this.property = property;
    }

    /**
     * From property name packet type.
     *
     * @param x the x
     * @return the packet type
     * @throws Exception the exception
     */
    public static PacketType fromPropertyName(String x) throws Exception {
        for (PacketType currentType : PacketType.values()) {
            if (x.equals(currentType.getProperty())) {
                return currentType;
            }
        }
        throw new Exception("Unmatched Type: " + x);
    }

    /**
     * Gets property.
     *
     * @return the property
     */
    public String getProperty() {
        return property;
    }

    /**
     * Get bytes byte [ ].
     *
     * @return the byte [ ]
     */
    public byte[] getBytes() {
        return property.getBytes();
    }

    @Override
    public String toString() {
        return property;
    }
}
