package kr.nanoit.dto.login;

public class IndexHeader {
    /**
     * The constant COMMON_INDEX_HEADER_BODY_LEN.
     */
    public static int COMMON_INDEX_HEADER_BODY_LEN = LengthHeader.COMMON_LENGTH_HEADER_PACKET_TYPE_INDEX + LengthHeader.COMMON_LENGTH_HEADER_PACKET_TYPE;
    /**
     * The constant COMMON_INDEX_HEADER_FULL_LENGTH.
     */
    public static int COMMON_INDEX_HEADER_FULL_LENGTH = COMMON_INDEX_HEADER_BODY_LEN + LengthHeader.COMMON_LENGTH_HEADER_BODY_LEN;

}