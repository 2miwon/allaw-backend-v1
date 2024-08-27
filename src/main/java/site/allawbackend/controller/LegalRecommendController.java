package site.allawbackend.controller;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScriptScoreFunctionBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/legal")
public class LegalRecommendController {

    @Autowired
    private RestHighLevelClient client;

    @PostMapping("/recommend")
    public List<String> searchRelevantLaws(@RequestBody String caseText) throws IOException {

        float[] queryVector = generateKoBERTEmbedding(caseText);

        // 스크립트 파라미터 설정
        Map<String, Object> params = new HashMap<>();
        params.put("queryVector", queryVector);

       // 스크립트 기반 점수 계산
        ScriptScoreFunctionBuilder scriptScoreFunctionBuilder = new ScriptScoreFunctionBuilder(
                new Script(
                        ScriptType.INLINE,  // 스크립트 타입 설정 (INLINE 사용)
                        "painless",  // 스크립트 언어 설정
                        "cosineSimilarity(params.queryVector, 'content_vector')",  // 스크립트 본문
                        params  // 파라미터 전달
                )
        );


        // Elasticsearch 벡터 검색 쿼리 작성
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.functionScoreQuery(
                        QueryBuilders.matchAllQuery(),
                        scriptScoreFunctionBuilder
                ))
                .sort("_score", SortOrder.DESC)
                .size(3); // 상위 3개의 결과만 반환

        SearchRequest searchRequest = new SearchRequest("pdf_documents_with_kobert_embeddings");
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        // 검색된 법률안 제목과 내용 반환
        List<String> results = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            float score = hit.getScore();
            String title = (String) hit.getSourceAsMap().get("title");
            String content = (String) hit.getSourceAsMap().get("content");

            // 관련도 계산 (0-100%로 표시, 소숫점 2자리까지)
            float maxScore = searchResponse.getHits().getMaxScore();
            float relevancePercentage = (score / maxScore) * 100;

            // 소숫점 2자리까지 포맷팅하여 문자열로 변환
            String formattedRelevance = String.format("%.2f", relevancePercentage);

            results.add("관련도: " + formattedRelevance + "% - " + title + " - " + content);
        }

        return results;
    }

    // KoBERT 임베딩 생성 - Python flask와 연동
    private float[] generateKoBERTEmbedding(String text) {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
        String url = "http://localhost:5000/embed";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = Map.of("text", text);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<Map<String, Object>>() {});

            // 임베딩 벡터 추출
            List<Double> embeddingList = (List<Double>) response.getBody().get("embedding");
            float[] embedding = new float[embeddingList.size()];
            for (int i = 0; i < embeddingList.size(); i++) {
                embedding[i] = embeddingList.get(i).floatValue();
            }
            return embedding;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to generate embedding from KoBERT service", e);
        }
    }
}
