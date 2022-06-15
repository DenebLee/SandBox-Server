package kr.nanoit.dto.login;

import kr.nanoit.dto.messsage_Structure.IndexHeader;

public class IndexLogin {
    /**
     * The constant COMMON_INDEX_LOGIN_ID.
     */
    public static int COMMON_INDEX_LOGIN_ID = IndexHeader.COMMON_INDEX_HEADER_FULL_LENGTH;
    /**
     * The constant COMMON_INDEX_LOGIN_PASSWORD.
     */
    public static int COMMON_INDEX_LOGIN_PASSWORD = COMMON_INDEX_LOGIN_ID + LengthLogin.COMMON_LENGTH_LOGIN_ID;
    /**
     * The constant COMMON_INDEX_LOGIN_VERSION.
     */
    public static int COMMON_INDEX_LOGIN_VERSION = COMMON_INDEX_LOGIN_PASSWORD + LengthLogin.COMMON_LENGTH_LOGIN_PASSWORD;
    /**
     * The constant COMMON_INDEX_LOGIN_FULL_LENGTH.
     */
    public static int COMMON_INDEX_LOGIN_FULL_LENGTH = COMMON_INDEX_LOGIN_VERSION + LengthLogin.COMMON_LENGTH_LOGIN_VERSION;
}