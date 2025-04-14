package com.example.infosync.controller;

import com.example.infosync.mapper.RequestDataMapper;
import com.example.infosync.model.RequestData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RequestDataController {

    private final RequestDataMapper mapper;

    @PostMapping("/submit")
    public ResponseEntity<String> submit(@RequestBody RequestData data) {
        mapper.insert(data);
        return ResponseEntity.ok("已寫入");
    }

    @GetMapping("/latest")
    public ResponseEntity<RequestData> getLatest() {
        return ResponseEntity.ok(mapper.selectLatest());
    }
} 