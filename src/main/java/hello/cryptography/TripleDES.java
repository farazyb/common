/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hello.cryptography;


import hello.convertor.Convertor;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author kami
 */
public class TripleDES {

    public static void main(String[] args) {

        System.out.println(doEncrypt("5047061038480402=20041006843623898633", "37929D804F9EEFB66EE05285F7321A0237929D804F9EEFB6"));
//        SecretKey k1 = generatekey();
//
//        String encryption = Convertor.bytesToHex(encryptionCBC("00001234".getBytes(), k1));
//        String decryption = Convertor.bytesToHex(decryptionCBC(encryptionCBC("00001234".getBytes(), k1), k1));
//        System.out.println(encryption);
//        System.out.println(decryption);

    }

    public static SecretKey generatekey() {
        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance("DESede");
        } catch (NoSuchAlgorithmException ex) {
            //  Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        keyGen.init(112); // key length 112 for two keys, 168 for three keys
        SecretKey secretKey = keyGen.generateKey();
        return secretKey;
    }

    public static String doEncrypt(String track2, String key) {
        int lenght = track2.length();
        if (track2.length() < 32) {
            for (int i = 0; i < 32 - lenght; i++) {
                track2 = "E" + track2;
            }
        }
        String last32Track2Character = track2.substring(track2.length() - 32);

        track2 = track2.replaceAll(last32Track2Character, "");
        last32Track2Character = last32Track2Character.replace('=', 'F');
        byte[] data = Convertor.convertHexStringToByteArray(last32Track2Character);
//       String key="1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C1C";
        byte[] encryptedDataByte = TripleDES.encryptionECB(data, new SecretKeySpec(Convertor.hexStringToByteArray(key), "DESede"));
        System.out.println(last32Track2Character);
        //byte[] encryptedDataByte = DES.encryptECBCorrect(data, Convertor.hexStringToByteArray(key));

        return track2 + Convertor.bytesToHex(encryptedDataByte);
    }

    public static String encryptionECB(String strToEncrypt, SecretKey desKey) {
        try {
            Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, desKey);

            String encryptedString = DatatypeConverter.printBase64Binary(cipher.doFinal(strToEncrypt.getBytes()));
            //String encryptedString = DatatypeConverter.printBase64Binary(cipher.doFinal(strToEncrypt.getBytes()));
            Convertor c = new Convertor();
            return encryptedString;

        } catch (NoSuchAlgorithmException ex) {
            // Logger.getLogger(Test.class
            //       .getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            // Logger.getLogger(Test.class
            //       .getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            //  Logger.getLogger(Test.class
            //        .getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            //  Logger.getLogger(Test.class
            //        .getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            //  Logger.getLogger(Test.class
            //        .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String decryptionECB(String strToDecrypt, SecretKey desKey) {
        try {
            Cipher cipher = Cipher.getInstance("DESede/ECB/Nopadding");
            cipher.init(Cipher.DECRYPT_MODE, desKey);
            String decryptedString = new String(cipher.doFinal(Convertor.hexStringToByteArray(strToDecrypt)));
            return decryptedString;

        } catch (NoSuchAlgorithmException ex) {
            // Logger.getLogger(Test.class
            //       .getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            // Logger.getLogger(Test.class
            //       .getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            // Logger.getLogger(Test.class
            //       .getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            // Logger.getLogger(Test.class
            //       .getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            // Logger.getLogger(Test.class
            //       .getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static byte[] encryptionECB(byte[] strToEncrypt, SecretKey desKey) {
        try {
            Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, desKey);

            return cipher.doFinal(strToEncrypt);

        } catch (NoSuchAlgorithmException ex) {
            // Logger.getLogger(Test.class
            //       .getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            // Logger.getLogger(Test.class
            //       .getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            //  Logger.getLogger(Test.class
            //        .getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            //  Logger.getLogger(Test.class
            //        .getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            //  Logger.getLogger(Test.class
            //        .getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decryptionECB(byte[] strToDecrypt, SecretKey desKey) {
        try {
            Cipher cipher = Cipher.getInstance("DESede/ECB/NoPADDING");
            cipher.init(Cipher.DECRYPT_MODE, desKey);
            return cipher.doFinal(strToDecrypt);

        } catch (NoSuchAlgorithmException ex) {
            // Logger.getLogger(Test.class
            //       .getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            // Logger.getLogger(Test.class
            //       .getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            // Logger.getLogger(Test.class
            //       .getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            // Logger.getLogger(Test.class
            //       .getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            // Logger.getLogger(Test.class
            //       .getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static byte[] encryptionCBC(byte[] strToEncrypt, SecretKey desKey) {
        try {
            Cipher cipher = Cipher.getInstance("DESede/CBC/NoPadding");

// Create an initialization vector (necessary for CBC mode)
            IvParameterSpec IvParameters = new IvParameterSpec(
                    new byte[]{00, 00, 00, 00, 00, 00, 00, 00});

// Initialize the cipher and put it into encrypt mode
            cipher.init(Cipher.ENCRYPT_MODE, desKey, IvParameters);

            byte[] plaintext = strToEncrypt;

// Encrypt the data
            byte[] encrypted = cipher.doFinal(plaintext);

            return encrypted;

        } catch (Exception e) {

        }
        return null;
    }

    public static byte[] decryptionCBC(byte[] strToEncrypt, SecretKey desKey) {
        try {
            Cipher cipher = Cipher.getInstance("DESede/CBC/NoPadding");

// Create an initialization vector (necessary for CBC mode)
            IvParameterSpec IvParameters = new IvParameterSpec(
                    new byte[]{00, 00, 00, 00, 00, 00, 00, 00});

// Initialize the cipher and put it into encrypt mode
            cipher.init(Cipher.DECRYPT_MODE, desKey, IvParameters);

            byte[] plaintext = strToEncrypt;

// Encrypt the data
            byte[] decrypted = cipher.doFinal(plaintext);

            return decrypted;

        } catch (Exception e) {

        }
        return null;
    }

}
