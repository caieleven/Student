package com.run.student;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class JasyptTest {
    @Autowired
    StringEncryptor encryptor;

    @Test
    public void getPass() {
//        String urlMysql = encryptor.encrypt("jdbc:mysql://139.224.62.56:3306/students?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=GMT%2B8&zeroDateTimeBehavior=convertToNull");
//        String nameMysql = encryptor.encrypt("username");
//        String passwordMongo = encryptor.encrypt("password");
//        System.out.println(urlMysql+"----------------");
//        System.out.println(nameMysql+"----------------");
//        System.out.println(password+"----------------");
//        String nameMongo = encryptor.encrypt("username");
//        String passwordMongo = encryptor.encrypt("password");
//        System.out.println(nameMongo+"----------------");
//        System.out.println(passwordMongo+"----------------");
    }
}
