package site.allawbackend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ElasticsearchService {

    private final String elasticsearchUrl = "https://ysu-004.es.us-east-2.aws.elastic-cloud.com"; // Elasticsearch의 URL
    private final String apiKey = "STh4TDA0MEJ3enFyUy1PS1RuRXI6WFZNVUpid0JUVi1GdTRVV21QSFdzZw=="; // 여기에 본인의 API 키를 입력해주세요

    public String searchAutocomplete(String userInput) {
        // Elasticsearch에 보낼 요청 URL
        String url = "https://ysu-004.es.us-east-2.aws.elastic-cloud.com/autocomplete/_search";

        // Elasticsearch 쿼리 설정
        String query = "{\"query\": {\"prefix\": {\"search_field\": \"" + userInput + "\"}}}";

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "ApiKey STh4TDA0MEJ3enFyUy1PS1RuRXI6WFZNVUpid0JUVi1GdTRVV21QSFdzZw==");

        // RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();

        // HTTP 요청 보내기
        try {
            // 요청 본문 설정
            HttpEntity<String> requestEntity = new HttpEntity<>(query, headers);

            // GET 요청을 보내고 응답 받기
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    String.class
            );

            // 응답 결과 확인
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return (responseEntity.getBody());
            } else {
                return ("요청 실패: " + responseEntity.getStatusCode());
            }
        } catch (Exception e) {
            return ("요청 실패: " + e.getMessage());
        }
    }

    private String extractSearchField(String elasticsearchResponse) {
        try {
            // Elasticsearch 응답 문자열을 JSON으로 파싱
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(elasticsearchResponse);

            // "hits" 객체 추출
            JsonNode hitsNode = rootNode.path("hits");

            // "hits" 객체 내부의 "hits" 배열 추출
            JsonNode hitsArrayNode = hitsNode.path("hits");

            // 결과 문자열 초기화
            StringBuilder resultBuilder = new StringBuilder();

            // "hits" 배열의 각 요소에서 "_source" 객체 추출하여 "search_field" 값을 추출하여 결과에 추가
            for (JsonNode hit : hitsArrayNode) {
                JsonNode sourceNode = hit.path("_source");
                resultBuilder.append(sourceNode.path("search_field").asText()).append("\n");
            }

            // 최종 결과 반환
            return resultBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
