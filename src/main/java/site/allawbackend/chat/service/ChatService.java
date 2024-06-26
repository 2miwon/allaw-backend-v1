package site.allawbackend.chat.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import site.allawbackend.common.exception.BillNotFoundException;
import site.allawbackend.entity.Bill;
import site.allawbackend.repository.BillRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final RestTemplate restTemplate;

    private final BillRepository billRepository;
    private final GptService gptService;

    @Value("${openai.sum-system-prompt}")
    private String sumSystemPrompt;

    @Value("${openai.chat-system-prompt}")
    private String chatSystemPrompt;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.chat-model}")
    private String chatModel;

    @Value("${openai.sum-model}")
    private String sumModel;

    @Value("${openai.api-url}")
    private String apiUrl;

    @Value("${openai.agree-system-prompt}")
    private String agreeSystemPrompt;

    @Value("${openai.disagree-system-prompt}")
    private String disagreeSystemPrompt;

    @Value("${openai.agree-model}")
    private String agreeModel;

    @Value("${openai.disagree-model}")
    private String disagreeModel;



    public String chat(String prompt) {
        return gptService.chat(model, prompt, chatSystemPrompt);
    }

    public String summary(Long billId){
        Bill bill = getBill(billId);
        if (bill.getSummary() != null) {
            return bill.getSummary();
        }

        String link = bill.getFileLink();
        String extractText;
        extractText = PdfUtil.extractTextFromPdf(link);

        String summary = gptService.chat(sumModel, extractText, sumSystemPrompt);
        bill.saveSummary(summary);
        billRepository.save(bill);

        return summary;
    }

    public String chatBasedOnBill(String prompt, Long billId) {
        Bill bill = getBill(billId);
        String link = bill.getFileLink();

        String extractText = PdfUtil.extractTextFromPdf(link);
        String txt = prompt + extractText + " External references include a '(출처:" + bill.getTitle() + ") citation.";

        return gptService.chat(chatModel, txt, chatSystemPrompt);
    }


    public String agree(String prompt, String pledge) {
        String system = agreeSystemPrompt + pledge;
        return gptService.chat(agreeModel, prompt, system);
    }
    public String disagree(String prompt, String pledge) {
        String system = disagreeSystemPrompt + pledge;
        return gptService.chat(disagreeModel, prompt, system);
    }

    private Bill getBill(Long billId) {
        return billRepository.findById(billId)
                .orElseThrow(() -> new BillNotFoundException(billId));
    }

}
