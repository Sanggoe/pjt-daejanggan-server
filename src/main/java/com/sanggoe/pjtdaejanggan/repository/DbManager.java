/*
package com.sanggoe.pjtdaejanggan.repository;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

@Slf4j
public class DbManager {
    MyBatisConfig myBatisConfig;
    SqlSession session;

    public DbManager() {
        this.myBatisConfig = new MyBatisConfig();
        session = myBatisConfig.getNewSqlSession();
    }

    void upsertVerse(VerseInfo verseInfo) {
        myBatisConfig.getDataMapper(session).upsertVerse(verseInfo);
        session.commit();
    }

    List<VerseInfo> findByTheme(String theme) {
        return myBatisConfig.getDataMapper(session).findByTheme(theme);
    }

    List<VerseInfo> findByHead(String head) {
        return myBatisConfig.getDataMapper(session).findByHead(head);
    }

}
*/
