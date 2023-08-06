package com.hechaodong.bookmanager.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpSession;

import com.hechaodong.bookmanager.model.User;

public class ToolUtil {
	//判断一个字符串是否为空，如果不为空则返回false，空字符串或者null返回true
	public static boolean isEmpty(String str){
		if(str != null && !"".equals(str.trim())){
			return false;
		}
		return true;
	}

	//获取当前时间的时间戳，以毫秒为单位
	public static Long getTime(){
		long time = System.currentTimeMillis();
		return time;
	}

	//将给定的时间戳转换为对应的日期时间字符串，格式为"yyyy-MM-dd HH:mm:ss"
	public static String getDateByTime(Long time){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String string = format.format(new Date(time));
		return string;
	}

	//从给定的HttpSession对象中获取保存的当前用户对象(User)
	public static User getUser(HttpSession session){
		User user = (User) session.getAttribute("user");
		return user;
	}

	//将给定的当前用户对象(User)存储到HttpSession对象中，以便在会话期间使用
	public static void setUser(HttpSession session,User user){
		session.setAttribute("user", user);
	}
}
