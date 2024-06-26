package site.allawbackend.common.auth.dto;

import lombok.Getter;
import site.allawbackend.entity.User;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private User user;
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.user = user;
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }

    public Long getUserId() {
        return user.getId();
    }

}
