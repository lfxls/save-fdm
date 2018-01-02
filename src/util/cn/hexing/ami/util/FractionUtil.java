package cn.hexing.ami.util;

import java.math.BigDecimal;


/**
 * @Description 分数计算工具类
 * @author jun
 * @Copyright 2013 hexing Inc. All rights reserved
 * @time：2013-8-9
 * @version AMI3.0
 */
public class FractionUtil {
	/**
	 * 分数相加
	 * @param firstFraction 格式:1/5
	 * @param secondFraction 格式:1/5
	 * @return
	 */
	public static BigDecimal add(String firstFraction,String secondFraction) {
		if (firstFraction.indexOf("/")==-1 || secondFraction.indexOf("/")==-1) {
			return new BigDecimal(0);
		}
		String[] first = firstFraction.split("/");
		String[] second = secondFraction.split("/");
		return add(Integer.parseInt(first[0]),Integer.parseInt(first[1]),Integer.parseInt(second[0]),Integer.parseInt(second[1]));
	}
	/**
	 * 分数相加
	 * @param first_numerator
	 * @param first_denominator
	 * @param second_numrator
	 * @param second_denominator
	 */
	public static BigDecimal add(int first_numerator, int first_denominator,
			int second_numrator, int second_denominator) {
		// 以下代码能够在控制台上显示结果
		// 需要调用求最大公约数的函数
		// 需要调用求最小公倍数的函数
		int denominator;
		int numerator;

		if (first_denominator == second_denominator) // 分母相同时加分子
		{
			denominator = first_denominator;
			numerator = first_numerator + second_numrator;
		} else // 否则同分比较分子
		{
			denominator = first_denominator * second_denominator;
			numerator = first_numerator * second_denominator
					+ first_denominator * second_numrator;
		}
		int gcd = gcd(numerator, denominator);
		denominator = denominator / gcd;
		numerator = numerator / gcd;
		return new BigDecimal(numerator).divide(new BigDecimal(denominator),4,BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 分数相减
	 * @param firstFraction 格式:1/5
	 * @param secondFraction 格式:1/5
	 * @return
	 */
	public static BigDecimal subtract(String firstFraction,String secondFraction) {
		if (firstFraction.indexOf("/")==-1 || secondFraction.indexOf("/")==-1) {
			return new BigDecimal(0);
		}
		String[] first = firstFraction.split("/");
		String[] second = secondFraction.split("/");
		return subtract(Integer.parseInt(first[0]),Integer.parseInt(first[1]),Integer.parseInt(second[0]),Integer.parseInt(second[1]));
	}
	
	/**
	 * 分数相减
	 * @param first_numerator
	 * @param first_denominator
	 * @param second_numrator
	 * @param second_denominator
	 */
	public static BigDecimal subtract(int first_numerator, int first_denominator,
			int second_numrator, int second_denominator) {
		// 以下代码能够在控制台上显示结果
		// 需要调用求最大公约数的函数

		int denominator;
		int numerator;

		if (first_denominator == second_denominator) // 分母相同时加分子
		{
			denominator = first_denominator;
			numerator = first_numerator - second_numrator;
		} else // 否则同分比较分子
		{
			denominator = first_denominator * second_denominator;
			numerator = first_numerator * second_denominator
					- first_denominator * second_numrator;
		}
		int gcd = gcd(numerator, denominator);
		denominator = denominator / gcd;
		numerator = numerator / gcd;
		return new BigDecimal(numerator).divide(new BigDecimal(denominator),4,BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 分数相乘
	 * @param firstFraction 格式:1/5
	 * @param secondFraction 格式:1/5
	 * @return
	 */
	public static BigDecimal multiply(String firstFraction,String secondFraction) {
		if (firstFraction.indexOf("/")==-1 || secondFraction.indexOf("/")==-1) {
			return new BigDecimal(0);
		}
		String[] first = firstFraction.split("/");
		String[] second = secondFraction.split("/");
		return multiply(Integer.parseInt(first[0]),Integer.parseInt(first[1]),Integer.parseInt(second[0]),Integer.parseInt(second[1]));
	}
	
	/**
	 * 分数相乘
	 * @param first_numerator
	 * @param first_denominator
	 * @param second_numerator
	 * @param second_denominator
	 */
	public static BigDecimal multiply(int first_numerator, int first_denominator,
			int second_numerator, int second_denominator) {
		// 以下代码能够在控制台上显示结果
		// 需要调用求最大公约数的函数

		int denominator;
		int numerator;

		denominator = first_denominator * second_denominator;
		numerator = first_numerator * second_numerator;

		int gcd = gcd(numerator, denominator);
		denominator = denominator / gcd;
		numerator = numerator / gcd;
		return new BigDecimal(numerator).divide(new BigDecimal(denominator),4,BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 分数相除
	 * @param firstFraction 格式:1/5
	 * @param secondFraction 格式:1/5
	 * @return
	 */
	public static BigDecimal divide(String firstFraction,String secondFraction) {
		if (firstFraction.indexOf("/")==-1 || secondFraction.indexOf("/")==-1) {
			return new BigDecimal(0);
		}
		String[] first = firstFraction.split("/");
		String[] second = secondFraction.split("/");
		return divide(Integer.parseInt(first[0]),Integer.parseInt(first[1]),Integer.parseInt(second[0]),Integer.parseInt(second[1]));
	}
	
	/**
	 * 分数相除
	 * @param first_numerator
	 * @param first_denominator
	 * @param second_numerator
	 * @param second_denominator
	 */
	public static BigDecimal divide(int first_numerator, int first_denominator,
			int second_numerator, int second_denominator) {
		// 以下代码能够在控制台上显示结果
		// 需要调用求最大公约数的函数

		int denominator;
		int numerator;

		numerator = first_numerator * second_denominator;
		denominator = first_denominator * second_numerator;

		int gcd = gcd(numerator, denominator);
		denominator = denominator / gcd;
		numerator = numerator / gcd;
		return new BigDecimal(numerator).divide(new BigDecimal(denominator),4,BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 分树转数值
	 * @param numerator
	 * @param denominator
	 * @return
	 */
	public static BigDecimal eval(int numerator, int denominator){
		return new BigDecimal(numerator).divide(new BigDecimal(denominator));
	}
	
	/**
	 * 分树转数值
	 * @param fraction 格式:1/5
	 * @return
	 */
	public static BigDecimal eval(String fraction){
		if (fraction.indexOf("/")==-1) {
			return new BigDecimal(0);
		}
		String[] arr = fraction.split("/");
		return new BigDecimal(arr[0]).divide(new BigDecimal(arr[1]));
	}
	
	private static int gcd(int x, int y) {
		int r;
		while (y != 0) {
			r = x % y;
			x = y;
			y = r;
		}
		return x;
	}
}
