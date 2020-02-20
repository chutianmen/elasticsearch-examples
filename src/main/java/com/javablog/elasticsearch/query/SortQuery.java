package com.javablog.elasticsearch.query;

import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;

public interface SortQuery {
    public void queryMatch(String indexName, String typeName, String field,String keyWord) throws IOException;
    public void sortQuery(String indexName, String typeName, String field, String keyWord, String sort, SortOrder sortOrder) throws IOException ;
    public void multSortQuery(String indexName, String typeName, String field,String keyWord,String sort1,String sort2,SortOrder sortOrder) throws IOException;
}
