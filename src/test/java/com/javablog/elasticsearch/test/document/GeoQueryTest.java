package com.javablog.elasticsearch.test.document;

import com.javablog.elasticsearch.SearchServiceApplication;
import com.javablog.elasticsearch.query.GeoQuery;
import com.javablog.elasticsearch.document.DocService;
import com.javablog.elasticsearch.document.IndexService;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SearchServiceApplication.class)
@WebAppConfiguration
public class GeoQueryTest {
    private final static Logger log = LoggerFactory.getLogger(GeoQueryTest.class);
    private String indexName = "cn_large_cities";
    private String type = "city_type";

    @Autowired
    private IndexService indexService;
    @Autowired
    private DocService docService;
    @Autowired
    private GeoQuery geoQuery;

    @Test
    public void testCreateIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        buildSetting(request);
        buildIndexMapping(request, type);
        indexService.createIndex(indexName,type,request);
    }

    @Test
    public void testDelIndex() throws IOException {
        indexService.deleteIndex(indexName);
    }

    //设置分片
    private void buildSetting(CreateIndexRequest request) {
        request.settings(Settings.builder().put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2));
    }

    /**
     * 生成地理信息表索引结构
     *
     * city 城市
     * state 省
     * location 位置
     * @param request
     * @param type
     * @throws IOException
     */
    private void  buildIndexMapping(CreateIndexRequest request, String type) throws IOException {
        XContentBuilder mappingBuilder = JsonXContent.contentBuilder()
                .startObject()
                    .startObject("properties")
                        .startObject("city")
                        .field("type", "keyword")
                        .field("index", "true")
                        .endObject()

                        .startObject("state")
                        .field("type", "keyword")
                        .field("index", "true")
                        .endObject()

                        .startObject("location")
                        .field("type", "geo_point")
//                        .field("index", "true")
                        .endObject()
                     .endObject()
                .endObject();
        request.mapping(type, mappingBuilder);
    }

    @Test
    public void testInitData() throws IOException {
        String json1 ="{" +
                "\"city\": \"北京\", " +
                "\"state\": \"北京\"," +
                "\"location\": {\"lat\": \"39.91667\", \"lon\": \"116.41667\"}"
                +"}";
        String json2 ="{" +
                "\"city\": \"上海\", " +
                "\"state\": \"上海\"," +
                "\"location\": {\"lat\": \"34.50000\", \"lon\": \"121.43333\"}"
                +"}";
        String json3 ="{" +
                "\"city\": \"厦门\", " +
                "\"state\": \"福建\"," +
                "\"location\": {\"lat\": \"24.46667\", \"lon\": \"118.10000\"}"
                +"}";
        String json4 ="{" +
                "\"city\": \"福州\", " +
                "\"state\": \"福建\"," +
                "\"location\": {\"lat\": \"26.08333\", \"lon\": \"119.30000\"}"
                +"}";
        String json5 ="{" +
                "\"city\": \"广州\", " +
                "\"state\": \"广东\"," +
                "\"location\": {\"lat\": \"23.16667\", \"lon\": \"113.23333\"}"
                +"}";

        docService.add(indexName, type, json1);
        docService.add(indexName, type, json2);
        docService.add(indexName, type, json3);
        docService.add(indexName, type, json4);
        docService.add(indexName, type, json5);
    }

    @Test
    public void testGeoDistanceQuery() throws IOException {
        //距厦门500公里以内的城市
        geoQuery.geoDistanceQuery(indexName,type,24.46667,118.0000,500);
    }

    @Test
    public void testGeoBoundingBoxh() throws IOException {
        geoQuery.geoBoundingBoxQuery(indexName,type,40.8,-74.0,40.715,-73.0);
    }

    @Test
    public void testPolygonQuery() throws IOException {
        geoQuery.geoPolygonQuery(indexName,type);
    }
}
