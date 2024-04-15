package site.allawbackend.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatResponseDto {
    private List<Choice> choices;

    @Getter
    @Setter
    public static class Choice {

        private int index;
        private MessageDto message;

        // constructors, getters and setters
    }
}
