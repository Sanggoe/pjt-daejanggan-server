package com.sanggoe.pjtdaejanggan.repository;

import com.sanggoe.pjtdaejanggan.entity.Verse;

import java.util.List;
import java.util.Optional;

public interface VerseRepository {

    Optional<Verse> findVersesByHead(List<String> username); // username기준으로, user 정보를 가져올 때 권한 정보도 같이 가져온다.

}
