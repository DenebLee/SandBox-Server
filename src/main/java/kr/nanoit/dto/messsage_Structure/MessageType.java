package kr.nanoit.dto.messsage_Structure;

public enum MessageType {

    SMS("SMS"),

    NONE("NONE");

    private String property;

    MessageType(String property) {
        this.property = property;
    }

    /**
     * From property name message type.
     *
     * @param x the x
     * @return the message type
     */
    public static MessageType fromPropertyName(String x) {
        for (MessageType currentType : MessageType.values()) {
            if (x.equals(currentType.getProperty())) {
                return currentType;
            }
        }
        return NONE;
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

    /**
     * Gets hash code.
     *
     * @return the hash code
     */
    public int getHashCode() {
        return property.hashCode();
    }

    @Override
    public String toString() {
        return property;
    }
}
