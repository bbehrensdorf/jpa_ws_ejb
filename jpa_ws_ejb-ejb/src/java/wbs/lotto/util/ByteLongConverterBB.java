package wbs.lotto.util;


public class ByteLongConverterBB {

    public static byte[] longToByte(final long[] longArray) {

        byte[] result = new byte[longArray.length * Long.BYTES];
        for (int i = 0; i < longArray.length; i++) {
            long l = longArray[i];
            byte[] b = new byte[Long.BYTES];
            for (int j = Long.BYTES-1; j >= 0; j--) {
                b[j] = (byte) (l & 0xFF);
                l >>= 8;
            }
            System.arraycopy(b, 0, result, i * Long.BYTES, b.length);
        }

        return result;
    }

    public static long[] byteToLong(final byte[] byteArray) {
        long[] result = new long[byteArray.length / Long.BYTES];

        for (int i = 0; i < byteArray.length; i += Long.BYTES) {
            byte[] b = new byte[Long.BYTES];
            System.arraycopy(byteArray, i, b, 0, Long.BYTES);
            long l = 0L;
            for (int j = 0; j < Long.BYTES; j++) {
                l <<= 8;
                l |= (b[j] & 0xFF);
            }
            result[i / 8] = l;
        }

        return result;
    }

}
