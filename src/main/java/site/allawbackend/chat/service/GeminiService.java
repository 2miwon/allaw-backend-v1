package site.allawbackend.chat.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import site.allawbackend.dto.ChatRequest;
import site.allawbackend.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import site.allawbackend.common.exception.BillNotFoundException;
import site.allawbackend.entity.Bill;
import site.allawbackend.repository.BillRepository;

@Service
@RequiredArgsConstructor
public class GeminiService {
    private final BillRepository billRepository;

    @Qualifier("geminiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${openai.chat-system-prompt}")
    private String chatSystemPrompt;

    public String chat(String prompt) { //without bill

        // Gemini에 요청 전송
        String requestUrl = apiUrl + "?key=" + geminiApiKey;

        ChatRequest request = new ChatRequest(prompt);
        ChatResponse response = restTemplate.postForObject(requestUrl, request, ChatResponse.class);

        String message = response.getCandidates().get(0).getContent().getParts().get(0).getText().toString();

        return message;
    }

    public String getContents(String prompt) { //with bill

        String requestUrl = apiUrl;
        ChatRequest request = new ChatRequest(prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + geminiApiKey);  // Bearer 토큰 형식
        headers.set("Content-Type", "application/json");

        // HttpEntity에 요청 본문과 헤더를 포함
        HttpEntity<ChatRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<ChatResponse> response = restTemplate.exchange(requestUrl, HttpMethod.POST, entity, ChatResponse.class);

        String message = response.getBody().getCandidates().get(0).getContent().getParts().get(0).getText().toString();
        return message;
    }

    public String chatBasedOnBill(String prompt, Long billId) {
        Bill bill = getBill(billId);
        String link = bill.getFileLink();

        String extractText = PdfUtil.extractTextFromPdf(link);
        String txt = chatSystemPrompt+ prompt + extractText + " External references include a '(출처:" + bill.getTitle() + ") citation.";

        return getContents(txt);
    }

    private Bill getBill(Long billId) {
        return billRepository.findById(billId)
                .orElseThrow(() -> new BillNotFoundException(billId));
    }
}