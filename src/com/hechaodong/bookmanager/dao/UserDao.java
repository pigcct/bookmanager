package com.hechaodong.bookmanager.dao;

import java.sql.*;
import com.hechaodong.bookmanager.model.User;
import com.hechaodong.bookmanager.utils.ToolUtil;

import java.sql.ResultSet;
public class UserDao {
	
	public User login(Connection con, User user)throws Exception {

		// 定义一个User类型的变量，用来记录查询的结果
		User resultUser = null;

		// 定义sql语句
		String sql = "select * from user where username=? and password=? and role = ?";
	    PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(sql);
		preparedStatement.setString(1,user.getUserName());
		preparedStatement.setString(2,user.getPassword());
		preparedStatement.setInt(3,user.getRole());

		// 执行查询操作
	    ResultSet rs = preparedStatement.executeQuery();
	    if(rs.next()){
	    	resultUser = new User();
	    	resultUser.setUserId(rs.getInt("id"));
	    	resultUser.setUserName(rs.getString("username"));
	    	resultUser.setSex(rs.getString("sex"));
	    	resultUser.setPhone(rs.getString("phone"));
	    }
		return resultUser;
	}

	// 添加用户信息
	public int addUser(Connection con, User user) throws Exception{
		//查询注册用户名是否存在
		String sql = "select * from user where userName=? ";
	    PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
		pstmt.setString(1,user.getUserName());
	    ResultSet rs = pstmt.executeQuery();
	    if(rs.next()){
	    	return 2;
	    }

		// 执行插入操作
	    String sql2="insert into user (username,password,role,sex,phone) values (?,?,?,?,?)";
	    PreparedStatement pstmt2=(PreparedStatement) con.prepareStatement(sql2);
		pstmt2.setString(1, user.getUserName());
		pstmt2.setString(2, user.getPassword());
		pstmt2.setInt(3, user.getRole());
		pstmt2.setString(4,user.getSex());
		pstmt2.setString(5,user.getPhone());

		return pstmt2.executeUpdate();
	}
	

	// 查询用户信息
	public ResultSet list(Connection con,User user)throws Exception{
		StringBuffer sb=new StringBuffer("select * from user where role = 1");
		if(!ToolUtil.isEmpty(user.getUserName())){
			sb.append(" and username like '%"+user.getUserName()+"%'");
		}
		PreparedStatement pstmt=(PreparedStatement) con.prepareStatement(sb.toString());

		return pstmt.executeQuery();
	}

	// 更新信息
	public int update(Connection con,User user)throws Exception{
		String sql="update user set username=?,password=?,sex=?,phone=? where id=?";
		PreparedStatement pstmt=(PreparedStatement) con.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, user.getPassword());
		pstmt.setString(3, user.getSex());
		pstmt.setString(4, user.getPhone());
		pstmt.setInt(5, user.getUserId());

		return pstmt.executeUpdate();
	}
}
