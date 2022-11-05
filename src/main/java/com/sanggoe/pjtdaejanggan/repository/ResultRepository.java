package com.sanggoe.pjtdaejanggan.repository;

import com.sanggoe.pjtdaejanggan.entity.CheckRecord;

import java.util.List;
import java.util.Optional;

public interface ResultRepository {

    CheckRecord save(CheckRecord checkRecord);

    //    boolean deleteByUsername(String string); // 회원 삭제 기능 추가 시 확장  

    Optional<List<CheckRecord>> findByUsername(String username);

}
