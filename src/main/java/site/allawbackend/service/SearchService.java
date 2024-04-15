package site.allawbackend.service;

import org.springframework.web.client.RestTemplate;

public class SearchService {

    private RestTemplate restTemplate = new RestTemplate();

    // public Map<String, Object> parseSearch(Map<String, Object> data) {
    // List<Map<String, Object>> hits = (List<Map<String, Object>>)
    // data.get("hits");
    // List<Map<String, Object>> refine = hits.stream()
    // .map(hit -> Map.of(
    // "title", ((Map<String, Object>) hit.get("_source")).get("title"),
    // "speaker", ((Map<String, Object>) hit.get("_source")).get("speaker"),
    // "bill_no", ((Map<String, Object>) hit.get("_source")).get("bill_no"),
    // "date", ((Map<String, Object>) hit.get("_source")).get("date")))
    // .collect(Collectors.toList());
    // Map<String, Object> result = new HashMap<>();
    // result.put("total", Integer.parseInt(((Map<String, Object>)
    // data.get("hits")).get("total").toString()));
    // result.put("result", refine);
    // return result;
    // }

    // public Map<String, Object> elasticSearch(String query, int page, String sort)
    // {
    // String url = System.getenv("ELASTIC_ENDPOINT") + "/pdf_documents/_search/";
    // HttpHeaders headers = new HttpHeaders();
    // headers.set("Content-Type", "application/json");
    // int from = (page - 1) * 10 > 0 ? (page - 1) * 10 : 0;
    // Map<String, Object> requestBody = new HashMap<>();
    // requestBody.put("query", Map.of("match", Map.of("content", query)));
    // requestBody.put("size", 10);
    // requestBody.put("from", from);
    // if (sort.equals("DATE")) {
    // requestBody.put("sort", List.of(Map.of("date", "desc")));
    // }

    // ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST,
    // new HttpEntity<>(requestBody, headers), Map.class);
    // return response.getBody();
    // }

    // public Map<String, Object> getSearch(String query, int page, String sort) {
    // return parseSearch(elasticSearch(query, page, sort));
    // }

    // public static void main(String[] args) {
    // ElasticSearchService service = new ElasticSearchService();
    // Map<String, Object> searchResult = service.getSearch("your_query_here", 1,
    // "DATE");
    // System.out.println(searchResult);
    // }
}
