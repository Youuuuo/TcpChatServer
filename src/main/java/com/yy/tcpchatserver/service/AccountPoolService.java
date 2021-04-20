package com.yy.tcpchatserver.service;


import com.yy.tcpchatserver.dao.AccountPoolDao;
import com.yy.tcpchatserver.pojo.AccountPool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AccountPoolService {
    @Resource
    private AccountPoolDao accountPoolDao;

    public void saveAccount(AccountPool accountPool) {
        accountPoolDao.save(accountPool);
    }
}
