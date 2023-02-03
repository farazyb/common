package hello.cryptography;


import hello.convertor.Convertor;

/**
 * @author navand
 */
public class MACCalculation {


    /**
     * Mac calculation with {@link DES#encryptionCBC}
     * Message Authentication Standard (ANSI X9.9-1986)
     *
     * @param message
     * @param macKey
     * @return
     */
    public static byte[] calculate(byte[] message, byte[] macKey) {
        int group = (message.length + (8 - 1)) / 8;
        int offset = 0;
        byte[] mac = null;
        for (int i = 0; i < group; i++) {
            byte[] temp = new byte[8];
            if (i != group - 1) {
                System.arraycopy(message, offset, temp, 0, 8);
                offset += 8;
            } else {// 0x00
                System.arraycopy(message, offset, temp, 0, message.length - offset);
            }
            if (i != 0) {//
                temp = XOR(mac, temp);
            }
            mac = DES.encryptionCBC(temp, macKey);
        }
        return mac;
    }

    /**
     * Create PIN block with ANSI X9.8 and {@link DES#encryptDESedeCBC}
     *
     * @param cardPin
     * @param cardNumber
     * @param pinKey
     * @return
     */
    public static byte[] calculateAnsi98ForPin2(String cardPin, String cardNumber, byte[] pinKey) {
        byte[] mac;
        String len = cardPin.length() + "";
        if ((cardPin.length() + "").length() == 1) {
            len = "0" + len;
        }
        String part1 = len + cardPin;
        for (int i = 0; i < 16 - cardPin.length(); i++) {
            part1 = part1 + "F";
        }

        String part2 = "0000" + cardNumber.substring(3, 15);
        byte[] result = XOR(Convertor.convertHexStringToByteArray(part1), Convertor.convertHexStringToByteArray(part2));
        mac = DES.encryptDESedeCBC(result, pinKey);
        return mac;
    }
    public static byte[] newCalculateAnsi98(String cardPin, String cardNumber, byte[] pinKey) {
        String len = Integer.toString(cardPin.length());
        if (len.length() == 1) {
            len = "0" + len;
        }
        String part1 = len + cardPin + "FFFFFFFFFFFF".substring(0, 16 - cardPin.length());
        String part2 = "0000" + cardNumber.substring(3, 15);
        byte[] result = XOR(Convertor.hexStringToByteArray(part1), Convertor.hexStringToByteArray(part2));
        return DES.encryptionCBC(result, pinKey);
    }

    /**
     * Mac calculation with {@link DES#encryptionCBC} and {@link DES#decryptionCBC}
     * manually triple DES
     * Message Authentication Standard (ANSI X9.9-1986)
     *
     * @param message
     * @param macKey
     * @return
     */
    public static byte[] oldCalculateTripleDes(byte[] message, String macKey) {

        int group = (message.length + (8 - 1)) / 8;
        byte[] firstSixTeen = Convertor.convertHexStringToByteArray(macKey.substring(0, 16));
        byte[] secounfSixTeen = Convertor.convertHexStringToByteArray(macKey.substring(16, 32));
        byte[] thirdSixTeen = Convertor.convertHexStringToByteArray(macKey.substring(32, 48));
        int offset = 0;
        byte[] mac = null;
        byte[] mac1 = null;
        byte[] mac2 = null;
        for (int i = 0; i < group; i++) {
            byte[] temp = new byte[8];
            if (i != group - 1) {
                System.arraycopy(message, offset, temp, 0, 8);
                offset += 8;
            } else {// 0x00
                System.arraycopy(message, offset, temp, 0, message.length - offset);
            }

            if (i != 0) {//
                temp = XOR(mac, temp);
            }
            mac = DES.encryptionCBC(temp, firstSixTeen);

        }
        mac1 = DES.decryptionCBC(mac, secounfSixTeen);
        mac2 = DES.encryptionCBC(mac1, thirdSixTeen);

        return mac2;
    }

    /**
     * Calculates the Triple DES encryption for a given message and MAC key.
     *
     * @param message The message to be encrypted.
     * @param macKey  The MAC key used for encryption.
     * @return The encrypted message.
     */
    @Deprecated
    public static byte[] CalculateTripleDes(byte[] message, String macKey) {
        int group = (message.length + 7) / 8; // Optimization: Using integer division instead of floating point division
        byte[] firstSixteen = Convertor.convertHexStringToByteArray(macKey.substring(0, 16));
        byte[] secondSixteen = Convertor.convertHexStringToByteArray(macKey.substring(16, 32));
        byte[] thirdSixteen = Convertor.convertHexStringToByteArray(macKey.substring(32, 48));
        int offset = 0;
        byte[] mac = null;
        for (int i = 0; i < group; i++) {
            byte[] temp = new byte[8];
            if (i != group - 1) {
                System.arraycopy(message, offset, temp, 0, 8);
                offset += 8;
            } else {// 0x00
                System.arraycopy(message, offset, temp, 0, message.length - offset);
            }
            if (i != 0) {
                temp = XOR(mac, temp);
            }
            mac = DES.encryptionCBC(temp, firstSixteen);
        }
        byte[] mac1 = DES.newDecryptionCBC(mac, secondSixteen);
        byte[] mac2 = DES.newEncryptionCBC(mac1, thirdSixteen);
        return mac2;
    }

    /**
     * Create PIN block with ANSI X9.8 and {@link DES#encryptDESedeCBC}
     *
     * @param cardPin
     * @param cardNumber
     * @param pinKey
     * @return
     */
    public static byte[] calculateAnsi98TeriplDes(String cardPin, String cardNumber, byte[] pinKey) {
        byte[] mac;
        String len = cardPin.length() + "";
        if ((cardPin.length() + "").length() == 1) {
            len = "0" + len;
        }
        String part1 = len + cardPin;
        for (int i = 0; i < 16 - cardPin.length(); i++) {
            part1 = part1 + "F";
        }

        String part2 = "0000" + cardNumber.substring(3, 15);
        byte[] result = XOR(Convertor.convertHexStringToByteArray(part1), Convertor.convertHexStringToByteArray(part2));
        mac = DES.encryptDESedeCBC(result, pinKey);
        return mac;
    }

    /**
     * Create PIN block with ANSI X9.8 and {@link DES#encryptionCBC}
     *
     * @param cardPin
     * @param cardNumber
     * @param pinKey
     * @return
     */
    public static byte[] calculateAnsi98(String cardPin, String cardNumber, byte[] pinKey) {
        byte[] mac;
        String len = cardPin.length() + "";
        if ((cardPin.length() + "").length() == 1) {
            len = "0" + len;
        }
        String part1 = len + cardPin;
        for (int i = 0; i < 16 - cardPin.length(); i++) {
            part1 = part1 + "F";
        }

        String part2 = "0000" + cardNumber.substring(3, 15);
        byte[] result = XOR(Convertor.convertHexStringToByteArray(part1), Convertor.convertHexStringToByteArray(part2));
        mac = DES.encryptionCBC(result, pinKey);
        return mac;
    }

    public static byte[] calculateAnsi981(String cardPin, String cardNumber, String pinKey) {
        byte[] mac;
        String len = cardPin.length() + "";
        if ((cardPin.length() + "").length() == 1) {
            len = "0" + len;
        }
        String part1 = len + cardPin;
        for (int i = 0; i < 16 - cardPin.length(); i++) {
            part1 = part1 + "F";
        }

        String part2 = "0000" + cardNumber.substring(3, 15);
        byte[] result = XOR(Convertor.convertHexStringToByteArray(part1), Convertor.convertHexStringToByteArray(part2));
        mac = MACCalculation.oldCalculateTripleDes(result, pinKey);
        return mac;
    }

    /**
     * @param byteArrayA
     * @param byteArrayB
     * @return
     */
    public static byte[] XOR(byte[] byteArrayA, byte[] byteArrayB) {
        byte[] result = new byte[8];
        for (int i = 0, j = result.length; i < j; i++) {
            result[i] = (byte) (byteArrayA[i] ^ byteArrayB[i]);
        }
        return result;
    }

    /**
     * Extract PIN from PIN block with {@link DES#decryptionECB}
     *
     * @param pinBlock
     * @param cardNumber
     * @param pinKey
     * @return
     */
    public static String extractPinFromPinBlock(String pinBlock, String cardNumber, byte[] pinKey) {
        String part1 = pinBlock;
        String part2 = "0000" + cardNumber.substring(3, 15);

        byte[] result = XOR(DES.decryptionECB(Convertor.convertHexStringToByteArray(part1), pinKey), Convertor.convertHexStringToByteArray(part2));
        part1 = Convertor.bytesToHex(result);
        int len = Integer.parseInt(part1.substring(0, 2));
        return part1.substring(2, 2 + len);
    }

    public static void main(String[] args) throws Exception {

        byte[] data = Convertor.convertHexStringToByteArray("363237333831313038333530363331383334303030303031323131313633313537303030313533313633313537313231313132313130323130323538393231303030303332373035383633333030323132373034323130353930383320202020202020202020202020303034333030303030303032383031323230303030303030303030303030303030303030");
        long startTime = System.currentTimeMillis();
        byte[] d = MACCalculation.oldCalculateTripleDes(data, "1C1C1C1C1C1C1C1C2C2C2C2C2C2C2C2C1C1C1C1C1C1C1C1C");
        long endTime = System.currentTimeMillis();
        long optimizedDuration = endTime - startTime;
         data = Convertor.convertHexStringToByteArray("363237333831313038333530363331383334303030303031323131313633313537303030313533313633313537313231313132313130323130323538393231303030303332373035383633333030323132373034323130353930383320202020202020202020202020303034333030303030303032383031323230303030303030303030303030303030303030");
        startTime = System.currentTimeMillis();
        byte[] c = MACCalculation.CalculateTripleDes(data, "1C1C1C1C1C1C1C1C2C2C2C2C2C2C2C2C1C1C1C1C1C1C1C1C");
        endTime = System.currentTimeMillis();
        long originalDuration = endTime - startTime;
        System.out.println("Speedup: " + (double) originalDuration / optimizedDuration + " times");
       if( d.equals(c)){
           System.out.println("e");
       }


    }
}
