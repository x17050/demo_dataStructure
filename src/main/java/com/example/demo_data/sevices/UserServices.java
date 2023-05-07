package com.example.demo_data.sevices;

import com.example.demo_data.entity.User;

public interface UserServices {
    void reg(User user);
    User login(String username,String password,String role);
    void feedback(String title);
}
