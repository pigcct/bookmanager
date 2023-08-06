package com.hechaodong.bookmanager.dao;

import java.sql.*;
import java.sql.ResultSet;

import com.hechaodong.bookmanager.model.Book;
import com.hechaodong.bookmanager.utils.ToolUtil;

// 图书信息的增删改查操作
public class BookDao {

    // 图书添加
    public int add(Connection con, Book book) throws Exception {
        String sql = "insert into book (book_name,type_id,author,publish,price,number,status,remark) values(?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(sql);
        preparedStatement.setString(1, book.getBookName());// 以getBookName()第一个参数添加到sql语句的第一个占位符中
        preparedStatement.setInt(2, book.getBookTypeId());
        preparedStatement.setString(3, book.getAuthor());
        preparedStatement.setString(4, book.getPublish());
        preparedStatement.setDouble(5, book.getPrice());
        preparedStatement.setInt(6, book.getNumber());
        preparedStatement.setInt(7, book.getStatus());
        preparedStatement.setString(8, book.getRemark());

        return preparedStatement.executeUpdate();
    }


    // 查询图书信息
    public ResultSet list(Connection con, Book book) throws Exception {
        // 创建StringBuffer对象，来拼接sql语句
        StringBuffer sb = new StringBuffer("select b.*,bt.type_name from book b,book_type bt where b.type_id=bt.id");
        if (!ToolUtil.isEmpty(book.getBookName())) {
            sb.append(" and b.book_name like '%" + book.getBookName() + "%'");
        }
        if (!ToolUtil.isEmpty(book.getAuthor())) {
            sb.append(" and b.author like '%" + book.getAuthor() + "%'");
        }
        if (book.getBookTypeId() != null && book.getBookTypeId() != 0) {
            sb.append(" and b.type_id=" + book.getBookTypeId());
        }
        if (book.getStatus() != null) {
            sb.append(" and b.status=" + book.getStatus());
        }
        if (book.getBookId() != null) {
            sb.append(" and b.id=" + book.getBookId());
        }
        sb.append(" ORDER BY b.status");
        PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(sb.toString());

        return preparedStatement.executeQuery();

    }

    // 查询可借阅的图书信息
    public ResultSet listCan(Connection con, Book book) throws Exception {
        StringBuffer sb = new StringBuffer("select b.*,bt.type_name from book b,book_type bt where type_id=bt.id and b.status = 1");
        // 字符串的拼接查询
        if (!ToolUtil.isEmpty(book.getBookName())) {
            sb.append(" and b.book_name like '%" + book.getBookName() + "%'");
        }
        if (book.getBookId() != null) {
            sb.append(" and b.id=" + book.getBookId());
        }
        PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(sb.toString());

        return preparedStatement.executeQuery();
    }


    // 图书信息删除
    public int delete(Connection con, String id) throws Exception {
        String sql = "delete from book where id=?";
        PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(sql);
        preparedStatement.setString(1, id);

        return preparedStatement.executeUpdate();
    }

    // 图书信息修改
    public int update(Connection con, Book book) throws Exception {
        String sql = "update book set book_name=?,type_id=?,author=?,publish=?,price=?,number=?,status=?,remark=? where id=?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, book.getBookName());
        statement.setInt(2, book.getBookTypeId());
        statement.setString(3, book.getAuthor());
        statement.setString(4, book.getPublish());
        statement.setDouble(5, book.getPrice());
        statement.setInt(6, book.getNumber());
        statement.setInt(7, book.getStatus());
        statement.setString(8, book.getRemark());
        statement.setInt(9, book.getBookId());

        return statement.executeUpdate();
    }

}
