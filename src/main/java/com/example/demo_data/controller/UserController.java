package com.example.demo_data.controller;


import com.example.demo_data.entity.Student;
import com.example.demo_data.entity.User;
import com.example.demo_data.mapper.StudentMapper;
import com.example.demo_data.mapper.TeacherMapper;
import com.example.demo_data.mapper.UserMapper;
import com.example.demo_data.sevices.UserServices;
import com.example.demo_data.sevices.ex.InsertException;
import com.example.demo_data.sevices.ex.UsernameDuplicateException;
import com.example.demo_data.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController //其作用等同于@Controller+@ResponseBody
//@Controller
@RequestMapping("users")
public class UserController extends BaseController{

    @Autowired
    private UserServices userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @RequestMapping(value = "/reg")
    public JsonResult<Void> reg(User user) {
        System.out.println("username"+user.getUsername());
        userService.reg(user);
        return new JsonResult<>(OK);
    }
    @GetMapping("/login")
    @ResponseBody
    public JsonResult<User> login(String username, String password, String role,HttpSession session){
        System.out.println("这是一个验证");
        User data = userService.login(username,password,role);
        session.setAttribute("id",data.getId());
        session.setAttribute("username",data.getUsername());
        session.setAttribute("role",data.getRole());
        return new JsonResult<User>(OK, data);
    }
    @GetMapping("/feedback")
    public JsonResult<Void> feedback(String problem,String description,String type,String name,String email){
        userService.feedback(description);
        return new JsonResult<>(OK);
    }
    @GetMapping("person")
    public JsonResult<User> person(HttpSession session){
        String name = (String) session.getAttribute("username");
        User user = userMapper.findByUsername(name);
        return new JsonResult<>(OK,user);
    }
    @GetMapping("xiugai")
    public JsonResult<Void> xiugai(String oldpassword,String newpassword,String realypassword,HttpSession session){
        if(newpassword.equals(realypassword)) {
            String name = (String) session.getAttribute("username");
            User user = userMapper.findByUsername(name);
            if(user.getRole().equals("student")){
                studentMapper.update(name,newpassword);
                userMapper.update(name,newpassword);
            }
            else {
                teacherMapper.update(name,newpassword);
                userMapper.update(name,newpassword);
            }
            return new JsonResult<>(OK);
        }
        else throw new UsernameDuplicateException("密码和确认密码不一致！");
    }
}


