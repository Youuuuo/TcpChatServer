package com.yy.tcpchatserver.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyFriendBeiZhuRequestVo {
    private String userId;
    private String friendId;
    private String friendBeiZhuName;
}
