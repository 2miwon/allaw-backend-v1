package site.allawbackend.service;

import opennlp.tools.tokenize.SimpleTokenizer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeywordExtractionService {

    public List<String> extractKeywords(String text) {
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        String[] tokens = tokenizer.tokenize(text);

        // 간단한 키워드 필터링 로직 (예: 명사만 추출)
        List<String> keywords = new ArrayList<>();
        for (String token : tokens) {
            if (isKeyword(token)) {
                keywords.add(token);
            }
        }
        return keywords;
    }

    private boolean isKeyword(String word) {
        // 예시: 길이가 일정 수준 이상인 단어들만 키워드로 간주
        return word.length() > 2;
    }
}
