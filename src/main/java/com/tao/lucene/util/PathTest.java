package com.tao.lucene.util;

import org.junit.Test;

/**
 * Created by Administrator on 2016/12/21 0021.
 */
public class PathTest {

    public static void TestFile(){
        String path = PathTest.class.getClassLoader().getResource("").getPath();
        System.out.println(path);
    }

    public static void main(String[] args) {
        PathTest.TestFile();
    }
}
