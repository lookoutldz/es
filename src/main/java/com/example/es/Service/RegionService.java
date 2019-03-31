package com.example.es.Service;

import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service(value = "regionService")
public class RegionService {

    private static final Logger logger = LoggerFactory.getLogger(RegionService.class);

    @Autowired
    JestClient jestClient;

    public Object query(Long id, String name, Double longitude, Double latitude, Double radiusKm) {

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (id != null) {
            TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("id", id);
            queryBuilder.must(termQueryBuilder);
        }
        if (name != null) {
            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", name);
            queryBuilder.must(matchQueryBuilder);
        }
        GeoDistanceSortBuilder geoDistanceSortBuilder = null;
        if (longitude != null && latitude != null && radiusKm != null) {
            // 距离筛选
            GeoDistanceQueryBuilder geoDistanceQueryBuilder = QueryBuilders.geoDistanceQuery("location")
                    .point(latitude, longitude).distance(radiusKm, DistanceUnit.KILOMETERS);
            // 距离排序
            geoDistanceSortBuilder = SortBuilders.geoDistanceSort("location", latitude, longitude)
                    .unit(DistanceUnit.KILOMETERS).order(SortOrder.ASC);
            queryBuilder.must(geoDistanceQueryBuilder);
        }

        SearchSourceBuilder ssb = new SearchSourceBuilder();
        ssb.query(queryBuilder);
        if (geoDistanceSortBuilder != null) {
            ssb.sort(geoDistanceSortBuilder);
        } else {
            ssb.sort("id", SortOrder.ASC);
        }
        logger.warn(ssb.toString());
        Search search = new Search.Builder(ssb.toString()).addIndex("test_region").build();

        try {
            SearchResult searchResult = jestClient.execute(search);
            logger.warn(searchResult.toString());
            List<SearchResult.Hit<Map, Void>> hits = searchResult.getHits(Map.class, false);
            return hits;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
