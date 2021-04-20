package com.yy.tcpchatserver.dao;

import com.yy.tcpchatserver.pojo.GroupMessage;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupMessageDao extends MongoRepository<GroupMessage, ObjectId> {

}
