package hello.cryptography;



import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author kami
 */
public class DES {

    /**
     * Encrypt type: DES/ECB/NoPadding
     *
     * @param data
     * @param key
     * @return encrypted in base64
     */
    public static String encryptionECB(String data, byte[] key) {
        try {
            SecretKey secretKey = getSecretKeySpec(key);
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            String encryptedString = DatatypeConverter.printBase64Binary(cipher.doFinal(data.getBytes()));
            return encryptedString;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Decrypt type: DES/ECB/NoPadding
     *
     * @param data
     * @param key
     * @return decrypted base 64
     */
    public static String decryptionECB(String data, byte[] key) {
        try {
            SecretKey secretKey = getSecretKeySpec(key);
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            String decryptedString = new String(cipher.doFinal(DatatypeConverter.parseBase64Binary(data)));
            return decryptedString;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Encrypt type: DES/ECB/NoPadding
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] encryptionECB(byte[] data, byte[] key) {
        byte[] encrypted = null;
        try {
            SecretKey secretKey = getSecretKeySpec(key);
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            encrypted = cipher.doFinal(data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return encrypted;
    }

    /**
     * Decrypt type: DES/ECB/NoPadding
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] decryptionECB(byte[] data, byte[] key) {
        byte[] decrypted = null;
        try {
            SecretKey secretKey = getSecretKeySpec(key);
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            decrypted = cipher.doFinal(data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return decrypted;
    }

    /**
     * Encrypt type: DES/CBC/NoPadding
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] encryptionCBC(byte[] data, byte[] key) {
        byte[] encrypted = null;
        try {
            SecretKey secretKey = getSecretKeySpec(key);
            Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
            IvParameterSpec IvParameters = new IvParameterSpec(new byte[8]);// Create an initialization vector (necessary for CBC mode)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, IvParameters);// Initialize the cipher and put it into encrypt mode
            byte[] plaintext = data;
            encrypted = cipher.doFinal(plaintext);// Encrypt the data
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return encrypted;
    }
    public static byte[] newEncryptionCBC(byte[] data, byte[] key) {
        try {
            SecretKey secretKey = newGetSecretKeySpec(key);
            Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[8]));
            return cipher.doFinal(data);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Decrypt type: DES/CBC/NoPadding
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] decryptionCBC(byte[] data, byte[] key) {
        byte[] decrypted = null;
        try {
            SecretKey secretKey = getSecretKeySpec(key);
            Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
            IvParameterSpec IvParameters = new IvParameterSpec(new byte[8]);// Create an initialization vector (necessary for CBC mode)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameters);// Initialize the cipher and put it into encrypt mode
            byte[] plaintext = data;
            decrypted = cipher.doFinal(plaintext);// Encrypt the data
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return decrypted;
    }
    public static byte[] newDecryptionCBC(byte[] data, byte[] key) {
        try {
            SecretKey secretKey = newGetSecretKeySpec(key);
            Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[8]));
            return cipher.doFinal(data);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Encrypt type: DESede/CBC/NoPadding
     *
     * @param message
     * @param pinKey
     * @return
     */
    public static byte[] encryptDESedeCBC(byte[] message, byte[] pinKey) {
        try {
            final byte[] keyBytes = Arrays.copyOf(pinKey, 24);
            for (int j = 0, k = 16; j < 8; ) {
                keyBytes[k++] = keyBytes[j++];
            }
            final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
            final Cipher cipher = Cipher.getInstance("DESede/CBC/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            final byte[] plainTextBytes = message;
            final byte[] cipherText = cipher.doFinal(plainTextBytes);
            // final String encodedCipherText = new sun.misc.BASE64Encoder()
            // .encode(cipherText);
            return cipherText;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Generate DES key
     *
     * @return
     */
    public static byte[] generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * Convert byte array key to {@link SecretKey}
     *
     * @param keyB
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static SecretKey getSecretKeySpec(byte[] keyB) throws NoSuchAlgorithmException, InvalidKeySpecException {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            DESKeySpec desKeySpec = new DESKeySpec(keyB);
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            return secretKey;

        } catch (InvalidKeyException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    private static SecretKey newGetSecretKeySpec(byte[] keyB) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            DESKeySpec desKeySpec = new DESKeySpec(keyB);
            return keyFactory.generateSecret(desKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }



}
