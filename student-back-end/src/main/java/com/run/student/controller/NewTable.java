package com.run.student.controller;


import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/newtable")
public class NewTable {
    @Autowired
    MongoTemplate mongoTemplate;

    @GetMapping("/test")
    public List<DBObject> test(){
        DBObject dbObject = new BasicDBObject();
        dbObject.put("id", 19122206);
        dbObject.put("是否党员", "否");
        MongoCollection<Document> collection = mongoTemplate.getCollection("党员");
//        mongoTemplate.getCollection("党员").insertOne(dbObject);
        List<DBObject> result = mongoTemplate.find(new Query(), DBObject.class, "users");
        return result;
    }
}
