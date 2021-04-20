package com.yy.tcpchatserver.service;

import com.yy.tcpchatserver.auth.entity.JwtAuthUser;
import com.yy.tcpchatserver.dao.UserDao;
import com.yy.tcpchatserver.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.findUserByUsernameOrCode(s, s);
        // System.out.println("查询到的登录用户信息为：" + user);
        if (user == null) throw new UsernameNotFoundException("该用户不存在！");
        JwtAuthUser jwtAuthUser = new JwtAuthUser();
        BeanUtils.copyProperties(user, jwtAuthUser);
        return jwtAuthUser;
    }
}
