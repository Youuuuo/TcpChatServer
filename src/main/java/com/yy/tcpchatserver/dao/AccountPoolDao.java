package com.yy.tcpchatserver.dao;

import com.yy.tcpchatserver.pojo.AccountPool;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountPoolDao extends MongoRepository<AccountPool, String> {

}
