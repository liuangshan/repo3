package org.lyy.Test;

import org.junit.Test;
import org.lyy.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceTest {

    @Test
    public  void run(){
        //获得ApplicationContext
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");

        //得到service对象,已经在UserServiceImpl上添加注解了，context.getBean获取的是UserServiceImpl
        UserService service=(UserService) context.getBean("userService");

        service.queryAll();
    }
}
