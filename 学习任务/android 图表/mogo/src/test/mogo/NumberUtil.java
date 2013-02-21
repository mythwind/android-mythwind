package test.mogo;

import java.text.DecimalFormat;

public class NumberUtil {
	/** * 保留小数点后2位 * @param d * @return */
	public static String getDecimal(double d) {
		DecimalFormat df2 = new DecimalFormat("0.00");
		return df2.format(d);
	}
}