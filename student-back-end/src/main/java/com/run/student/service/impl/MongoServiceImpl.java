package com.run.student.service.impl;

import com.run.student.service.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class MongoServiceImpl implements MongoService {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean createCollection(String collectionName) {
        mongoTemplate.createCollection(collectionName);
        return true;
    }

    @Override
    public boolean dropCollection(String collectionName) {
        mongoTemplate.dropCollection(collectionName);
        return false;
    }

    @Override
    public List<Integer> allSidInCollection(String collectionName) {
        return null;
    }

    @Override
    public List<Document> listBySid(String collectionName, List<Integer> sid) {
        return null;
    }

    @Override
    public boolean updateOne(String collectionName, Document document) {
        return false;
    }

    @Override
    public boolean updateBatch(String collectName, List<Document> documents) {
        return false;
    }

    @Override
    public boolean insertOne(String collectionName, Map<String, Object> map) {
        mongoTemplate.insert(map, collectionName);
        return false;
    }
}
