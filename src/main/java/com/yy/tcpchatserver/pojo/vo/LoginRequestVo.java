package com.yy.tcpchatserver.pojo.vo;

import com.yy.tcpchatserver.pojo.BrowserSetting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestVo {
    private String username;
    private String avatar;
    private String password;
    private String cvCode;
    private BrowserSetting setting;
}
