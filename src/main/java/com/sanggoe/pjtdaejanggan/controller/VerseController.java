package com.sanggoe.pjtdaejanggan.controller;

import com.sanggoe.pjtdaejanggan.dto.*;
import com.sanggoe.pjtdaejanggan.service.VerseService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://fg.nh.myds.me", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/verse")
public class VerseController {
    private final VerseService verseService;

    public VerseController(VerseService verseService) {
        this.verseService = verseService;
    }

    // 선택한 headListDto 객체를 파라미터로 받아 해당하는 Verse들을 DB에서 꺼내 반환하는 getVersesForPractice 메서드 실행
    @PostMapping("/practice-verses")
    public ResponseEntity<PracticeResponseDto> getPracticeVerses(@Valid @RequestBody PracticeRequestDto practiceRequestDto) {
        return ResponseEntity.ok(verseService.getPracticeVerses(practiceRequestDto));
    }

    // CheckingInfoResponseDto 객체를 파라미터로 받아서 verseService의 getCheckingVerses 메서드를 수행한다
    // 점검 정보를 request로 받아 점검 구절 선별 결과를 response로 보내는 것
    @PostMapping("/checking-verses")
    public ResponseEntity<CheckingInfoResponseDto> getCheckingVerses(@Valid @RequestBody CheckingInfoRequestDto checkingInfoRequestDto) {
        return ResponseEntity.ok(verseService.getCheckingVerses(checkingInfoRequestDto));
    }

    // CheckingChapverseRequestDto 객체를 파라미터로 받아서 verseService의 getChapterCheckingResult 메서드를 수행한다
    // 장절 입력 정보를 request로 받아 정답 채점 결과를 response로 보내는 것
    @PostMapping("/checking-chapverse")
    public ResponseEntity<CheckingChapverseResponseDto> getChapverseCheckingResult(@Valid @RequestBody CheckingChapverseRequestDto checkingChapverseRequestDto) {
        return ResponseEntity.ok(verseService.getChapverseCheckingResult(checkingChapverseRequestDto));
    }

    // CheckingContentsRequestDto 객체를 파라미터로 받아서 verseService의 getChapterCheckingResult 메서드를 수행한다
    // 내용 입력 정보를 request로 받아 정답 채점 결과를 response로 보내는 것
    @PostMapping("/checking-contents")
    public ResponseEntity<CheckingContentsResponseDto> getContentCheckingResult(@Valid @RequestBody CheckingContentsRequestDto checkingContentsRequestDto) {
        return ResponseEntity.ok(verseService.getContentCheckingResult(checkingContentsRequestDto));
    }

    @PostMapping("/checking-hint")
    public ResponseEntity<CheckingContentsResponseDto> getHintResult(@Valid @RequestBody CheckingContentsRequestDto checkingContentsRequestDto) {
        if (checkingContentsRequestDto.getCurrentHint() == 9) {
            return ResponseEntity.ok(verseService.getContentCheckingResult(checkingContentsRequestDto));
        }
        return ResponseEntity.ok(verseService.getHintResult(checkingContentsRequestDto));
    }
}
