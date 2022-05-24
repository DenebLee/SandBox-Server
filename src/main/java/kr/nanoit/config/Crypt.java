package kr.nanoit.config;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;

public class Crypt {
    // NOTE: STATIC
    public static final String AES_CBC_PKCS_5_PADDING = "AES/CBC/PKCS5PADDING";
    public static final String ALGORITHM_AES = "AES";
    public static final int KEY_MAX_LENGTH = 16;

    // NOTE: CONSTRUCTOR
    private final String encryptKey;

    // NOTE: class variable
    private Key key;
    private IvParameterSpec ivParameterSpec;

    public Crypt(final String encryptKey) { this.encryptKey = encryptKey; }

    public void initialize() {
        // 초기화

        byte[] slicedKey = getBytesKeyByLength();
        byte[] IV = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        this.ivParameterSpec = new IvParameterSpec(IV);
        this.key = new SecretKeySpec(slicedKey, ALGORITHM_AES);

    }

    private byte[] getBytesKeyByLength() {

        byte[] byteKey = encryptKey.getBytes(StandardCharsets.UTF_8);
        System.out.println("ByteKey : " + byteKey);
        if (byteKey.length < KEY_MAX_LENGTH) {
            return byteKey;
        } else {
            return Arrays.copyOfRange(byteKey, 0, KEY_MAX_LENGTH);
        }
    }

    public String DataEncrypt(String pw) throws Exception {

        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS_5_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        return Base64.getEncoder().encodeToString(cipher.doFinal(pw.getBytes(StandardCharsets.UTF_8)));

    }

    public String MessageDataEncrypt(String data) throws Exception {

        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS_5_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));

    }
}
