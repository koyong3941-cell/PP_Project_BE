package com.kh.pp.ai.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.kh.pp.board.model.dto.BoardDto;
import com.kh.pp.board.model.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardAiService {

    private final ChatClient chatClient;
    private final BoardService boardService;

    public String summarizeBoard(Long boardNo) {

        // 1. 게시글 조회
        BoardDto board = boardService.boardDetail(boardNo);

        // 2. 존재하지 않으면 404
        if (board == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다.");
        }

        // 3. 요약 프롬프트 작성
        String prompt = """
                다음 게시글 본문을 최대 3줄로 요약해줘.
                절대 3줄을 넘지 마.
                요약 외에 다른 말은 하지 마.
                ---
                %s
                ---
                """.formatted(board.getBoardContent());

        // 4. AI 호출
        return chatClient.prompt()
                .options(OllamaChatOptions.builder()
                        .temperature(1.5)
                        )
                .user(prompt)
                .call()
                .content();
    }
    
    public List<String> recommendTitles(String content) {

        if (content == null || content.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "본문을 입력해주세요.");
        }

        String prompt = """
                당신은 게시글 제목 추천 전문가입니다.
                아래 게시글 본문을 읽고, 가장 어울리는 제목 후보를 정확히 5개 추천해주세요.
                
                반드시 아래 형식으로만 대답하세요:
                1. 제목1
                2. 제목2
                3. 제목3
                4. 제목4
                5. 제목5
                
                다른 설명이나 문장은 절대 추가하지 마세요.
                
                ---
                %s
                ---
                """.formatted(content);

        String result = chatClient.prompt()
                .options(OllamaChatOptions.builder()
                        .temperature(0.7)
                        )
                .user(prompt)
                .call()
                .content();

        // 결과를 리스트로 변환
        return Arrays.stream(result.split("\n"))
                .map(String::trim)
                .filter(line -> line.matches("^\\d+\\..*"))  // "1. ", "2. " 로 시작하는 줄만
                .map(line -> line.replaceFirst("^\\d+\\.\\s*", "")) // 숫자 제거
                .limit(5)
                .toList();
    }
}