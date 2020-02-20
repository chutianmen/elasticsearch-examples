package com.javablog.elasticsearch.query;

import java.io.IOException;
import java.util.Map;

public interface AggregationQuery {
    public  void AvgAggregations(String indexName, String typeName, String field) throws IOException ;
    public  void cardinalityAggregations(String indexName, String typeName, String field) throws IOException;
    public  void dateHistogramAggregation(String indexName, String typeName, String field) throws IOException;
    public  void dateRangeAggregation(String indexName, String typeName, String field) throws IOException;
    public  void extendedStatsAggregation(String indexName, String typeName, String field) throws IOException ;
    public void filterAggregation(String indexName, String typeName, String termField,String termValue) throws IOException ;
    public  void histogramAggregation(String indexName, String typeName, String field,int interval) throws IOException ;
    public  void histogramDateAggregation(String indexName, String typeName, String field,int interval) throws IOException ;
    public void termsAggregation(String indexName, String typeName, long startTime, long endTime) throws IOException;
    public  void geoDistanceAggregation(String indexName, String typeName) throws IOException ;
}
