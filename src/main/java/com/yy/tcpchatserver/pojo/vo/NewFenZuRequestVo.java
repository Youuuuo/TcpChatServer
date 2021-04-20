package com.yy.tcpchatserver.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewFenZuRequestVo {
    private String fenZuName;
    private String userId;
}
