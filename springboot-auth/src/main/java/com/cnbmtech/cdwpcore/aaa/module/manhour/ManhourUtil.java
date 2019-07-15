
/**  
* @Title: ManhourUtil.java
* @Package com.cnbmtech.cdwpcore.aaa.module.manhour
* @Description: TODO
* @author zhengangwu
* @date 2018年4月11日
* @version V1.0  
*/

package com.cnbmtech.cdwpcore.aaa.module.manhour;

/**
 * @ClassName: ManhourUtil
 * @Description: TODO
 * @author zhengangwu
 * @date 2018年4月11日
 *
 */

public class ManhourUtil {

	/**
	 * @Title: main @Description: TODO @param @param args @return void @throws
	 */

	public static int[] genManhour(final int year, final int month) {
		return null;
	}

	public static int[] genManhour(final int year) {
		return null;
	}

	public static int getDayNumber(final int year, final int month) {
		int day = 0;
		switch (month) {
		case 1:
			;
		case 3:
			;
		case 5:
			;
		case 7:
			;
		case 8:
			;
		case 10:
			;
		case 12:
			day = 31;
			break;
		case 4:
			;
		case 6:
			;
		case 9:
			;
		case 11:
			day = 30;
			break;
		case 2:
			day = isLeapYear(year)? 29 : 28;
		default:
			break;
		}
		return day;
	}

	public static boolean isLeapYear(final int year){
		return ((year % 4 == 0 && year % 100 != 0) || year % 400 ==0);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
