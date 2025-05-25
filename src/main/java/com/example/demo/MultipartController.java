package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class MultipartController {
    @PostMapping(path = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "json", required = false) String json
    ) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("ファイル名: ").append(file.getOriginalFilename()).append("\n");
        sb.append("サイズ: ").append(file.getSize()).append(" bytes\n");
        sb.append("説明: ").append(description == null ? "(なし)" : description).append("\n");
        if (json != null) {
            sb.append("json: ").append(json).append("\n");
        }
        sb.append("内容(先頭100文字): ");
        String content = new String(file.getBytes(), StandardCharsets.UTF_8);
        sb.append(content.length() > 100 ? content.substring(0, 100) : content);
        return ResponseEntity.ok(sb.toString());
    }
} 