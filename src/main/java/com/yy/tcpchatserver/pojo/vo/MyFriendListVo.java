package com.yy.tcpchatserver.pojo.vo;

import com.yy.tcpchatserver.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyFriendListVo {
    private String id;
    private Date createDate;
    private String userM;
    private String userY;
    private List<User> uList;
    private List<User> uList1;
    private List<User> uList2;
}