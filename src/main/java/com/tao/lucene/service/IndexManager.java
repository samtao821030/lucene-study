package com.tao.lucene.service;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Administrator on 2017/1/12 0012.
 */
public class IndexManager {
    @Test
    public void insertAndUpdateIndex() throws IOException, SolrServerException {
        HttpSolrServer server = new HttpSolrServer("http://localhost:8080/solr");
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id","c001");
        doc.addField("name","solr test");
        server.add(doc);
        server.commit();
    }

    @Test
    public void deleteIndex() throws IOException, SolrServerException{
        HttpSolrServer server = new HttpSolrServer("http://localhost:8080/solr");

        server.deleteById("c001");
        server.commit();
    }

}
