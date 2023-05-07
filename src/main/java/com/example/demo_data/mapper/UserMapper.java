package com.example.demo_data.mapper;

import com.example.demo_data.entity.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Results;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserMapper {
    Integer insert(User user);
    User findByUsername(String username);
    List<User> findAll();
    Integer findIdByUsername(String username);
    void update(String name, String newpassword);
}


