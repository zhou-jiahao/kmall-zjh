package com.kgc.kmall.search;

import com.kgc.kmall.bean.PmsSearchSkuInfo;
import com.kgc.kmall.service.SkuService;
import com.kgc.kmall.util.RedisUtil;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.dubbo.config.annotation.Reference;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class KmallSearchServiceApplicationTests {

	@Reference
	SkuService skuService;

	@Resource
	JestClient jestClient;

	@Test
	void contextLoads() throws IOException {
		List<PmsSearchSkuInfo> pmsSearchSkuInfos=new ArrayList<>();
		//查询条件
		String json="{\n" +
				"  \"query\": {\n" +
				"    \"bool\": {\n" +
				"      \"filter\": [\n" +
				"          {\"terms\":{\"skuAttrValueList.valueId\":[\"39\",\"40\",\"41\",\"42\"]}},\n" +
				"          {\"term\":{\"skuAttrValueList.valueId\":\"43\"}}\n" +
				"        ], \n" +
				"      \"must\": \n" +
				"        {\n" +
				"          \"match\": {\n" +
				"            \"skuName\": \"iphone\"\n" +
				"          }\n" +
				"        }\n" +
				"      \n" +
				"    }\n" +
				"  }\n" +
				"}";
		Search search=new Search.Builder(json).addIndex("kmall").addType("PmsSkuInfo").build();
		try {
			SearchResult searchResult = jestClient.execute(search);
			List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = searchResult.getHits(PmsSearchSkuInfo.class);
			for (SearchResult.Hit<PmsSearchSkuInfo, Void> hit : hits) {
				PmsSearchSkuInfo pmsSearchSkuInfo = hit.source;
				pmsSearchSkuInfos.add(pmsSearchSkuInfo);
				System.out.println(pmsSearchSkuInfo.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Test
	public void testSearchBuilder(){
		SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();

		//bool
		BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();

		//term
		TermQueryBuilder termQueryBuilder=new TermQueryBuilder("skuAttrValueList.valueId",39);
		TermQueryBuilder termQueryBuilder1=new TermQueryBuilder("skuAttrValueList.valueId",43);
		//filter
		boolQueryBuilder.filter(termQueryBuilder);
		boolQueryBuilder.filter(termQueryBuilder1);

		//match
		MatchQueryBuilder matchQueryBuilder=new MatchQueryBuilder("skuName","iphone");
		//must
		boolQueryBuilder.must(matchQueryBuilder);

		//query
		searchSourceBuilder.query(boolQueryBuilder);
		//排序
		searchSourceBuilder.sort("id", SortOrder.DESC);
		System.out.println(searchSourceBuilder.toString());

	}

}
