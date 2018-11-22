package com.example.mytimer;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * 认证测试
 */
public class AuthenticationTest {


    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser() {

        simpleAccountRealm.addAccount(
                "chengchao", "123456aa",
                "admin", "user");
    }



    @Test
    public void testAuthentication() {

        // 1 构建 SecurityManager 环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        // 2 主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("chengchao", "123456aa");
        subject.login(token);

        boolean authenticated = subject.isAuthenticated();

        System.out.println("authenticated : "+ authenticated);

        boolean hasRole = subject.hasRole("admin");

        System.out.println("hasRole : "+ hasRole);

        subject.checkRole("admin");

        subject.checkRole("user1");



    }
}
