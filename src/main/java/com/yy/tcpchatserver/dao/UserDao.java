package com.yy.tcpchatserver.dao;

import com.yy.tcpchatserver.pojo.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDao extends MongoRepository<User, ObjectId> {
    User findUserByUsernameOrCode(String username, String code);
    User findUserByUsername(String username);
}
