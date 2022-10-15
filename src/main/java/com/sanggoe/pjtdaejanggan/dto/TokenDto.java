package com.sanggoe.pjtdaejanggan.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto { // 토큰 Response 할 때 사용될 토큰 DTO

    private String token;
}