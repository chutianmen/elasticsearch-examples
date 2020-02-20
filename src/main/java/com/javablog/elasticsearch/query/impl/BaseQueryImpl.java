package com.javablog.elasticsearch.query.impl;

import com.javablog.elasticsearch.query.BaseQuery;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

@Service("baseQuery")
public class BaseQueryImpl implements BaseQuery {
    private final static Logger log = LoggerFactory.getLogger(BaseQueryImpl.class);
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 查询某个字段里含有某个关键词的文档
     * @param indexName   索引名
     * @param typeName    TYPE
     * @param fieldName   字段名称
     * @param fieldValue  字段值
     * @return 返回结果列表
     * @throws IOException
     */
    public List<Map<String,Object>> termQuery(String indexName, String typeName, String fieldName, String fieldValue) throws IOException {
        List<Map<String,Object>> response =new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.types(typeName);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termQuery(fieldName, fieldValue));
        //分页
        sourceBuilder.from(0);
        sourceBuilder.size(10);
        searchRequest.source(sourceBuilder);
        log.info("source:" + searchRequest.toString());
        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        System.out.println("count:"+hits.totalHits);
        SearchHit[] h =  hits.getHits();
        for (SearchHit hit : h) {
            System.out.println("结果"+hit.getSourceAsMap());
            response.add(hit.getSourceAsMap());
        }
        return response;
    }

    /**
     * 查询某个字段里含有多个关键词的文档
     * @param indexName   索引名
     * @param typeName    TYPE
     * @param fieldName   字段名称
     * @param fieldValues  字段值
     * @return 返回结果列表
     * @throws IOException
     */
    public List<Map<String,Object>> termsQuery(String indexName, String typeName, String fieldName, String... fieldValues) throws IOException {
        List<Map<String,Object>> response =new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.types(typeName);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termsQuery(fieldName,fieldValues));
        sourceBuilder.from(0);
        sourceBuilder.size(10);
        searchRequest.source(sourceBuilder);
        log.info("source:" + searchRequest.source());
        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        System.out.println("count:"+hits.totalHits);
        SearchHit[] h =  hits.getHits();
        for (SearchHit hit : h) {
            System.out.println("结果"+hit.getSourceAsMap());
            response.add(hit.getSourceAsMap());
        }
        return response;
    }

    /**
     * 查询所有文档
     * @param indexName   索引名称
     * @param typeName    TYPE
     * @throws IOException
     */
    @Override
    public void queryAll(String indexName, String typeName) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.types(typeName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(matchAllQuery());
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(20);
        searchRequest.source(searchSourceBuilder);
        log.info("source:" + searchRequest.source());
        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        System.out.println(hits.totalHits);
        SearchHit[] h =  hits.getHits();
        for (SearchHit hit : h) {
            System.out.println(hit.getSourceAsMap());
        }
    }

    /**
     * match 搜索
     * @param indexName 索引名称
     * @param typeName  TYPE名称
     * @param field     字段
     * @param keyWord   搜索关键词
     * @throws IOException
     */
    @Override
    public void queryMatch(String indexName, String typeName, String field,String keyWord) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.types(typeName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery(field,keyWord));
        searchRequest.source(searchSourceBuilder);
        log.info("source:" + searchRequest.source());
        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        System.out.println("count:"+hits.totalHits);
        SearchHit[] h =  hits.getHits();
        for (SearchHit hit : h) {
            System.out.println("结果"+hit.getSourceAsMap());
        }
    }

    /**
     * 布尔match查询
     * @param indexName    索引名称
     * @param typeName     TYPE名称
     * @param field        字段名称
     * @param keyWord      关键词
     * @param op           该参数取值为or 或 and
     * @throws IOException
     */
    @Override
    public void queryMatchWithOperate(String indexName, String typeName, String field,String keyWord,Operator op) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.types(typeName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery(field,keyWord).operator(op));
        searchRequest.source(searchSourceBuilder);
        log.info("source:" + searchRequest.source());
        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        System.out.println("count:"+hits.totalHits);
        SearchHit[] h =  hits.getHits();
        for (SearchHit hit : h) {
            System.out.println("结果"+hit.getSourceAsMap());
        }
    }

    /**
     *该查询通过字段fields参数作用在多个字段上。
     * @param indexName  索引名称
     * @param typeName   TYPE名称
     * @param keyWord    关键字
     * @param fieldNames  字段
     * @throws IOException
     */
    @Override
    public void queryMulitMatch(String indexName, String typeName,String keyWord,String ...fieldNames) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.types(typeName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(keyWord,fieldNames));
        searchRequest.source(searchSourceBuilder);
        log.info("source:" + searchRequest.source());
        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        System.out.println("count:"+hits.totalHits);
        SearchHit[] h =  hits.getHits();
        for (SearchHit hit : h) {
            System.out.println("结果"+hit.getSourceAsMap());
        }
    }

    /**
     * 对查询词语分析后构建一个短语查询
     * @param indexName    索引名称
     * @param typeName     TYPE名称
     * @param fieldName    字段名称
     * @param keyWord      关键字
     * @throws IOException
     */
    @Override
    public void queryMatchPhrase(String indexName, String typeName,String fieldName,String keyWord) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.types(typeName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchPhraseQuery(fieldName,keyWord));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        System.out.println("count:"+hits.totalHits);
        SearchHit[] h =  hits.getHits();
        for (SearchHit hit : h) {
            System.out.println("结果"+hit.getSourceAsMap());
        }
    }

    @Override
    public void queryMatchPrefixQuery(String indexName, String typeName,String fieldName,String keyWord) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.types(typeName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchPhrasePrefixQuery(fieldName,keyWord).maxExpansions(1));
        searchRequest.source(searchSourceBuilder);
        log.info("source:" + searchRequest.source());
        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        System.out.println("count:"+hits.totalHits);
        SearchHit[] h =  hits.getHits();
        for (SearchHit hit : h) {
            System.out.println("结果"+hit.getSourceAsMap());
        }
    }

    /**
     * 查出指定_id的文档
     * @param indexName   索引名称
     * @param typeName    TYPE名称
     * @param ids         _id值
     * @throws IOException
     */
    @Override
    public void idsQuery(String indexName, String typeName,String ... ids) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.types(typeName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.idsQuery().addIds(ids));
        searchRequest.source(searchSourceBuilder);
        log.info("string:" + searchRequest.source());
        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        System.out.println("count:"+hits.totalHits);
        SearchHit[] h =  hits.getHits();
        for (SearchHit hit : h) {
            System.out.println("结果"+hit.getSourceAsMap());
        }
    }

    /**
     * 查找某字段以某个前缀开头的文档
     * @param indexName 索引名称
     * @param typeName  TYPE名称
     * @param field     字段
     * @param prefix    前缀
     * @throws IOException
     */
    @Override
    public void prefixQuery(String indexName, String typeName, String field, String prefix) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.types(typeName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.prefixQuery(field,prefix));
        searchRequest.source(searchSourceBuilder);
        log.info("string:" + searchRequest.source());
        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        System.out.println("count:"+hits.totalHits);
        SearchHit[] h =  hits.getHits();
        for (SearchHit hit : h) {
            System.out.println("结果"+hit.getSourceAsMap());
        }
    }

    /**
     * 查找某字段以某个前缀开头的文档
     * @param indexName 索引名称
     * @param typeName  TYPE名称
     * @param field     字段
     * @param value     查询关键字
     * @throws IOException
     */
    @Override
    public void fuzzyQuery(String indexName, String typeName,String field,String value) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.types(typeName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.fuzzyQuery(field,value).prefixLength(2));
        searchRequest.source(searchSourceBuilder);
        log.info("string:" + searchRequest.source());
        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        System.out.println("count:"+hits.totalHits);
        SearchHit[] h =  hits.getHits();
        for (SearchHit hit : h) {
            System.out.println("结果"+hit.getSourceAsMap());
        }
    }

    /**
     * 以通配符来查询
     * @param indexName     索引名称
     * @param typeName      TYPE名称
     * @param fieldName     字段名称
     * @param wildcard      通配符
     * @throws IOException
     */
    @Override
    public void wildCardQuery(String indexName, String typeName, String fieldName, String wildcard) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.types(typeName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.wildcardQuery(fieldName, wildcard));
        searchRequest.source(searchSourceBuilder);
        log.info("string:" + searchRequest.source());
        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        System.out.println("count:"+hits.totalHits);
        SearchHit[] h =  hits.getHits();
        for (SearchHit hit : h) {
            System.out.println("结果"+hit.getSourceAsMap());
        }
    }

    /**
     * 范围查询
     * @param indexName     索引名称
     * @param typeName      TYPE名称
     * @param fieldName     字段名称
     * @param from
     * @param to
     * @throws IOException
     */
    @Override
    public void rangeQuery(String indexName, String typeName, String fieldName, int from,int to) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.types(typeName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.rangeQuery(fieldName).from(from).to(to));
        searchRequest.source(searchSourceBuilder);
        log.info("string:" + searchRequest.source());
        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        System.out.println("count:"+hits.totalHits);
        SearchHit[] h =  hits.getHits();
        for (SearchHit hit : h) {
            System.out.println("结果"+hit.getSourceAsMap());
        }
    }

    /**
     *正则表达示查询
     * @param indexName     索引名称
     * @param typeName      TYPE名称
     * @param fieldName     字段名称
     * @param regexp        正则表达示
     * @throws IOException
     */
    @Override
    public void regexpQuery(String indexName, String typeName, String fieldName, String regexp) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.types(typeName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.regexpQuery(fieldName,regexp));
        searchRequest.source(searchSourceBuilder);
        log.info("string:" + searchRequest.source());
        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        System.out.println("count:"+hits.totalHits);
        SearchHit[] h =  hits.getHits();
        for (SearchHit hit : h) {
            System.out.println("结果"+hit.getSourceAsMap());
        }
    }

    @Override
    public void moreLikeThisQuery(String indexName, String typeName, String[] fieldNames, String[] likeTexts) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.types(typeName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.moreLikeThisQuery(likeTexts).minTermFreq(1));
        searchRequest.source(searchSourceBuilder);
        log.info("string:" + searchRequest.source());
        SearchResponse searchResponse =  restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        System.out.println("count:"+hits.totalHits);
        SearchHit[] h =  hits.getHits();
        for (SearchHit hit : h) {
            System.out.println("结果"+hit.getSourceAsMap());
        }
    }

    @Override
    public void scrollQuery(String indexName, String typeName) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.types(typeName);
        //初始化scroll
        //值不需要足够长来处理所有数据—它只需要足够长来处理前一批结果。每个滚动请求(带有滚动参数)设置一个新的过期时间。
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L)); //设定滚动时间间隔
        searchRequest.scroll(scroll);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(matchAllQuery());
        searchSourceBuilder.size(5); //设定每次返回多少条数据
        searchRequest.source(searchSourceBuilder);
        log.info("string:" + searchRequest.source());
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String scrollId = searchResponse.getScrollId();
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        System.out.println("-----首页-----");
        for (SearchHit searchHit : searchHits) {
            System.out.println(searchHit.getSourceAsString());
        }
        //遍历搜索命中的数据，直到没有数据
        while (searchHits != null && searchHits.length > 0) {
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(scroll);
            log.info("string:" + scrollRequest.toString());
            try {
                searchResponse = restHighLevelClient.scroll(scrollRequest, RequestOptions.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            scrollId = searchResponse.getScrollId();
            searchHits = searchResponse.getHits().getHits();
            if (searchHits != null && searchHits.length > 0) {
                System.out.println("-----下一页-----");
                for (SearchHit searchHit : searchHits) {
                    System.out.println(searchHit.getSourceAsString());
                }
            }

        }
        //清除滚屏
        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(scrollId);//也可以选择setScrollIds()将多个scrollId一起使用
        ClearScrollResponse clearScrollResponse = null;
        try {
            clearScrollResponse = restHighLevelClient.clearScroll(clearScrollRequest,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean succeeded = clearScrollResponse.isSucceeded();
        System.out.println("succeeded:" + succeeded);
    }
}
