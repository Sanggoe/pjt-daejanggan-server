package com.sanggoe.pjtdaejanggan.repository;

import com.sanggoe.pjtdaejanggan.entity.CheckRecord;

import java.util.List;
import java.util.Optional;

public interface ResultRepository {

    CheckRecord save(CheckRecord checkRecord);

    //    boolean deleteByUsername(String string);

    Optional<List<CheckRecord>> findByUsername(String username);

}
