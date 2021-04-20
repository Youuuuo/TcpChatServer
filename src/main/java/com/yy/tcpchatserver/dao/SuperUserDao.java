package com.yy.tcpchatserver.dao;

import com.yy.tcpchatserver.pojo.SuperUser;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SuperUserDao extends MongoRepository<SuperUser, ObjectId> {

}
