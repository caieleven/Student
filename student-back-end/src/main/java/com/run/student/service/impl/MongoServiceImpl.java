package com.run.student.service.impl;

import com.run.student.service.MongoService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
    public List<Long> allSidInCollection(String collectionName) {
        Query query = new Query();
        query.fields().include("sids");
        query.addCriteria(Criteria.where("tableName").is(collectionName));
        HashMap<String, Object> one = mongoTemplate.findOne(query, HashMap.class, "TableInfo");
        if(ObjectUtils.isEmpty(one.get("sids")))
            return new ArrayList<Long>();
        return (List<Long>) one.get("sids");
    }

    @Override
    public List<Document> listBySids(String collectionName, List<Long> sids) {
        return null;
    }

    @Override
    public Map<String, Object> queryBySid(String collectionName, Long sid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("sid").is(sid));
        HashMap<String, Object> one = mongoTemplate.findOne(query, HashMap.class, collectionName);
        one.remove("_id");
        one.remove("sid");
        return one;
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
    public Map<String, Object> getAllInfoInTableInfo(String tableName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("tableName").is(tableName));
        return mongoTemplate.findOne(query, HashMap.class, "TableInfo");
    }

    @Override
    public boolean updateSids(String tableName, List<Long> sids) {
        //查找表中原有的id
        final List<Long> oldSids = this.allSidInCollection(tableName);
        //合并并去重
        List<Long> newSids = Stream.of(sids, oldSids).flatMap(Collection::stream).distinct().collect(Collectors.toList());
        Query query = new Query(Criteria.where("tableName").is(tableName));
        Update update = new Update().set("sids", newSids);
        mongoTemplate.updateFirst(query, update, "TableInfo");
        return true;
    }

    @Override
    public boolean insertOne(String collectionName, Map<String, Object> map) {
        Document document = new Document();
        for(Map.Entry<String, Object> entry : map.entrySet()){
            document.append(entry.getKey(), entry.getValue());
        }
        mongoTemplate.insert(document, collectionName);
        return false;
    }
}
