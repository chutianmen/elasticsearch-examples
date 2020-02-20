package com.javablog.elasticsearch.query;

import java.io.IOException;

public interface FilterQuery {
    public void filterInBoolQuery(String indexName, String typeName) throws IOException;
    public void rangeQuery(String indexName, String typeName, String fieldName, int from,int to) throws IOException;
    public void existQuery(String indexName, String typeName, String fieldName) throws IOException;
    public void typeQuery(String typeName) throws IOException ;
}
