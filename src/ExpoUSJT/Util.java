package ExpoUSJT;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Util {

	public static String getData() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(new Date());
	}

	public static java.sql.Date getSqlDate() {
		long millis = System.currentTimeMillis();
		java.util.Date date = new java.util.Date(millis);
		return new java.sql.Date(date.getTime());

	}

	public static int checkSum(String code) {
		int val = 0;
		for (int i = 0; i < code.length(); i++) {
			val += ((int) Integer.parseInt(code.charAt(i) + "")) * ((i % 2 == 0) ? 1 : 3);
		}

		int checksum_digit = 10 - (val % 10);
		if (checksum_digit == 10)
			checksum_digit = 0;

		return checksum_digit;
	}

	public static String getEan13Random() {

		String r = "" + generateRandom(12);

		return r + checkSum(r);

	}

	public static long generateRandom(int length) {
		Random random = new Random();
		char[] digits = new char[length];
		digits[0] = (char) (random.nextInt(9) + '1');
		for (int i = 1; i < length; i++) {
			digits[i] = (char) (random.nextInt(10) + '0');
		}
		return Long.parseLong(new String(digits));
	}
}
