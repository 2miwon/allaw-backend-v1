package site.allawbackend.common.auth;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.allawbackend.common.ApiResponse;
import site.allawbackend.common.auth.dto.SessionUser;
import site.allawbackend.entity.Role;
import site.allawbackend.entity.User;
import site.allawbackend.repository.UserRepository;

import static site.allawbackend.common.ApiResponse.success;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final HttpSession httpSession;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(@RequestParam String email, @RequestParam String name) {
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(User.builder()
                        .name(name)
                        .email(email)
                        .role(Role.USER)
                        .build()));

        httpSession.setAttribute("user", new SessionUser(user));
        return ResponseEntity.ok(success(null, "User logged in successfully"));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {
        httpSession.invalidate();
        return ResponseEntity.ok(success(null, "User logged out successfully"));
    }
}
