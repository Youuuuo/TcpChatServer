package com.yy.tcpchatserver.annon;

import java.lang.annotation.*;

//主键自增
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface AutoIncKey {

}