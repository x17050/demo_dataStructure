package com.example.demo_data.controller;

import com.example.demo_data.sevices.ex.*;
import com.example.demo_data.util.JsonResult;
import org.hibernate.service.spi.ServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

public class BaseController {

    public static final int OK = 200;
    @ExceptionHandler(ServiceException.class)
    public JsonResult<Void> handleException(Throwable e) {
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof UsernameDuplicateException) {
            result.setState(4000);
            result.setMessage("用户名已经被占用");
        } else if (e instanceof InsertException) {
            result.setState(5000);
            result.setMessage("插入数据时产生未知的异常");
        }
        else if (e instanceof UserNotFoundException) {
            result.setState(5001);
            result.setMessage("用户不存在");
        }
        else if (e instanceof PasswordNotMatchException) {
            result.setState(5002);
            result.setMessage("用户密码错误");
        }
        else if (e instanceof RoleNotException) {
            result.setState(5003);
            result.setMessage("用户选择角色错误");
        }
        else if (e instanceof UpDataException) {
            result.setState(5004);
            result.setMessage("修改密码错误");
        }
        return result;
    }

    protected final Integer getIdSession(HttpSession session){
       return Integer.valueOf(session.getAttribute("id").toString());
    }
    protected final String getUsernameSession(HttpSession session){
        return String.valueOf(session.getAttribute("username").toString());
    }
    protected final String getRoleSession(HttpSession session){
        return String.valueOf(session.getAttribute("role").toString());
    }

}


