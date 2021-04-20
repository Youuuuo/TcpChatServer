package com.yy.tcpchatserver.dao;

import com.yy.tcpchatserver.pojo.SingleMessage;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SingleMessageDao extends MongoRepository<SingleMessage, ObjectId> {

}
