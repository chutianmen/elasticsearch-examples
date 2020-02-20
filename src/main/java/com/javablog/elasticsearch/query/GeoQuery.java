package com.javablog.elasticsearch.query;

import java.io.IOException;

public interface GeoQuery {
    public void geoDistanceQuery(String indexName, String typeName,double lot,double lon,int distance) throws IOException;
    public void geoBoundingBoxQuery(String indexName, String typeName, double top,double left,double bottom,double right) throws IOException ;
    public void geoPolygonQuery(String indexName, String typeName) throws IOException ;
}
