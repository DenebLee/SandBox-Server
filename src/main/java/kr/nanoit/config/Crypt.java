package kr.nanoit.config;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.Base64;

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

    /**
     * Instantiates a new Crypt.
     */
    public Crypt() {

    }

    /**
     * hex print, 테스트 용도로 데이터를 hex값으로 확인하기 위해 사용
     *
     * @param data the data
     * @return X
     */
    public static void printHEX(byte[] data) {
        for (byte aData : data)
            System.out.print("[" + Integer.toHexString(0xff & aData) + "] ");
        System.out.print("\n");
    }

    /**
     * 암호화, 패딩 방식 설정 및 암호화 복호화에 사용할 key 생성
     *
     * @param fileEncryptKey the file encrypt key
     * @return X
     *//*
    private void cryptInit(String fileEncryptKey) {
        try {
            if (fileEncryptKey == null) {
                log.info("Crypt Init Err, enCryptKey=null, ");
            } else {
                int keySize = fileEncryptKey.getBytes().length;

                if (keySize > 16)
                    keySize = 16;

                System.arraycopy(fileEncryptKey.getBytes(), 0, keyBytes, 0, keySize);

                //key 생성
                key = new SecretKeySpec(keyBytes, "AES");

                ivSpec = new IvParameterSpec(IV);
                provider = new BouncyCastleProvider();

                //어떤 모드로 암호화, 복호화를 할 것인지 설정, 암호화종류/방식/패딩처리 방식ZeroBytePadding
                cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            }
        } catch (Exception e) {
            log.warn("Crypt Init Exception, ", e);
        }
    }*/

    /**
     * 암호화, 패딩 방식 설정 및 암호화 복호화에 사용할 key 생성
     *
     * @param enc the enc
     */
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
        return decryptAsByte(Base64.getDecoder().decode(data));
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
    public InputStream fileDecrypt(File source, String key) throws Exception {
        cryptInit(key);
        InputStream out = null;

        if (source != null && source.exists() && source.length() > 0) {
            InputStream input = new BufferedInputStream(new FileInputStream(source));

            byte[] buffer = new byte[(int) source.length()];
            int readLength = input.read(buffer, 0, buffer.length);
            byte[] buffer2 = deCrypt(new String(buffer));
            StringBuilder decryData = new StringBuilder(new String(buffer2));

            int startI = decryData.indexOf("<?xml");
            int endI;
            if (startI > 0) {
                decryData.delete(0, startI);
            }

            while ((startI = decryData.indexOf("<!--") - 3) > 0) {
                if ((endI = decryData.indexOf("-->") + 3) > startI) {
                    decryData.delete(startI, endI);
                } else {
                    break;
                }
            }
            out = new ByteArrayInputStream(decryData.toString().getBytes(), 0, decryData.toString().getBytes().length);

            out.close();
            input.close();
        }

        return out;
    }

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
    public synchronized String decryptAsString(byte[] inData) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] decryptedText;
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        decryptedText = cipher.doFinal(inData);
        String outData = new String(decryptedText).trim();
        int len;
        if ((len = outData.indexOf(0x00)) < 0) {
            len = outData.length();
        }
        return outData.substring(0, len);
    }
}
