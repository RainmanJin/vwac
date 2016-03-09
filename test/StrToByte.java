

public class StrToByte
{

	public static String byte2hex(byte[] b) // 二进制转字符串

	{

		String hs = "";

		String stmp = "";

		for (int n = 0; n < b.length; n++)
		{

			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));

			if (stmp.length() == 1)

				hs = hs + "0" + stmp;

			else

				hs = hs + stmp;

		}

		return hs;

	}

	public static byte[] hex2byte(String str)
	{ // 字符串转二进制
		if (str == null)
			return null;

		str = str.trim();

		int len = str.length();

		byte[] b = new byte[len];

		try
		{

			for (int i = 0; i < str.length(); i++)
			{
				b[i] = (byte) Integer

				.decode("0x" + str.substring(i, i + 1)).intValue();

			}

			return b;

		} catch (Exception e)
		{

			return null;

		}

	}

	public static void main(String[] args)
	{
		byte[] bytes = new byte[4];
		bytes[0] = 10;
		bytes[1] = 5;
		bytes[2] = 11;
		bytes[3] = 8;

		String result = "";

		result = byte2hex(bytes).replaceAll("0", "");

		System.out.println(result);

		bytes = hex2byte(result);
		System.out.println(bytes[0]);
		System.out.println(bytes[1]);
		System.out.println(bytes[2]);
		System.out.println(bytes[3]);

	}

}