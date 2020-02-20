package com.javablog.elasticsearch.document;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;

import java.io.IOException;

public interface IndexService {
    public void createIndex(String index, String type, CreateIndexRequest request) throws IOException;

    public void deleteIndex(String index) throws IOException;

    public boolean existsIndex(String index) throws IOException;
}

