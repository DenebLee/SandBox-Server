package kr.nanoit.config;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;

/**
 * The type Crypt.
 */
public class Crypt {

    private kr.nanoit.config.Base64Coder Base64Coder = new Base64Coder();
    private byte[] IV = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    private byte[] keyBytes = new byte[16];

    private IvParameterSpec ivSpec;
    private SecretKeySpec key;
    private Cipher cipher;
    private BouncyCastleProvider provider;
    public Crypt() {

    }

    public void cryptInit(String enc) {
        try {
            String Key = enc;

            if (Key == null) {
                System.out.println("Crypt Init Err, enCryptKey=null, ");
            } else {
                System.out.println(String.format("Crypt Init, enCryptKey=%s", enc));
                int keySize = Key.getBytes().length;

                if (keySize > 16)
                    keySize = 16;

                System.arraycopy(Key.getBytes(), 0, keyBytes, 0, keySize);

                //key 생성
                key = new SecretKeySpec(keyBytes, "AES");

                ivSpec = new IvParameterSpec(IV);
                provider = new BouncyCastleProvider();

                //어떤 모드로 암호화, 복호화를 할 것인지 설정, 암호화종류/방식/패딩처리 방식ZeroBytePadding
                cipher = Cipher.getInstance("AES    /CBC/PKCS5Padding");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * De crypt byte [ ].
     *
     * @param data the data
     * @return the byte [ ]
     * @throws InvalidKeyException                the invalid key exception
     * @throws InvalidAlgorithmParameterException the invalid algorithm parameter exception
     * @throws IllegalBlockSizeException          the illegal block size exception
     * @throws BadPaddingException                the bad padding exception
     */
    public byte[] deCrypt(String data) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        return decryptAsByte(Base64.decodeBase64(data));
    }

    /**
     * En crypt char [ ].
     *
     * @param data  the data
     * @param encod the encod
     * @return the char [ ]
     */
    public char[] enCrypt(String data, String encod) {
        char[] enCryptData = null;
        try {
            enCryptData = Base64Coder.encode(encrypt(data, encod));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enCryptData;
    }

    /**
     * AES128 + base64 복호화
     *
     * @param source the source
     * @param key    the key
     * @return InputStram input stream
     * @throws Exception the exception
     */

    /**
     * 암호화
     *
     * @param inData the in data
     * @param encod  the encod
     * @return byte[], encrypt data
     * @throws Exception the exception
     */
    public synchronized byte[] encrypt(String inData, String encod) throws Exception {
        byte[] cipherText;
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        cipherText = cipher.doFinal(inData.getBytes(encod));
        return cipherText;
    }

    /**
     * charset 변경하여  암호화
     *
     * @param inData  the in data
     * @param encod   the encod
     * @param fullLen the full len
     * @return byte[], encrypt data
     * @throws Exception the exception
     */
    public synchronized byte[] encrypt(String inData, String encod, int fullLen) throws Exception {
        byte[] cipherText;
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        cipherText = cipher.doFinal(inData.getBytes(encod));
        return cipherText;
    }

    /**
     * 복호화
     *
     * @param inData the in data
     * @return byte[], decrypt data
     * @throws InvalidKeyException                the invalid key exception
     * @throws InvalidAlgorithmParameterException the invalid algorithm parameter exception
     * @throws IllegalBlockSizeException          the illegal block size exception
     * @throws BadPaddingException                the bad padding exception
     */
    private synchronized byte[] decryptAsByte(byte[] inData) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] decryptedText;
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        decryptedText = cipher.doFinal(inData);
        return decryptedText;
    }

    /**
     * 복호화
     *
     * @param inData the in data
     * @return String, decrypt data
     * @throws InvalidKeyException                the invalid key exception
     * @throws InvalidAlgorithmParameterException the invalid algorithm parameter exception
     * @throws IllegalBlockSizeException          the illegal block size exception
     * @throws BadPaddingException                the bad padding exception
     */

}
