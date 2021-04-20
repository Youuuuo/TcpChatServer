package com.yy.tcpchatserver.dao;

import com.yy.tcpchatserver.pojo.GoodFriend;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface GoodFriendDao extends MongoRepository<GoodFriend, ObjectId> {
}

