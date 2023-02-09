package convertor;

import hello.convertor.Convertor;
import org.junit.Test;

import java.util.Random;

import static hello.convertor.Convertor.hexStringToByteArray;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class HexStringToByteArrayTest {
    private static final int ITERATIONS = 100_000;
    private static final int LENGTH = 100;
    private static final Random RANDOM = new Random();

    @Test
    public void testOriginal() {

        for (int i = 0; i < ITERATIONS; i++) {

            String s = randomHexString(LENGTH);
            byte[] expected = toByteArray(s);
            byte[] actual = hexStringToByteArray(s);
            assertArrayEquals(expected, actual);
        }
    }

    @Test
    public void testOptimized() {

        for (int i = 0; i < ITERATIONS; i++) {
            String s = randomHexString(LENGTH);
            byte[] expected = toByteArray(s);
            byte[] actual = Convertor.convertHexStringToByteArray(s);
            assertArrayEquals(expected, actual);
        }
    }

    private static String randomHexString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(Integer.toHexString(RANDOM.nextInt(16)));
        }
        return sb.toString();
    }

    private static byte[] toByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            int high = Integer.parseInt(s.substring(i, i + 1), 16);
            int low = Integer.parseInt(s.substring(i + 1, i + 2), 16);
            data[i / 2] = (byte) ((high << 4) + low);
        }
        return data;
    }


    @Test
    public void testBytesToHex() {
        for (int i = 0; i < ITERATIONS; i++) {
            byte[] in = randomBytes(LENGTH);
            String expected = toHexString(in);
            String actual = Convertor.convertBytesToHex(in);
            assertEquals(expected, actual);
        }
    }

    private static byte[] randomBytes(int length) {
        byte[] result = new byte[length];
        RANDOM.nextBytes(result);
        return result;
    }

    private static String toHexString(byte[] in) {
        final StringBuilder builder = new StringBuilder();
        for (byte b : in) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString().toUpperCase();
    }

   /* public static void main(String[] args) {
        final String data="3031303046323338303238313238433138303030303030303030303031303030303030313136363231393836313030363437343330373333303030303030303031303030303030303032323730393531323230323930383931333231323230323237303030303230363632313938363337363231393836313030383436383837373D3136303531303038353830373231343432373737303131303139303239303839303030313130313930313030303032322020202020202030303930373132423642304138313038333133303245333232453337324533303646313638373130333633323331333933383336333133303330333833343336333833383337333738393032333033303632303538313033333233333338333634303938393132323030303034324631463243464345423536434545";
        byte[] bytes = convertHexStringToByteArray(data);

        long startTime = System.nanoTime();
       String old= Convertor.bytesToHex(bytes);

        long endTime = System.nanoTime();
        long originalDuration = endTime - startTime;
        startTime = System.nanoTime();
        String neeForm=Convertor.convertBytesToHex(bytes);
        endTime = System.nanoTime();
        long optimizedDuration = endTime - startTime;


        System.out.println("Original duration: " + originalDuration + " ns");
        System.out.println("Optimized duration: " + optimizedDuration + " ns");
        System.out.println("Speedup: " + (double) originalDuration / optimizedDuration + " times");
        if(neeForm.equals(data)){
            System.out.println("equal");
        }
        if(old.equals(data)){
            System.out.println("equal");
        }
    }*/
}
