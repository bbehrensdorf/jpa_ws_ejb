package wbs.lotto.util;

public class ByteLongConverter {

	public static byte[] longToByte(final long[] longArray) {
		final byte[] byteArray = new byte[longArray.length * 8];
		for (int i = 0; i < longArray.length; i++) {
			for (int j = 7; j >= 0; j--) {
				byteArray[i * 8 + 7 - j] = (byte) Long.rotateRight(longArray[i], j * 8);
			}
		}
		return byteArray;
	}

	public static long[] byteToLong(final byte[] byteArray) {
		final long[] longArray = new long[byteArray.length / 8];
		for (int i = 0; i < longArray.length; i++) {
			longArray[i] = 0L;
			for (int j = 7; j >= 0; j--) {
				long l = 0L;
				for (int k = 7; k >= 0; k--) {
					l = l << 1;
					if ((byteArray[i * 8 + j] & (byte) (1 << k)) != 0) {
						l = (l | 1L);
					}
				}
				l = l << 8 * (7 - j);
				longArray[i] = longArray[i] | l;
			}
		}
		return longArray;
	}
	
}
