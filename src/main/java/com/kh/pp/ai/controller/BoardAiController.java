package com.kh.pp.ai.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.pp.ai.service.BoardAiService;
import com.kh.pp.common.api.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/boards/ai")
@RequiredArgsConstructor
public class BoardAiController {

    private final BoardAiService boardAiService;

    @GetMapping("/{boardNo}")
    public ResponseEntity<String> boardDetailAi(@PathVariable(name = "boardNo") Long boardNo) {
        String summary = boardAiService.summarizeBoard(boardNo);
        return ResponseEntity.ok(summary);
    }
    
    @PostMapping("/recommend-titles")
    public ResponseEntity<ApiResponse<List<String>>> recommendTitles(@RequestBody Map<String, String> body) {
        
        String content = body.get("content");
        List<String> titles = boardAiService.recommendTitles(content);
        
        return ResponseEntity.ok(ApiResponse.success(titles));
    }
}