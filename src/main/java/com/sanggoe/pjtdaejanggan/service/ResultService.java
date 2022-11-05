package com.sanggoe.pjtdaejanggan.service;

import com.sanggoe.pjtdaejanggan.dto.MyRecordsRequestDto;
import com.sanggoe.pjtdaejanggan.dto.MyRecordsResponseDto;
import com.sanggoe.pjtdaejanggan.dto.SaveCheckingResultDto;
import com.sanggoe.pjtdaejanggan.entity.CheckRecord;
import com.sanggoe.pjtdaejanggan.repository.JpaResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResultService {
    private final JpaResultRepository resultRepository;

//    private static final Logger logger = LoggerFactory.getLogger(ResultService.class);

    public ResultService(JpaResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    // 점검 결과 저장을 위한 메서드
    @Transactional
    public CheckRecord saveCheckingResult(SaveCheckingResultDto result) {
        CheckRecord checkRecord = CheckRecord.builder()
                .username(result.getUsername())
                .check_time(result.getCheck_time())
                .count_total(result.getCount_total())
                .count_selected(result.getCount_selected())
                .score_total(result.getScore_total())
                .score_transform(result.getScore_transform())
                .check_chapverses(result.getCheck_chapverses())
                .check_type(result.getCheck_type())
                .verse_type(result.getVerse_type())
                .build();

        return resultRepository.save(checkRecord);
    }

    @Transactional
    public MyRecordsResponseDto getMyRecords(MyRecordsRequestDto myRecordsRequestDto) {
        List<CheckRecord> checkRecords = resultRepository.findByUsername(myRecordsRequestDto.getUsername()).orElse(null);
        return MyRecordsResponseDto.from(checkRecords);
    }
}
