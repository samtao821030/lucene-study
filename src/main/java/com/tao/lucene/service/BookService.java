package com.tao.lucene.service;

import com.tao.lucene.dao.BookDao;
import com.tao.lucene.dao.BookDaoImpl;
import com.tao.lucene.po.Book;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/12/21 0021.
 */
public class BookService {
    private BookDao bookDao;

    @Before
    public void init(){
        bookDao = new BookDaoImpl();
    }

    @Test
    public void createIndex() throws SQLException, IOException {
        List<Book> books = bookDao.queryBooks("select * from book");
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3,analyzer);
        Directory directory = FSDirectory.open(new File("E:\\study\\lucene\\index"));
        IndexWriter writer = new IndexWriter(directory,config);
        Document document = null;
        for (Book book:books){
            document = new Document();
            //id field
            Field idField = new TextField("id",book.getId().toString(), Field.Store.YES);
            //name field
            Field nameField = new TextField("name",book.getName(),Field.Store.YES);
            //price field
            Field priceField = new TextField("price",book.getPrice().toString(), Field.Store.YES);
            //pic field
            Field picField = new TextField("pic",book.getPic(), Field.Store.YES);
            //description field
            Field descriptionField = new TextField("description",book.getDescription(), Field.Store.YES);

            document.add(idField);
            document.add(nameField);
            document.add(priceField);
            document.add(picField);
            document.add(descriptionField);

            writer.addDocument(document);
        }
        writer.close();
        System.out.println();
    }

    @Test
    public void searchIndex() throws IOException, ParseException {
        Directory directory = FSDirectory.open(new File("E:\\study\\lucene\\index"));
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        QueryParser parser = new QueryParser("description",new StandardAnalyzer());
        Query query = parser.parse("description:spring");
        TopDocs topDocs = searcher.search(query,10);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for(ScoreDoc scoreDoc:scoreDocs){
            int docId = scoreDoc.doc;
            Document document = searcher.doc(docId);
            System.out.println("name:"+document.get("name"));
            System.out.println("price:"+document.get("price"));
            System.out.println("description:"+document.get("description"));
        }

    }


}
