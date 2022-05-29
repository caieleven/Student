package com.run.student.service;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

public interface MongoService {
    boolean createCollection(String collectionName);
    boolean dropCollection(String collectionName);
    List<Integer> allSidInCollection(String collectionName);
    List<Document> listBySid(String collectionName, List<Integer> sid);
    boolean updateOne(String collectionName, Document document);
    boolean updateBatch(String collectName, List<Document> documents);

    /**
     * 向集合中插入
     * @param collectionName
     * @param map 插入值
     * @return
     */
    boolean insertOne(String collectionName, Map<String, Object> map);
}
