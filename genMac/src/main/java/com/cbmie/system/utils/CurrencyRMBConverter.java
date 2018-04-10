package com.cbmie.system.utils;

public class CurrencyRMBConverter {
	private static final String CN_ZERO = "零";
	// private static final String CN_SYMBOL = "￥:";
	private static final String CN_SYMBOL = "";
	private static final String CN_DOLLAR = "圆";
	private static final String CN_INTEGER = "整";

	private static final String[] digits = new String[] { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
	private static final String[] radices = new String[] { "", "拾", "佰", "仟" };
	private static final String[] bigRadices = new String[] { "", "万", "亿", "万" };
	private static final String[] decimals = new String[] { "角", "分" };

	/**
	 * 转换大写
	 * 
	 * @param currencyDigits
	 * @return
	 */
	public static String convert(String currencyDigits) {

		String integral = null; // 整数部分
		String decimal = null; // 小数部分
		String outputCharacters = null; // 最终转换输出结果

		String d = null;
		int zeroCount = 0, p = 0, quotient = 0, modulus = 0;

		// 删除数字中的逗号,
		currencyDigits = currencyDigits.replace("/,/g", "");
		currencyDigits = currencyDigits.replaceAll(",", "");
		// 删除数字左边的0
		currencyDigits = currencyDigits.replace("/^0+/", "");
		if (currencyDigits == null || "".equals(currencyDigits) || ".00".equals(currencyDigits))
			currencyDigits = "0.00";
		// 拆分数字中的整数与小数部分
		String[] parts = currencyDigits.split("\\.");
		if (parts.length > 1) {
			integral = parts[0];
			decimal = parts[1];

			// 如果小数部分长度大于2，四舍五入，保留两位小数
			if (decimal.length() > 2) {
				long dd = Math.round(Double.parseDouble("0." + decimal) * 100);
				decimal = Long.toString(dd);
			}

		} else {
			integral = parts[0];
			decimal = "0";
		}

		// Start processing:
		outputCharacters = "";
		// Process integral part if it is larger than 0:
		if (Double.parseDouble(integral) > 0) {

			zeroCount = 0;

			for (int i = 0; i < integral.length(); i++) {

				p = integral.length() - i - 1;
				d = integral.substring(i, i + 1);

				quotient = p / 4;
				modulus = p % 4;
				if (d.equals("0")) {
					zeroCount++;
				} else {
					if (zeroCount > 0) {
						outputCharacters += digits[0];
					}
					zeroCount = 0;
					outputCharacters += digits[Integer.parseInt(d)] + radices[modulus];
				}
				if (modulus == 0 && zeroCount < 4) {
					outputCharacters += bigRadices[quotient];
				}
			}
			outputCharacters += CN_DOLLAR;
		}

		// Process decimal part if it is larger than 0:
		if (Double.parseDouble(decimal) > 0) {
			for (int i = 0; i < decimal.length(); i++) {
				d = decimal.substring(i, i + 1);
				if (!d.equals("0")) {
					outputCharacters += digits[Integer.parseInt(d)] + decimals[i];
				} else {
					if (i == 0) {
						outputCharacters += CN_ZERO;
					}
				}
			}
		}

		// Confirm and return the final output string:
		if (outputCharacters.equals("")) {
			outputCharacters = CN_ZERO + CN_DOLLAR;
		}
		if (decimal == null || decimal.equals("0")) {
			outputCharacters += CN_INTEGER;
		}

		outputCharacters = CN_SYMBOL + outputCharacters;
		return outputCharacters;
	}

}
