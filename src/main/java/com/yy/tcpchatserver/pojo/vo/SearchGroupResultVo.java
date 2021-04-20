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
public class SearchGroupResultVo {
    private String id; // 群标识
    private String title;// 群名称
    private String desc;
    private String img;
    private String code;
    private Integer userNum;
    private Date createDate;
    private String holderName;
    private List<User> holderUsers;
}
