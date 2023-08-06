package com.hechaodong.bookmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hechaodong.bookmanager.model.BookType;
import com.hechaodong.bookmanager.utils.ToolUtil;

public class BookTypeDao {
	
	// 图书类别添加
	public int add(Connection con,BookType bookType)throws Exception{
		// 先查询是否有一样的类别名
		String sql="select * from book_type where type_name = ?";
		PreparedStatement preparedStatement=con.prepareStatement(sql);
		preparedStatement.setString(1, bookType.getTypeName());
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			/*
			* 遍历结果集
			* 如果结果集中有记录，则表示已存在相同名称的图书类型，那么立即返回数字 2，表示添加失败
			* */
			return 2;
		}

		// 完成类别数据的插入操作
		sql="insert into book_type (type_name,remark) values(?,?)";
		PreparedStatement statement=con.prepareStatement(sql);
		statement.setString(1, bookType.getTypeName());
		statement.setString(2, bookType.getRemark());

		return statement.executeUpdate();
	}

	// 查询图书类别集合
	public ResultSet list(Connection con,BookType bookType)throws Exception{
		StringBuffer sb=new StringBuffer("select * from book_type");
		// 判断传入的图书类型对象 bookType 的名称是否为空
		if(!ToolUtil.isEmpty(bookType.getTypeName())){
			sb.append(" and type_name like '%"+bookType.getTypeName()+"%'");
		}
		PreparedStatement preparedStatement=con.prepareStatement(sb.toString().replaceFirst("and", "where"));

		return preparedStatement.executeQuery();
	}
	
	// 删除图书类别
	public int delete(Connection con,String id)throws Exception{
		//先查询该类别下是否有书籍
		String sql="select b.* from book b left join book_type bt on b.type_id = bt.id where bt.id =? ";
		PreparedStatement preparedStatement=con.prepareStatement(sql);
		preparedStatement.setString(1, id);
		ResultSet query = preparedStatement.executeQuery();// 查询操作
		int number =0;
		while(query.next()){
			number++;
		}
		if(number > 0){ // 表示该类别下存在书籍，那么立即返回数字 3，表示删除失败
			return 3;
		}
		
		//判断类别总数是否大于1
		sql="select * from book_type";
		PreparedStatement pstmt2=con.prepareStatement(sql);
		ResultSet resultSet = pstmt2.executeQuery();
		int count = 0;
		while(resultSet.next()){
			count++;
		}
		if(count<2){ // 如果计数器 count 的值小于 2，表示图书类型的总数小于 2，即只有一个类别，那么立即返回数字 2，表示删除失败
			return 2;
		}
		sql="delete from book_type where id=?";
		PreparedStatement pstmt3=con.prepareStatement(sql);
		pstmt3.setString(1, id);

		return pstmt3.executeUpdate();
	}
	
	// 更新图示类别
	public int update(Connection con,BookType bookType)throws Exception{
		String sql="update book_type set type_name=?,remark=? where id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, bookType.getTypeName());
		pstmt.setString(2, bookType.getRemark());
		pstmt.setInt(3, bookType.getTypeId());

		return pstmt.executeUpdate();
	}
}
