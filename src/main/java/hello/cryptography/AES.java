package hello.cryptography;


import hello.convertor.Convertor;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

/**
 * @author kami
 */
public class AES {


    public byte[] decrypt(byte[] data) {
        byte[] result = null;
        try {
            /*byte[] keyHex = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
             0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
             0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
             0x00, 0x00};*/
            byte[] keyHex = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                    0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
            return decrypt(data, keyHex);
        } catch (Exception ex) {
           // logger.error("Exception!", ex);
        }
        return result;
    }

    public byte[] decrypt(byte[] data, byte[] keyHex) {
        byte[] result = null;
        try {
            SecretKey key = new SecretKeySpec(keyHex, "AES");
            byte[] iv = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            IvParameterSpec IvParameters = new IvParameterSpec(iv);

            cipher.init(Cipher.DECRYPT_MODE, key, IvParameters);

            result = cipher.doFinal(data);

            System.out.println(DatatypeConverter.printHexBinary(result));

            return result;
        } catch (Exception ex) {
           // logger.error("Exception!", ex);
        }
        return result;
    }

    public byte[] encrypt(byte[] data) {

        byte[] result = null;
        try {
            byte[] keyHex = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                    0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
            result = encrypt(data, keyHex);
        } catch (Exception ex) {
            //logger.error("Exception!", ex);
        }
        return result;
    }

    public byte[] encrypt(byte[] data, byte[] keyHex) {
        byte[] workingData;
        int remind = data.length % 16;
        if (remind != 0) {
            workingData = new byte[data.length + 16 - remind];
            Arrays.fill(workingData, (byte) 0x00);
            System.arraycopy(data, 0, workingData, 0, data.length);
        } else {
            workingData = new byte[data.length];
            Arrays.fill(workingData, (byte) 0x00);
            System.arraycopy(data, 0, workingData, 0, data.length);
        }
        byte[] result = null;
        try {
            SecretKey key = new SecretKeySpec(keyHex, "AES");
            byte[] iv = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
            IvParameterSpec IvParameters = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key, IvParameters);

            result = cipher.doFinal(workingData);

            System.out.println(DatatypeConverter.printHexBinary(result));

        } catch (Exception ex) {
           // logger.error("Exception!", ex);
        }
        return result;
    }
    public byte[] newEncrypt(byte[] data, byte[] keyHex) {
        byte[] workingData = Arrays.copyOf(data, data.length + 16 - (data.length % 16));
        byte[] result = null;

        try {
            SecretKey key = new SecretKeySpec(keyHex, "AES");
            byte[] iv = new byte[16];
            IvParameterSpec IvParameters = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key, IvParameters);

            result = cipher.doFinal(workingData);

            System.out.println(DatatypeConverter.printHexBinary(result));
        } catch (Exception ex) {
            //logger.error("Exception!", ex);
        }
        return result;
    }

    public static byte[] getAESKey(String baseHex) {
        byte[] base = Convertor.convertHexStringToByteArray(baseHex);

//        base = new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
//                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
//                (byte) 0xff, (byte) 0xff, (byte) 0xff};
        System.out.println(Convertor.convertBytesToHex(base));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String rawDate = simpleDateFormat.format(Calendar.getInstance().getTime());
        byte[] date = rawDate.getBytes(StandardCharsets.UTF_8);
        byte[] secureDate = MD5.getSecurePassword(date, "SEA".getBytes(StandardCharsets.UTF_8));
        byte[] one = combine(Arrays.copyOfRange(base, 0, 8), Arrays.copyOfRange(secureDate, 0, 8));
        byte[] two = combine(Arrays.copyOfRange(base, 8, 16), Arrays.copyOfRange(secureDate, 8, 16));

        byte[] combined = new byte[one.length + two.length];

        System.arraycopy(one, 0, combined, 0, one.length);
        System.arraycopy(two, 0, combined, one.length, two.length);
        return combined;
    }

    public static byte[] getAESKey(String baseHex, int amount) {
        byte[] base = Convertor.hexStringToByteArray(baseHex);

//        base = new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
//                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
//                (byte) 0xff, (byte) 0xff, (byte) 0xff};
        System.out.println(Convertor.bytesToHex(base));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, amount);
        String rawDate = simpleDateFormat.format(calendar.getTime());
        byte[] date = rawDate.getBytes(StandardCharsets.UTF_8);
        byte[] secureDate = MD5.getSecurePassword(date, "SEA".getBytes(StandardCharsets.UTF_8));
        byte[] one = combine(Arrays.copyOfRange(base, 0, 8), Arrays.copyOfRange(secureDate, 0, 8));
        byte[] two = combine(Arrays.copyOfRange(base, 8, 16), Arrays.copyOfRange(secureDate, 8, 16));

        byte[] combined = new byte[one.length + two.length];

        System.arraycopy(one, 0, combined, 0, one.length);
        System.arraycopy(two, 0, combined, one.length, two.length);
        return combined;
    }

    public static byte[] combine(byte[] first, byte[] second) {

        ByteBuffer wrapped = ByteBuffer.wrap(first); // big-endian by default
        long firstNum = wrapped.getLong(); // 1
        wrapped = ByteBuffer.wrap(second); // big-endian by default
        long secondNum = wrapped.getLong(); // 1
        long result = (firstNum & secondNum);
        ByteBuffer dbuf = ByteBuffer.allocate(8);
        dbuf.putLong(result);
        byte[] bytes = dbuf.array();

        return bytes;
    }


    public static void main(String[] args) {
        byte[] data = new byte[10000];
        byte[] keyHex = new byte[16];

AES aes=new AES();

        // Time the original method
        long startTime = System.currentTimeMillis();
        aes.encrypt(data, keyHex);
        long endTime = System.currentTimeMillis();
        long originalTime = endTime - startTime;

        // Time the optimized method
        startTime = System.currentTimeMillis();
        aes.newEncrypt(data, keyHex);
        endTime = System.currentTimeMillis();
        long optimizedTime = endTime - startTime;

        // Compare the results
        System.out.println("Original method took: " + originalTime + "ms");
        System.out.println("Optimized method took: " + optimizedTime + "ms");
        if (optimizedTime < originalTime) {
            System.out.println("The optimized method is faster!");
        } else {
            System.out.println("The original method is faster!");
        }
    }
}
