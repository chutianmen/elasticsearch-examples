package com.javablog.elasticsearch.query;

import java.io.IOException;

public interface HighLightQuery {
    public void hightLightQuery(String indexName, String type,String field,String keyword) throws IOException;
    public void hightLightQueryByFragment(String indexName, String type, int fragmentSize) throws IOException;
    public void hightLightQueryByNumOfFragments(String indexName, String type,  int fragmentSize,int numOfFragments) throws IOException;
    public void hightLightNoMatchSize(String indexName, String type, int fragmentSize,int numOfFragments,int noMatchSize) throws IOException;
}
