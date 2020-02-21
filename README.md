**完整代码与资料下载，问题交流，请加QQ群，群号（1047661092）：**

![](./docs/images/ma.png)

史上最全elasticsearch JAVA API使用代码,包括了几乎所有的常用查询API示例。

Elasticsearch 会在7.0之后的版本废弃TransportClient，在8.0之后的版本移除TransportClient ([文档](https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/client.html))。因此，使用RestClient来进行相关的操作。

> We plan on deprecating the `TransportClient` in Elasticsearch 7.0 and removing it completely in 8.0. Instead, you should be using the [Java High Level REST Client](https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.4/java-rest-high.html), which executes HTTP requests rather than serialized Java requests. The [migration guide](https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.4/java-rest-high-level-migration.html)describes  all the steps needed to migrate.

取而代之的是High Level REST Client。

我们是Springboot2.x使用的High Level REST Client。

# API包含如下：

## 一、Document APIs

#### 	1.1 新建文档

#### 	1.2 更新文档

#### 	1.3 删除文档

#### 	1.4 批量操作

##### 	  		  1.4.1 批量增加与修改

##### 	   		 1.4.2 批量删除

#### 	1.5 refresh

## 二、 term & terms

#### 	2.1 term

#### 	2.2 terms

## 三、 match查询

####   	3.1 match_all查询

####   	3.2  match查询

####   	3.3 布尔match查询

####   	3.4 multi_match查询

####   	3.5 match_phrase 
## 四、 基本查询

#### 	4.1 ids查询

#### 	4.2 prefix查询

#### 	4.3 fuzzy查询

#### 	4.4 wildcard查询

#### 	4.5 range查询

#### 	4.6 regexp查询

#### 	4.7 scroll查询

#####   	 	4.7.1 原理

#####   		 4.7.2 使用场景

## 五、 delete-by-query

## 六、 复合查询

#### 	6.1 bool查询

#### 	6.2 booting查询

## 七、 排序

## 八、 过滤查询结果

#### 	8.1  filter

#### 	8.2 range过滤器

####     8.5 exists 过滤器

## 九、 高亮

## 十、 地理信息搜索

#### 	10.1 地理坐标点

#### 	10.2 经纬度坐标格式

#### 	10.3 通过地理坐标点过滤

#### 	10.4 geo_distance

#### 	10.5  geo_bounding_box

#### 	10.6 geo_polygon

## 十一、聚合

#### 	11.1 cardinality去重计数

#### 	11.2 range统计

#### 	11.3 histogram 统计

#### 	11.4 date_histogram统计

#### 	11.5 extended_stats统计聚合

#### 	11.6 terms_stats统计

#### 	11.7 geo_distance统计



# Java技术交流QQ群

完整代码与资料下载，问题交流，请加QQ群，群号（**1047661092**）：

![](./docs/images/ma.png)
