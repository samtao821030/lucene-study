package com.tao.lucene.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 2016/12/21 0021.
 */
public class DBUtil {
    private static DBUtil dbUtil = null;
    private DruidDataSource dds = null;

    private DBUtil(){
        try {
            dds = (DruidDataSource) DruidDataSourceFactory.createDataSource(loadPropertyFile("db_server.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public static synchronized DBUtil getInstance() throws Exception {
        if(dbUtil==null){
            dbUtil = new DBUtil();
        }
        return dbUtil;
    }

    public static Properties loadPropertyFile(String fullFile){
        String filePath = null;
        if (null == fullFile || fullFile.equals(""))
            throw new IllegalArgumentException(
                    "Properties file path can not be null : " + fullFile);
        filePath = DBUtil.class.getClassLoader().getResource("")
                .getPath() + fullFile;
        //webRootPath = new File(webRootPath).getParent();
        InputStream inputStream = null;
        Properties p = null;
        try {
            inputStream = new FileInputStream(new File(filePath));
            p = new Properties();
            p.load(inputStream);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Properties file not found: "
                    + fullFile);
        } catch (IOException e) {
            throw new IllegalArgumentException(
                    "Properties file can not be loading: " + fullFile);
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return p;
    }

    //查询记录
    public List<JSONObject> queryResults(String sql) throws SQLException {
        List<JSONObject> results = new ArrayList<JSONObject>();
        if(dds!=null) {
            DruidPooledConnection conn = dds.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            JSONObject record = null;
            while(rs.next()){
                record = new JSONObject();
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                for(int i=1;i<=columnCount;i++){
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = rs.getObject(columnName);
                    record.put(columnName,columnValue);
                }
                results.add(record);
            }
        }
        return results;
    }

    public static void main(String[] args) throws Exception {
        DBUtil.getInstance().queryResults("select * from book");
    }
}
