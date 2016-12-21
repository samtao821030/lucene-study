package com.tao.lucene.dao;

import com.tao.lucene.po.Book;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/12/21 0021.
 */
public interface BookDao {
    //查询书籍
    public List<Book> queryBooks(String sql) throws SQLException;
}
