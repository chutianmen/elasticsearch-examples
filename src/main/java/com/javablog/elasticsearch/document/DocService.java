package com.javablog.elasticsearch.document;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.update.UpdateResponse;

import java.io.IOException;
import java.util.Map;

public interface DocService {
    public void add(String index,String type,String json) throws IOException;
    public void deleteDocWithId(String indexName, String typeName, String docId) throws IOException;
    public void bulkDeleteDoc(String indexName, String typeName, String[] docIdArr) throws IOException;
    public void refresh(String indexName) throws IOException;
    public long countDoc(String indexName, String typeName) throws IOException ;
    public void add(String index,String type,String json,String id) throws IOException ;
    public UpdateResponse update(String index, String type, Map<String, Object> map, String id) throws IOException ;
    public BulkResponse bulkUpdateOrInsertDoc(String indexName, String typeName, BulkRequest builder) throws IOException ;
    public void deleteByQuery(String indexName, String typeName,String fieldName,String  fieldValue) throws IOException ;
}
