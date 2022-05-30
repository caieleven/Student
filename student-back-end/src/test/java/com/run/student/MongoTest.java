package com.run.student;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.run.student.service.MongoService;
import org.apache.commons.lang3.ObjectUtils;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Mono;

import java.util.*;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;


@SpringBootTest
public class MongoTest {

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    MongoService mongoService;


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
    public void testInsert(){
        HashMap<String, Object> map = new HashMap<>();
        List<String> baseList = new ArrayList<>();
        baseList.add("sid");
        map.put("tableName", "测试");
        map.put("baseColumns", baseList);
        mongoTemplate.insert(map, "新建表103");
    }

    @Test
    public void testQuery(){

        Query query = new Query();
        query.addCriteria(Criteria.where("tableName").is("test1101"));
        final List<Document> tableInfo = mongoTemplate.find(query, Document.class, "TableInfo");
        for (Document document : tableInfo){
            for(java.util.Map.Entry<String, Object> entry: document.entrySet()){
                System.out.println(entry.toString());
            }
        }

    }

    @Test
    void testAddSids(){
        List<Long> list = new ArrayList<>();
        list.add((long) 19122206);
        list.add((long) 19122207);
        mongoService.updateSids("年会101", list);
    }
}
