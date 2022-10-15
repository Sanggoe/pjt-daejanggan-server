package com.sanggoe.pjtdaejanggan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckingVersesDto { // 점검할 암송 구절 선별 위한 정보를 담아 request 할 때 사용하는 Dto

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @NotNull
    @Size(min = 3, max = 50)
    private String nickname;

/*
CheckingVerses = [
    {
        chapverse: [String],
        theme: [String],
        head: [String],
        subhead: [String],
        title: [String],
        contents: [String],
    },
    ...
]
*/

}