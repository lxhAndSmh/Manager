package com.botpy.loanbanker.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.Build;
import android.os.Environment;
import android.support.v4.view.MotionEventCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.botpy.loanbanker.application.AppConfig;

import java.io.Closeable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Utils {
	
	public static int flag = 0;
	private static final int VERSION_CODE = 1;
	private static final int VERSION_NAME = 2;

	/**
	 * 判断是否有sdcard
	 *
	 * @return
	 */
	public static boolean hasSDCard() {
		boolean b = false;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			b = true;
		}
		return b;
	}

	/**
	 * 得到sdcard路径
	 * @return
	 */
	public static String getExtPath() {
		String path = "";
		if (hasSDCard()) {
			path = Environment.getExternalStorageDirectory().getPath();
		}
		return path;
	}

	/**
	 * 得到/data/data/yanbin.imagedownload目录
	 * @param mActivity
	 * @return
	 */
	public String getPackagePath(Activity mActivity){
		return mActivity.getFilesDir().toString();
	}

	/**
	 * 根据url得到图片名
	 * @param url
	 * @return
	 */
	public String getImageName(String url) {
		String imageName = "";
		if(url != null){
			imageName = url.substring(url.lastIndexOf("/") + 1);
		}
		return imageName;
	}
	
	/**
	 * 检查验证码是否是正确的数字
	 * @param code
	 * @return
	 */
	public static boolean isValidateCodeOk(String code){
		String regex = "\\d{6}";
		return code.matches(regex);
	}

	/**
	 * 获取手机的设备IMEI号码
	 * @return the device id of the phone
	 */
	public static String getPhoneIMEI(Context context){
		TelephonyManager telephoneManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String device = telephoneManager.getDeviceId();

		return device;
	}
	
	
	/**
	 * 数据使用 Hmac 签名
	 */
	public static String cipherSignature(String key, String signalString) {
		try {
			Mac mac = Mac.getInstance("HmacSHA256");
			byte[] secretByte = key.getBytes("UTF-8");
			byte[] dataBytes = signalString.getBytes("UTF-8");
			
			SecretKey secret = new SecretKeySpec(secretByte, "HmacSHA256");
			mac.init(secret);
			byte[] doFinal = mac.doFinal(dataBytes);
//			byte[] hexB = new Hex().encode(doFinal);
			String checksum = new String(/*hexB*/);
			
			return checksum.toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 产生一个时间戳
	 */
	public static String getTimeStamp() {
		return String.valueOf((int)(System.currentTimeMillis() / 1000));
	}
	
	public static int getCurrentTimeMillis() {
		return ((Long) System.currentTimeMillis()).intValue();
	}
	
	/**
	 * @return 获取手机型号
	 */
	public static String getPhoneModel(){
		return Build.MODEL;
	}

	/**
	 * @return 获取手机品牌
     */
	public static String getPhoneBrand() {
		return Build.BRAND;
	}

	/**
	 * @return 获取手机系统的版本号
	 */
	public static String getSystemVersion(){
		return Build.VERSION.RELEASE;
	}

	/**
	 * @return 获取系统的SDK版本号码
	 */
	public static int getSDKVersion(){
		return Build.VERSION.SDK_INT;
	}
	
	/**
	 * 获取应用程序的信息
	 * @param context	上下文
	 * @param flag		VERSION_NAME/VERSION_CODE
	 * @return
	 */
	private static String getAppInfo(Context context, int flag){
		PackageManager pm = context.getPackageManager();
		PackageInfo packageInfo = null;
		int versionCode = 0;
		String versionName = "";
		try {
			packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
			versionCode = packageInfo.versionCode;
			versionName = packageInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		if (flag == VERSION_CODE) {
			return String.valueOf(versionCode);
		} else if (flag == VERSION_NAME) {
			return versionName;
		} else {
			throw new IllegalArgumentException("参数不合法");
		}
	}
	
	/**
	 * 检车本地的软件版本号码
	 * @return 
	 */
	public static String getNowBuildCode(Context context){
        return getAppInfo(context, VERSION_CODE);
	}
	
	/**
	 * 获取请求网络的API名称
	 *
	 */
	public static String getAPIName(){
		return "Loan-Banker";
	}

	public static String getAppVersion() {
		return getAppVersion(AppConfig.getContext());
	}

	public static String getAppVersionCode() {
		return getAppVersionCode(AppConfig.getContext());
	}

	/**
	 * 获取APP的主要版本号
	 * @param context
	 * @return
	 */
	public static String getAppVersion(Context context){
		return getAppInfo(context, VERSION_NAME);
	}
	
	/**
	 * 获取APP的次要版本号———versionCode
	 * @param context
	 * @return
	 */
	public static String getAppVersionCode(Context context){
		return getAppInfo(context, VERSION_CODE);
	}
	
	/** 
	 * 将毫秒转换成时间值
	 */
	public static String formatDuring(long mss) {
		
	    long days = mss / (1000 * 60 * 60 * 24);  
	    long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);  
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);  
	    long seconds = (mss % (1000 * 60)) / 1000;  
	    return days + " days -" + hours + " hours " + minutes + " minutes " + seconds + " seconds ";
	}
	
	
	/** 
	 * 将毫秒转换成时间值
	 */
	public static String formatDateTime(long mss) {
		
//		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);  
		long minutes = (mss % ( 60 * 60)) /  60;  
		long seconds = (mss % 60) ; 
		if(seconds<10){
			return   minutes + " 分 0" + seconds + " 秒 ";
		}else{
			return   minutes + " 分 " + seconds + " 秒 ";
		}
	}
	
	/** 
	 * 将毫秒转换成分钟
	 */
	public static long formatDuringMinutes(long mss) { 
		
	    long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);  
	    return minutes;
	}
	
	
	
	public static Toast mToast;

	/**
	 * 显示Toast,如果上次的效果还未消失，则不再进行toast的显示
	 * @param mContext
	 * @param msg
	 */
	public static void showToast(Context mContext, String msg) {
		if (mToast == null) {
			mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
		}
		mToast.setText(msg);
		mToast.show();
	}
	
	/**
	 * dip 转换成 px
	 * @param dip
	 * @param context
	 * @return
	 */
	public static float dip2Dimension(float dip, Context context) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, displayMetrics);
	}
	/**
	 * @param dip
	 * @param context
	 * @param complexUnit {@link TypedValue#COMPLEX_UNIT_DIP} {@link TypedValue#COMPLEX_UNIT_SP}}
	 * @return
	 */
	public static float toDimension(float dip, Context context, int complexUnit) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		return TypedValue.applyDimension(complexUnit, dip, displayMetrics);
	}

	public static String getActionName(MotionEvent event) {
		String action = "unknow";
		switch (MotionEventCompat.getActionMasked(event)) {
		case MotionEvent.ACTION_DOWN:
			action = "ACTION_DOWN";
			break;
		case MotionEvent.ACTION_MOVE:
			action = "ACTION_MOVE";
			break;
		case MotionEvent.ACTION_UP:
			action = "ACTION_UP";
			break;
		case MotionEvent.ACTION_CANCEL:
			action = "ACTION_CANCEL";
			break;
		case MotionEvent.ACTION_SCROLL:
			action = "ACTION_SCROLL";
			break;
		case MotionEvent.ACTION_OUTSIDE:
			action = "ACTION_SCROLL";
			break;
		default:
			break;
		}
		return action;
	}
	
	/**
	 * Convert Dp to Pixel
	 */
	public static int dpToPx(float dp, Resources resources){
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
		return (int) px;
	}
	
	public static int getRelativeTop(View myView) {
//	    if (myView.getParent() == myView.getRootView())
	    if(myView.getId() == android.R.id.content)
	        return myView.getTop();
	    else
	        return myView.getTop() + getRelativeTop((View) myView.getParent());
	}
	
	public static int getRelativeLeft(View myView) {
//	    if (myView.getParent() == myView.getRootView())
		if(myView.getId() == android.R.id.content)
			return myView.getLeft();
		else
			return myView.getLeft() + getRelativeLeft((View) myView.getParent());
	}

	public static String uuid() {
		StringBuilder sb = new StringBuilder();
		int start = 48, end = 58;
		appendChar(sb, start, end);
		appendChar(sb, 65, 90);
		appendChar(sb, 97, 123);
		String charSet = sb.toString();
		StringBuilder sb1 = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 24; i++) {
			int len = charSet.length();
			int pos = random.nextInt(len);
			sb1.append(charSet.charAt(pos));
		}
		return sb1.toString();
	}

	public static void appendChar(StringBuilder sb, int start, int end) {
		int i;
		for (i = start; i < end; i++) {
			sb.append((char) i);
		}
	}

	public static void closeQuietly(Closeable closeable) {
		try {
			closeable.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 隐藏软键盘
	 */
	public static void hideKeyboard(Context context) {
		InputMethodManager manager = (InputMethodManager) ( context)
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		manager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus()
						.getApplicationWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);

	}

	/**
	 * 显示键盘
	 */
	public static void setKeyboard(Context context) {
		InputMethodManager imm = (InputMethodManager) ((Activity) context)
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * MD5加密
	 */
	public static String md5(String string) {
		if (TextUtils.isEmpty(string)) {
			return "";
		}

		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte[] bytes = md5.digest(string.getBytes());
			String result = "";
			for (byte b : bytes) {
				String temp = Integer.toHexString(b & 0xff);
				if (temp.length() == 1) {
					temp = "0" + temp;
				}
				result += temp;
			}
			return result.toLowerCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}

