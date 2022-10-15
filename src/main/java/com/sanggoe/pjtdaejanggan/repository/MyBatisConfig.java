/*
package com.sanggoe.pjtdaejanggan.repository;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
@Slf4j
public class MyBatisConfig {
    private final String resource = "mybatis-config.xml";
    SqlSessionFactory sqlSessionFactory;

    public MyBatisConfig() {
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);

        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    public SqlSession getNewSqlSession(){
        return sqlSessionFactory.openSession();
    }

    public DataMapper getDataMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(DataMapper.class);
    }

}
*/
