package com.sanggoe.pjtdaejanggan.controller;

import com.sanggoe.pjtdaejanggan.dto.MyRecordsRequestDto;
import com.sanggoe.pjtdaejanggan.dto.MyRecordsResponseDto;
import com.sanggoe.pjtdaejanggan.dto.SaveCheckingResultDto;
import com.sanggoe.pjtdaejanggan.entity.CheckRecord;
import com.sanggoe.pjtdaejanggan.service.ResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://fg.nh.myds.me", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/result")
public class ResultController {
    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @PostMapping("/save-check-result")
    public ResponseEntity<CheckRecord> saveCheckingResult(@Valid @RequestBody SaveCheckingResultDto saveCheckingResultDto) {
        return ResponseEntity.ok(resultService.saveCheckingResult(saveCheckingResultDto));
    }

    @PostMapping("/my-records")
    public ResponseEntity<MyRecordsResponseDto> getMyRecords(@Valid @RequestBody MyRecordsRequestDto myRecordsRequestDto) {
        return ResponseEntity.ok(resultService.getMyRecords(myRecordsRequestDto));
    }
}
