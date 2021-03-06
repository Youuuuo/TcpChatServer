package com.yy.tcpchatserver.config;

import com.yy.tcpchatserver.auth.UnAuthEntryPoint;
import com.yy.tcpchatserver.filter.JwtLoginAuthFilter;
import com.yy.tcpchatserver.filter.JwtPreAuthFilter;
import com.yy.tcpchatserver.filter.KaptchaFilter;
import com.yy.tcpchatserver.handler.ChatLogoutSuccessHandler;
import com.yy.tcpchatserver.service.OnlineUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private OnlineUserService onlineUserService;

    //?????????
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //?????? userDetailsService???????????????????????????????????????
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    //?????????????????????????????????????????????
    @Override
    public void configure(WebSecurity web) throws Exception {
        //???????????????????????????context-path:/chat?????????????????????
        web.ignoring().antMatchers("/user/getCode","/sys/uploadUi", "/sys/getFaceImages", "/user/register", "/sys/downloadFile",
                "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/superuser/login"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // ????????????????????????
        http.cors()
                .and().csrf().disable() // ??????csrf
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //??????session???????????????
                .and().authorizeRequests() //??????????????????
                .antMatchers("/expression/**", "/face/**", "/img/**", "/uploads/**").permitAll() //??????????????????
                .anyRequest().authenticated()
                .and().logout().logoutSuccessHandler(new ChatLogoutSuccessHandler()).and()
                // ??????????????????????????????????????????????????????
                .addFilterBefore(new KaptchaFilter(redisTemplate), UsernamePasswordAuthenticationFilter.class)
                // ??????UsernamePasswordAuthenticationFilter??????login??????
                .addFilter(new JwtLoginAuthFilter(authenticationManager(), mongoTemplate, onlineUserService))
                // ?????????OncePerRequestFilter????????????????????????
                .addFilter(new JwtPreAuthFilter(authenticationManager(), onlineUserService))
                .httpBasic().authenticationEntryPoint(new UnAuthEntryPoint()); //??????????????????
    }
}
