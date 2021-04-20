package com.yy.tcpchatserver.handler;

import com.corundumstudio.socketio.SocketIOServer;
import com.yy.tcpchatserver.pojo.SuperUser;
import com.yy.tcpchatserver.pojo.SystemUser;
import com.yy.tcpchatserver.service.SuperUserService;
import com.yy.tcpchatserver.service.SysService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SocketServerHandler implements ApplicationRunner {
    private Logger logger = LoggerFactory.getLogger(SocketServerHandler.class);
    @Resource
    private SocketIOServer socketIOServer;

    @Resource
    private SysService sysService;

    @Resource
    private SuperUserService superUserService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //初始化一个系统用户
        logger.info("-----------initSystemUser-------------");
        initSystemUser();
        //初始化一个管理员账号（默认注册）
        logger.info("-----------initSuperUser-------------");
        initSuperUser();
        logger.info("-----------socket server start-----------");
        socketIOServer.start();
    }

    private void initSystemUser() {
        SystemUser systemUser = new SystemUser();
        systemUser.setCode("111111");
        systemUser.setNickname("验证消息");
        systemUser.setStatus(1);
        sysService.notExistThenAddSystemUser(systemUser);
    }


    private void initSuperUser() {
        SuperUser superUser = new SuperUser();
        superUser.setAccount("admin");
        superUser.setPassword("admin");
        superUser.setRole(0);
        superUserService.notExistThenAddSuperUser(superUser);
    }
}
