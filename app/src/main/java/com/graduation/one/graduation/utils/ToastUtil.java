package com.graduation.one.graduation.utils;

import android.widget.Toast;

import com.graduation.one.graduation.MyApplication;


/**
 * 土司工具
 */
public class ToastUtil {
	/**
	 * @param msg	打印文本内容
	 */
	public static void show(String msg) {
		Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * @param msg	打印文本内容
	 */
	public static void showLong(String msg) {
		Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_LONG).show();
	}
}
