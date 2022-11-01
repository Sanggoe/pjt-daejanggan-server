package com.sanggoe.pjtdaejanggan.service;

import com.sanggoe.pjtdaejanggan.dto.SaveCheckingResultDto;
import com.sanggoe.pjtdaejanggan.entity.CheckRecord;
import com.sanggoe.pjtdaejanggan.repository.JpaResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResultService {
    private final JpaResultRepository resultRepository;
    private static final Logger logger = LoggerFactory.getLogger(ResultService.class);

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
                .build();

        logger.debug(">>>>>>>>> save result >>>>>>>>>");
        logger.debug(result.getUsername());
        logger.debug(result.getCheck_time());
        logger.debug(String.valueOf(result.getCount_total()));
        logger.debug(String.valueOf(result.getCount_selected()));
        logger.debug(String.valueOf(result.getScore_total()));
        logger.debug(String.valueOf(result.getScore_transform()));
        logger.debug(String.valueOf(result.getCheck_chapverses()));
        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return resultRepository.save(checkRecord);
    }
}
