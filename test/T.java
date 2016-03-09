

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * <p>
 * Title: AES encrypt tool
 * </p>
 * <p>
 * Description: 用AES 128对用户密码进行加密。
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: HUAWEI
 * </p>
 * 
 * @author 吴斌 38145
 * @version 1.0
 * @since iSpaceV100R001C01B030
 */
public final class T
{
	/**
	 * 
	 * 构造函数。
	 * 
	 */
	private T()
	{

	}

	/**
	 * AES加密
	 * 
	 * @param bkey
	 *            参数
	 * @param bsrc
	 *            参数
	 * @return 返回
	 */
	public static String encryptAES(byte[] bkey, byte[] bsrc)
	{
		// 保存密文
		String sCipher = null;

		// 加密密钥
		byte[] encKey = new byte[16];

		int len = bkey.length;

		if (len > 16)
		{
			len = 16;
		}

		System.arraycopy(bkey, 0, encKey, 0, len);

		System.out.println("Key:\t" + new String(encKey));

		// 用AES生成SecretKey对象
		SecretKey deskey = new SecretKeySpec(encKey, "AES");

		try
		{
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

			// 初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, deskey);

			sCipher = ByteTool.byte2hex(cipher.doFinal(bsrc));
		} catch (NoSuchAlgorithmException e)
		{
			printError("No Such Algorithm exception", e);
		} catch (NoSuchPaddingException e)
		{
			printError("No Such Padding exception", e);
		} catch (InvalidKeyException e)
		{
			printError("Invalid Key exception", e);
		} catch (IllegalStateException e)
		{
			printError("Illegal State exception", e);
		} catch (IllegalBlockSizeException e)
		{
			printError("Illegal BlockSize exception", e);
		} catch (BadPaddingException e)
		{
			printError("Bad Padding exception", e);
		}

		return sCipher;
	}

	/**
	 * AES解密
	 * 
	 * @param bkey
	 *            参数
	 * @param decrSrc
	 *            参数
	 * @return 参数
	 */
	public static byte[] decryptAES(byte[] bkey, byte[] decrSrc)
	{
		// 解密后的原文
		byte[] byteSrc = null;

		// 加密密钥
		byte[] encKey = new byte[16];

		int len = bkey.length;

		if (len > 16)
		{
			len = 16;
		}

		System.arraycopy(bkey, 0, encKey, 0, len);

		// 用AES生成SecretKey对象
		SecretKey deskey = new SecretKeySpec(encKey, "AES");

		try
		{
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

			// 初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, deskey);

			byteSrc = cipher.doFinal(decrSrc);
		} catch (NoSuchAlgorithmException e)
		{
			printError("No Such Algorithm exception", e);
		} catch (NoSuchPaddingException e)
		{
			printError("No Such Padding exception", e);
		} catch (InvalidKeyException e)
		{
			printError("Invalid Key exception", e);
		} catch (IllegalStateException e)
		{
			printError("Illegal State exception", e);
		} catch (IllegalBlockSizeException e)
		{
			printError("Illegal BlockSize exception", e);
		} catch (BadPaddingException e)
		{
			printError("Bad Padding exception", e);
		}

		return byteSrc;
	}

	public static void main(String[] args)
	{
		String sss = "B7F86C207680348808809009B90FC91E5F9ACA37D3865C3B4F3AAA6A7487B36113C4C4CC59FDE63534FCB20992D3D2E5DE0E6F65DA7C6EB11B8EBD1372AE7580AA2AB1A11A15823BB2C83A4CEBDB1DCF2C28479AD60CCD083695FDC30337E3DC1D0019B9CEEA94FF045045E9A99F6449043A377DE902B5F912BACF06A83ABED3902A5A94A61CC5AD61355D28544BD542453FBF420417CC691EBC6DDA94A2F1028980A328E7A42D32D4B6E5DE5EB8610E8AFD35AE4AD02346EF4B9E3B4A5E4DD2B47E63DD2697B5F71E59D2773E9A2E03C1D7EA74210D00DFE9FDC25EC9FC7CFB0D4B06C14B4F2CDE7F2750A12A96937A62CB14079055B23323CE1F6B33E21EA2E3CB774E7B45A81CAFFE1DF63498BA84D521CEBFC62F913ED2C28714D25F7844FED27BC090329BDD80D7BAB2A18A5CC58C64AC91937C042F066B0CBD90294848A27B6CA36B45629F0DC2E3F3BC5E3CA2D4B94B091D8051491DA8DF1B9BD15C44E4A53BB014A84E8579652B2824DF34705977E22BA64AB154BCEE257A2D0B76C36C6B95D49677F2D97D572D0B9C37FF7F260E32FC89F7B7F790ADCFD458F71B76F5A32D6A64DCF5B48E8D5CE8ACD52DDDB54F2A72B085AF044CDE229BCE6FB766BBA0FC376B062B6620F8B196E398A4B1E2B7D343F6557CCAB8CFE65CB4DA2256AE507D7D9FA6AEAF14498ECF8E7646ECB67339FC2F8C071E01A4A3E452F7C473CC561810F765CBEB66FDABA87C6633981A87D4B2EE8AC6368FFE5059214020217991863545FD9A6F487FDD30FDCD63AD4C2CABD024C9FE002DFBFDA1400ED1C9B6D92A7D4BB389EDBB906300416C41E667B583F69205BB4558760C9EB5A1D3A788A58A955DDB34845179FA1981B6E9ADCA773A41547509FB3B455D29D89FA2B2AEA93F059CB25412EFC73889C61ADC9312FCD11A26C80EC7D29AE65CDC49CA5B677974E62CCD316069763821D22674BF1D4200E6D1249B988FF67D81C908B9F70D198D2AAC1465AB8A9ED9DB5956F5796516EAD8FFA79540E1866E29512893B3FD93AF31440AD20D58279DE3B4858C13B0B9E4F8F9B1830838F5076475BC924C709C561CCF1CFF62874972B405129C9B46BE12FB9E50B0A368055A812A1B857F72C726E756B21ED98D297BB711EE416787E22B085DE26091BFD42119A15421ECBFF67D744D4BC1BF6005D864F2246D5EB38ECD8B0AEA758A4F7D3CCF8B93B6B9F5F2E76662E709ADD1BAC94553FD3495A3FEA903B318D4478F69C26FE4146E330A4E7BF2F124F6C8826AEBC2E4602AA1986D11BF8C4C6D6F26EC5F1E4A68EE94AEE84D4CF0C76A1FD5F83A475A098CBEB0B9E4F8F9B1830838F5076475BC924C852CF7CDAB7B58006FF9CCD00F14BCB14432CB0CB38420C5ABDA8F471EFB9F1B33C96C026A49DE5452B774246AA3587663936ED5DC6146B6E0E393746E919BC684BF6D47AA4CFAEE9A06D53911FBD9D0AF5E2842CB8AB51D7B340FEC3EE9DBEF99B95B379CCD7B425513F1624C500C7711ED5E5F2FAF63DD8B2CE857016FF5B836CE5B4653DA058F53F7FEB104A156E3DEEAB4D52DA5CADF46C8F4C457B64148A93C6C213D90C23F56E16408520DE5A10A2A676047238747248C0313BCA982C49E0929128E6DD53FC848B28441628F2E13716B898FE35D3A377B64323A957D684AB76ECF188BF9786B12FF31B6591C623717B877392A4C829ADB47AF1551BF4FD89F069722829ACDCA42032BFE5C0FE9";
		String keys = "120356F7AFA9D2A87FD86825C72C36BD97E40BA7C88685E08FE6B41F1D169D77";

		System.out.println(new String(decryptAES(keys.getBytes(), ByteTool
				.hex2byte(sss))));
	}

	public static void printError(String s)
	{
		System.out.println("=-=============\t" + s);
	}

	public static void printError(String s, Exception e)
	{
		System.out.println(s);
		e.printStackTrace();
	}

}