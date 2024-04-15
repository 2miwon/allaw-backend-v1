package site.allawbackend.service;


import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import site.allawbackend.dto.ChatRequestDto;
import site.allawbackend.dto.ChatResponseDto;
import site.allawbackend.dto.SummaryRequestDto;
import site.allawbackend.entity.Bills;
import site.allawbackend.repository.BillsRepository;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class ChatGptService {
    private final RestTemplate restTemplate;

    private final BillsRepository billsRepository;

    @Value("${openai.sum-system-prompt}")
    private String SumSystemPrompt;

    @Value("${openai.chat-system-prompt}")
    private String ChatSystemPrompt;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.chat-model}")
    private String chatModel;

    @Value("${openai.sum-model}")
    private String sumModel;

    @Value("${openai.api-url}")
    private String apiUrl;

    @Value("${openai.agree-system-prompt}")
    private String AgreeSystemPrompt;

    @Value("${openai.disagree-system-prompt}")
    private String DisagreeSystemPrompt;

    @Value("${openai.agree-model}")
    private String AgreeModel;

    @Value("${openai.disagree-model}")
    private String DisagreeModel;



    public String chat(String prompt) {
        // create a request
        ChatRequestDto request = new ChatRequestDto(model, prompt, ChatSystemPrompt);

        // call the API
        ChatResponseDto response = restTemplate.postForObject(apiUrl, request, ChatResponseDto.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }

        // return the first response
        return response.getChoices().get(0).getMessage().getContent();
    }

    public String summary(Integer billsNum){
        Bills bills = billsRepository.findById(billsNum).orElseThrow();

        String link = bills.getFile_link();
        try (BufferedInputStream in = new BufferedInputStream(new URL(link).openStream());){
            PDDocument document = PDDocument.load(in);
            PDFTextStripper stripper = new PDFTextStripper();
            String extractText = stripper.getText(document);
            SummaryRequestDto request = new SummaryRequestDto(sumModel, extractText, SumSystemPrompt);

            // call the API
            ChatResponseDto response = restTemplate.postForObject(apiUrl, request, ChatResponseDto.class);

            if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
                return "No response";
            }
            return response.getChoices().get(0).getMessage().getContent();
        }
        catch (IOException ignored) {
            return "No response";
        }
    }

    public String checkChat(String prompt, Integer billsNum) {
        Bills bills = billsRepository.findById(billsNum).orElseThrow();

        String link = bills.getFile_link();

        try (BufferedInputStream in = new BufferedInputStream(new URL(link).openStream());){
            PDDocument document = PDDocument.load(in);
            PDFTextStripper stripper = new PDFTextStripper();
            String extractText = stripper.getText(document);

            String txt = prompt + extractText +"External references include a '(출처:" + bills.getTitle() +") citation.";

            ChatRequestDto request = new ChatRequestDto(chatModel, txt, ChatSystemPrompt);

            // call the API
            ChatResponseDto response = restTemplate.postForObject(apiUrl, request, ChatResponseDto.class);

            if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
                return "No response";
            }

            // return the first response
            return response.getChoices().get(0).getMessage().getContent();
        }
        catch (IOException ignored) {
            return "No response";
        }
    }

    public String agree(String prompt, String pdf) {
        String system = AgreeSystemPrompt + pdf;
        // create a request
        ChatRequestDto request = new ChatRequestDto(AgreeModel, prompt, system);

        // call the API
        ChatResponseDto response = restTemplate.postForObject(apiUrl, request, ChatResponseDto.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }

        // return the first response
        return response.getChoices().get(0).getMessage().getContent();
    }
    public String disagree(String prompt, String pdf) {
        String system = DisagreeSystemPrompt + pdf;
        // create a request
        ChatRequestDto request = new ChatRequestDto(DisagreeModel, prompt, system);

        // call the API
        ChatResponseDto response = restTemplate.postForObject(apiUrl, request, ChatResponseDto.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }

        // return the first response
        return response.getChoices().get(0).getMessage().getContent();
    }
}
