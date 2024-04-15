package site.allawbackend.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatRequestDto {
    private String model;
    private List<MessageDto> messages;

    public ChatRequestDto(String model, String prompt, String systemPrompt) {
        this.model = model;

        this.messages = new ArrayList<>();
        this.messages.add(new MessageDto("system", systemPrompt));
        this.messages.add(new MessageDto("user", prompt));
    }
}
