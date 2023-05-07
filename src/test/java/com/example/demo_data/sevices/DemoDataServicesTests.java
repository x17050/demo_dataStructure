package com.example.demo_data.sevices;

import com.example.demo_data.entity.User;
import com.example.demo_data.mapper.UserMapper;
import org.hibernate.service.spi.ServiceException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class  DemoDataServicesTests{

    @Autowired
    private UserServices userService;

    @Test
    public void reg() {

    }
    @Test
    public void login(){
        User user =userService.login("tom","123456","student");
        System.out.println(user);
    }
}
