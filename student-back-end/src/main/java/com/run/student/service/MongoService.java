package com.run.student.service;

import org.bson.Document;

import java.util.List;
import java.util.Map;

public interface MongoService {
    boolean createCollection(String collectionName);
    boolean dropCollection(String collectionName);
    List<Long> allSidInCollection(String collectionName);
    List<Document> listBySid(String collectionName, List<Integer> sid);
    boolean updateOne(String collectionName, Document document);
    boolean updateBatch(String collectName, List<Document> documents);

    /**
     * 更新表中的sid，不减少已添加的sid
     * @param tableName 附加表表名（集合名）
     * @param sids 学号列表
     * @return
     */
    boolean updateSids(String tableName, List<Long> sids);

    /**
     * 向集合中插入
     * @param collectionName
     * @param map 插入值
     * @return
     */
    boolean insertOne(String collectionName, Map<String, Object> map);
}
