package com.javablog.elasticsearch.query;

import java.io.IOException;

public interface BoolQuery {
    public void boolQuery(String indexName, String typeName) throws IOException ;
    public void boostingQuery(String indexName, String typeName) throws IOException;
}
