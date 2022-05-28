package com.run.student;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;


@SpringBootTest
public class MongoTest {

    @Autowired
    MongoTemplate mongoTemplate;


    @Test
    public void testCreateCollection(){
        final boolean ifExist = mongoTemplate.collectionExists("党员");
        if (ifExist){
            mongoTemplate.dropCollection("test");
        }
        mongoTemplate.createCollection("党员");
    }

    @Test
    public void testDynamicEntity(){
//        DBObject dbObject = new BasicDBObject();
//        dbObject.put("id", 19122206);
//        dbObject.put("是否党员", "否");
//        MongoCollection<Document> collection = mongoTemplate.getCollection("党员");
////        mongoTemplate.getCollection("党员").insertOne(dbObject);
////        List<DBObject> result = mongoTemplate.find(new Query(), DBObject.class, "users");
////        result.forEach(System.out::println);
//        Document document = new Document();
//        document.append("姓名","陈宝润");
//        document.append("序号", 19122206);
//        mongoTemplate.insert(dbObject, "党员");
//        mongoTemplate.insert(document, "users");
        List<DBObject> result = mongoTemplate.find(new Query(), DBObject.class, "users");
        for (DBObject item : result){
            final Object name = item.get("姓名");
            System.out.print(name);
        }
    }

    @Test
    public void testMysqlAndMongo(){

    }
}
