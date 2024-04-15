package site.allawbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.allawbackend.service.ElasticsearchService;

@RestController
@RequiredArgsConstructor
public class KeywordController {

    private final ElasticsearchService elasticsearchService;

    @PostMapping("/getKeyword")
    public ResponseEntity<String> getKeyword(@RequestBody String requestJson) {
        String response = elasticsearchService.searchAutocomplete(requestJson);
        return ResponseEntity.ok(response);
    }
}
