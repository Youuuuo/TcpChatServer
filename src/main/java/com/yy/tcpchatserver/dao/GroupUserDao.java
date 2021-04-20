package com.yy.tcpchatserver.dao;

import com.yy.tcpchatserver.pojo.GroupUser;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupUserDao extends MongoRepository<GroupUser, ObjectId> {
    GroupUser findGroupUserByUserIdAndGroupId(ObjectId userId, ObjectId groupId);
}
