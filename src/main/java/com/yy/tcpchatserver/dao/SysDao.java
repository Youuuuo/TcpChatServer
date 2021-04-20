package com.yy.tcpchatserver.dao;

import com.yy.tcpchatserver.pojo.SystemUser;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SysDao extends MongoRepository<SystemUser, ObjectId> {
}
