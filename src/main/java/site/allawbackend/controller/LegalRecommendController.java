package site.allawbackend.controller;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/legal")
public class LegalRecommendController {

    @Autowired
    private RestHighLevelClient client;

    @PostMapping("/recommend")
    public List<String> searchRelevantLaws(@RequestBody String caseText) throws IOException {
        // 텍스트 기반 검색 쿼리 작성
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.matchQuery("content", caseText)) // 사례 텍스트를 "content" 필드와 매칭
                .sort("_score", SortOrder.DESC)
                .size(3); // 상위 3개의 결과만 반환

        SearchRequest searchRequest = new SearchRequest("pdf_documents_basic"); // 올바른 인덱스 이름 사용
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        // 검색된 법률안 제목과 내용 반환
        List<String> results = new java.util.ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            String title = (String) hit.getSourceAsMap().get("title");
            String content = (String) hit.getSourceAsMap().get("content");
            results.add(title + " - " + content);
        }

        return results;
    }
}