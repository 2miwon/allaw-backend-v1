package site.allawbackend.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/.well-known/pki-validation")
public class ValidationController {

    @GetMapping("/F2C79193C84E9CD9D5E82FFAC1D33B01.txt")
    public ResponseEntity<String> getTextFile() {
        Resource resource = new ClassPathResource("F2C79193C84E9CD9D5E82FFAC1D33B01.txt");
        try (InputStream inputStream = resource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String fileContent = reader.lines().collect(Collectors.joining("\n"));
            return ResponseEntity.ok().body(fileContent);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to read the file.");
        }
    }
}