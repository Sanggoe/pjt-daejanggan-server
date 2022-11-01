package com.sanggoe.pjtdaejanggan.dto;

import com.sanggoe.pjtdaejanggan.entity.CheckRecord;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyRecordsResponseDto {

    @NotNull
    private List<CheckRecordDto> myRecords;

    public static MyRecordsResponseDto from(List<CheckRecord> checkRecords) {
        List<CheckRecordDto> checkRecordDtos = checkRecords.stream()
                .map(checkRecord -> CheckRecordDto.builder()
                        .check_time(checkRecord.getCheck_time())
                        .count_total(checkRecord.getCount_total())
                        .count_selected(checkRecord.getCount_selected())
                        .score_total(checkRecord.getScore_total())
                        .score_transform(checkRecord.getScore_transform())
                        .check_chapverses(checkRecord.getCheck_chapverses())
                        .build())
                .collect(Collectors.toList());

        return MyRecordsResponseDto.builder().myRecords(checkRecordDtos).build();
    }

}
