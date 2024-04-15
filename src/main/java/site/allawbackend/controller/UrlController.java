package site.allawbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import site.allawbackend.service.UrlService;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
public class UrlController {
    private final UrlService urlService;

    @GetMapping("/pdfurl/{billsNum}")
    public String pdfUrl(@PathVariable final Integer billsNum) {
        return urlService.pdfUrl(billsNum);
    }

    @GetMapping("/file/{billsNum}")
    public ResponseEntity<Resource> fileDownload(@PathVariable final Integer billsNum) throws IOException {
        Path filePath = Paths.get("bills/" + billsNum + ".pdf");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(filePath.toString()));
        String fileName = filePath.getFileName().toString();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .cacheControl(CacheControl.noCache())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .body(resource);
    }
}
