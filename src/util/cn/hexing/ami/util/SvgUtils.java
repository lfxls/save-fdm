package cn.hexing.ami.util;

public class SvgUtils {
	
	/**
	 * 根据圆心点x坐标，半径，角度计算在圆上的x坐标点
	 * @param x 圆心点x坐标
	 * @param s 圆半径
	 * @param d 角度
	 * @return
	 */
	public static int getX(int x, int s, double d){
		return (int)Math.round((x+s*cos(d)));
	}
	/**
	 * 根据起点，半径，角度计算在圆上的y坐标点
	 * @param y 圆心点y坐标
	 * @param s 半径
	 * @param d 角度
	 * @return
	 */
	public static int getY(int y,int s, double d){
		return (int)Math.round((y-s*sin(d)));
	}
	private static double cos(double a){
		double hd= 2*Math.PI/360*a;
		return Math.cos(hd);
	}
	private static double sin(double a){
		double hd= 2*Math.PI/360*a;
		return Math.sin(hd);
	}
}
