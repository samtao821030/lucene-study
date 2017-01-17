package com.tao.lucene.service;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.SolrParams;
import org.junit.Test;

/**
 * Created by Administrator on 2017/1/17 0017.
 */
public class IndexSearcher {
    @Test
    public void searchIndex01() throws SolrServerException {
        HttpSolrServer server = new HttpSolrServer("http://localhost:8080/solr");

        SolrQuery query = new SolrQuery();
        query.setQuery("product_name:小黄人");

        QueryResponse response = server.query(query);

        SolrDocumentList list = response.getResults();

        long count = list.getNumFound();
        System.out.println("总共发现记录数为："+count);

        for(SolrDocument doc : list){
            System.out.println("id:"+doc.get("id"));
            System.out.println("product_catalog:"+doc.get("product_catalog"));
            System.out.println("product_catalog_name:"+doc.get("product_catalog_name"));
            System.out.println("product_price:"+doc.get("product_price"));
            System.out.println("product_picture:"+doc.get("product_picture"));
            System.out.println("=====================================================");
        }
    }
}
