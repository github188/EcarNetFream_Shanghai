package com.ecar.ecarnetfream.publics.util;

import android.app.Service;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataFormatUtil {
	// 添加字符串
	public static synchronized String addText(StringBuilder sb, String... texts) {
		sb.delete(0, sb.length());
		for (String str : texts) {
			sb.append(str);
		}
		return sb.toString();
	}
	// 添加字符串，不处理StringBuilder
	public static synchronized String appendText(StringBuilder sb, String... texts) {
		for (String str : texts) {
			sb.append(str);
		}
		return sb.toString();
	}

	// float保留一位数字
	public static Float floatTo1pdown(float data) {
		BigDecimal bd = new BigDecimal(data);
		float num = bd.setScale(1, BigDecimal.ROUND_DOWN).floatValue();
		return num;
	}

	/**
	 * @功能：将byte转化为kb或者mb
	 * @param count
	 *            byte长度
	 * @return 小于1m返回kb，否则返回mb
	 */
	public static String byte2Mega(long count) {
		StringBuffer sbf = new StringBuffer();
		if (count < 1024 * 1024) {// 小于1m
			float total = (float) count / 1024;
			sbf.append(floatTo1p(total) + "KB");
		} else {
			float total = (float) count / 1024 / 1024;
			sbf.append(floatTo1p(total) + "MB");
		}

		return sbf.toString();
	}

	/* =====数据格式方法====== */

	// float保留两位数字
	public static Float floatTo2p(float data) {
		BigDecimal bd = new BigDecimal(data);
		float num = bd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		return num;
	}

	// float保留一位数字
	public static Float floatTo1p(float data) {
		BigDecimal bd = new BigDecimal(data);
		float num = bd.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
		return num;
	}

	// double保留两位数字
	public static Double doubleTo2p(Double data) {
		BigDecimal bd = new BigDecimal(data);
		double num = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return num;
	}

	// double保留两位数字
	public static String doubleTo2pRTstr(Double data) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(data);
	}

	// double保留一位数字
	public static Double doubleTo1p(Double data) {
		BigDecimal bd = new BigDecimal(data);
		double num = bd.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		return num;
	}

	// 转为保留二位小数格式
	public static String formatData(double data) {
		DecimalFormat format = new DecimalFormat("##0.00");
		String formatData = format.format(data);
		return formatData;
	}// 转为保留二位小数格式
	public static String formatData(String data) {
		String formatData = "0.00";
		try{
			Double doubleData = Double.parseDouble(data);
			DecimalFormat format = new DecimalFormat("##0.00");
			 formatData = format.format(doubleData);
		}catch (Exception e){

		}finally {
			return formatData;
		}
	}

	// double转换为财务格式
	public static String doubleMoney(String data) {
		Double d = 0D;
		try {
			d = Double.parseDouble(data);
		} catch (Exception e) {
		}

//		BigDecimal bb = new BigDecimal(d);
		DecimalFormat df = new DecimalFormat("##,###");
		// double f1 = bb.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return df.format(Math.abs(d));
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int setDip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int setPx2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	// 获取string资源字符串
	public static String getResourceString(Context context, int id) {
		return context.getResources().getString(id);
	}

	public static StringBuffer md5(String plainText) {
		StringBuffer buf = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			buf = new StringBuffer();
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buf;
	}

	// 此方法是字符转转化为16位md5的方法
	public static String to16Md5(String plainText) {
		String md5Str = null;
		md5Str = md5(plainText).toString().substring(8, 24);
		return md5Str;
	}

	// 此方法是字符转转化为32位md5的方法
	public static String to32Md5(String plainText) {
		String md5Str = null;
		md5Str = md5(plainText).toString();
		return md5Str;
	}

	// 设置控件的高 参数:context 上下文 size;屏幕的倍数 view:需要调整的控件
	public static void setViewsHeightDp(Context context, float dpValue,
			View view) {
		LayoutParams lp = view.getLayoutParams();
		lp.height = DataFormatUtil.setDip2px(context, dpValue);
	}

	// 设置控件的宽 参数:context 上下文 size;屏幕的倍数 view:需要调整的控件
	public static int setViewsWith(Context context, Double size, View view) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) context
				.getSystemService(Service.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
		int sizeX = dm.widthPixels;
		LayoutParams lp = view.getLayoutParams();
		int size1 = (int) (size * sizeX);
		lp.width = size1;
		return size1;

	}

	// 设置控件的高 参数:context 上下文 size;屏幕的倍数 view:需要调整的控件
	public static void setViewsHeightPx(Context context, int pxValue, View view) {
		LayoutParams lp = view.getLayoutParams();
		lp.height = pxValue;
	}

	// 设置控件的宽 参数:context 上下文 size;屏幕的倍数 view:需要调整的控件
	public static void setViewsWidthPx(Context context, int pxValue, View view) {
		LayoutParams lp = view.getLayoutParams();
		lp.width = pxValue;
	}

	/**
	 * @功能：对String 进行encoding 操作
	 * @param：
	 * @return：
	 * @throws Exception
	 */
	public static String getEncodedStr(String str) {
		try {
			return java.net.URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * @功能：检查是否符合金钱格式
	 * @param：
	 * @return：
	 * @throws Exception
	 */
	public static boolean isCoustemMoney(String mobiles) {
		Pattern p1 = Pattern.compile("^[0-9]*[.]{1}[0-9]{1,2}$");
		Matcher m1 = p1.matcher(mobiles);
		Pattern p2 = Pattern.compile("^[0-9]*$");
		Matcher m2 = p2.matcher(mobiles);
		Pattern p3 = Pattern.compile("^[0-9]*[.]{1}$");
		Matcher m3 = p3.matcher(mobiles);
		return m1.matches() || m2.matches() || m3.matches();

	}

	/**
	 * @功能：检查是否符手机格式
	 * @param：
	 * @return：
	 * @throws Exception
	 */
	public static boolean isPhoneNum(String mobiles) {
		Pattern p = Pattern.compile("^[1][358][0-9]{9}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 *
	 * @param spValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	public static String getAllDay(long day) {
		// 定义日期格式
		SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss");
		Date date = new Date(day);
		return matter.format(date);
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

}
