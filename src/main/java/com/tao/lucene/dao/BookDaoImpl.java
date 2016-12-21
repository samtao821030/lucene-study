package com.tao.lucene.dao;

import com.alibaba.fastjson.JSONObject;
import com.tao.lucene.po.Book;
import com.tao.lucene.util.DBUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/21 0021.
 */
public class BookDaoImpl implements BookDao {
    private DBUtil dbUtil = DBUtil.getInstance();

    public List<Book> queryBooks(String sql) throws SQLException {
        List<Book> books = new ArrayList<Book>();
        List<JSONObject> results = dbUtil.queryResults(sql);
        Book book = null;
        for(JSONObject jsonObject:results){
            book = JSONObject.parseObject(jsonObject.toJSONString(),Book.class);
            books.add(book);
        }
        return books;
    }

}
