package com.hechaodong.bookmanager.dao;

import java.sql.*;
import java.sql.ResultSet;
import com.hechaodong.bookmanager.model.BorrowDetail;

public class BorrowDetailDao {

	public ResultSet list(Connection con,BorrowDetail borrowDetail)throws Exception{

		// 创建一个SrtingBuffer,用来拼接sql语句
		StringBuffer sb=new StringBuffer("SELECT bd.*,u.username,b.book_name from borrowdetail bd,user u,book b where u.id=bd.user_id and b.id=bd.book_id");
		//根据查询语句进行sql语句的拼接操作
		if(borrowDetail.getUserId() != null){
			sb.append(" and u.id = ?");
		}
		if(borrowDetail.getStatus() != null){
			sb.append(" and bd.status = ?");
		}
		if(borrowDetail.getBookId() != null){
			sb.append(" and bd.book_id = ?");
		}
		sb.append("  order by bd.id");

		PreparedStatement prepareStatement=(PreparedStatement) con.prepareStatement(sb.toString());
		if(borrowDetail.getUserId() != null){
			prepareStatement.setInt(1, borrowDetail.getUserId());
		}
		if(borrowDetail.getStatus() != null && borrowDetail.getBookId() != null){
			prepareStatement.setInt(2, borrowDetail.getStatus());
			prepareStatement.setInt(3, borrowDetail.getBookId());
		}

		// 执行sql语句
		return prepareStatement.executeQuery();
	}

	// 添加借阅明细信息
	public int add(Connection con, BorrowDetail borrowDetail) throws Exception {
		String sql = "insert into borrowdetail (user_id,book_id,status,borrow_time) values (?,?,?,?)";
		PreparedStatement preparedStatement=(PreparedStatement) con.prepareStatement(sql);
		preparedStatement.setInt(1, borrowDetail.getUserId());
		preparedStatement.setInt(2, borrowDetail.getBookId());
		preparedStatement.setInt(3, borrowDetail.getStatus());
		preparedStatement.setLong(4, borrowDetail.getBorrowTime());
		return preparedStatement.executeUpdate();
	}

	// 还书
	public int returnBook(Connection con,BorrowDetail detail)throws Exception {
		String sql = "update borrowdetail set status = ? ,return_time = ? where id = ?";
		PreparedStatement preparedStatement=(PreparedStatement) con.prepareStatement(sql);
		preparedStatement.setInt(1, detail.getStatus());
		preparedStatement.setLong(2, detail.getReturnTime());
		preparedStatement.setInt(3, detail.getBorrowId());

		return preparedStatement.executeUpdate();
	}
}
