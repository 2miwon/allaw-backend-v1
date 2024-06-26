package site.allawbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.allawbackend.entity.Keyword;
import site.allawbackend.repository.KeywordRepository;

@Service
@RequiredArgsConstructor
public class KeywordService {
    private final KeywordRepository keywordRepository;

    public Keyword findByValue(final String value) {
        return keywordRepository.findByContent(value).get();
    }
}
