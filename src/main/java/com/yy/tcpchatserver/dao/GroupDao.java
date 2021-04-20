package com.yy.tcpchatserver.dao;

import com.yy.tcpchatserver.pojo.Group;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupDao extends MongoRepository<Group, ObjectId> {
}
