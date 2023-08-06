package com.hechaodong.bookmanager.utils;

import java.sql.*;

public class DbUtil {
	// 定义数据库连接字符串
	private String Driver = "com.mysql.cj.jdbc.Driver";	// 数据库驱动
	private String Url = "jdbc:mysql://localhost:3306/mybookmanager?serverTimezone=UTC&useSSL=false";
	private String UserName = "root";
	private String Password = "258088";

	// 连接数据库
	public Connection getConnection()throws Exception{
	    Class.forName(Driver);
		Connection con = (Connection) DriverManager.getConnection(Url,UserName,Password);
		return con;
	}

	// 关闭数据库
	public void closeCon (Connection con)throws Exception {
		if(con!=null){
			con.close();
		}
	}

}
