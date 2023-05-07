package com.example.demo_data.sevices.iml;

import com.example.demo_data.entity.*;
import com.example.demo_data.mapper.*;
import com.example.demo_data.sevices.TeacherServices;
import com.example.demo_data.sevices.UserServices;
import com.example.demo_data.sevices.ex.InsertException;
import com.example.demo_data.sevices.ex.UsernameDuplicateException;
import com.example.demo_data.sevices.ex.*;
import javafx.scene.control.TextArea;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.print.attribute.standard.PrinterMessageFromOperator;
import javax.transaction.Transactional;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Random;

@Service
public class UserServicesIml implements UserServices {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private StudentTeacherMapper studentTeacherMapper;
    @Autowired
    private FeedbackMapper feedbackMapper;
    @Override
    @Transactional
    public void reg(User user) {
        String username = user.getUsername();
        User result = userMapper.findByUsername(username);
        if(result != null){
            throw new UsernameDuplicateException("用户名被占");
        }
        Integer rows = userMapper.insert(user);
        if(rows != 1){
            throw new InsertException("在用户注册的过程中产生了未知的异常");
        }
        if (user.getRole().equals("teacher")) {
            Teacher teacher = new Teacher();
            teacher.setName(user.getUsername());
            teacher.setPassword(user.getPassword());
            teacher.setUser_id(user.getId());
            rows = teacherMapper.insect(teacher);
            if (rows != 1) {
                throw new InsertException("在插入教师记录的过程中产生了未知的异常");
            }
            Integer teacherId = teacher.getId();
            Student_Teacher studentTeacher = new Student_Teacher();
            studentTeacher.setTeacher_id(teacherId);
            studentTeacherMapper.insert(studentTeacher);
        } else if (user.getRole().equals("student")) {
            Student student = new Student();
            student.setName(user.getUsername());
            student.setPassword(user.getPassword());
            student.setUser_id(user.getId());
            student.setProcessing(0);
            rows = studentMapper.insect(student);
            if (rows != 1) {
                throw new InsertException("在插入学生记录的过程中产生了未知的异常");
            }
            try{
                List<Teacher> teachers = teacherMapper.findAll();
                if (teachers.isEmpty()) {
                    throw new RuntimeException("没有可分配的老师");
                }
                int randomIndex = new Random().nextInt(teachers.size());
                Teacher randomTeacher = teachers.get(randomIndex);

                Student_Teacher student_teacher = new Student_Teacher();
                student_teacher.setStudent_id(student.getId());
                student_teacher.setTeacher_id(randomTeacher.getId());

                int row = studentTeacherMapper.insert(student_teacher);
                if (row != 1) {
                    throw new InsertException("在分配老师的过程中产生了未知的异常");
                }
                TransactionAspectSupport.currentTransactionStatus().flush();
            }catch (Exception e) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                throw e;
            }
        }
    }
    @Override
    public User login(String username, String password,String role) {
        User result = userMapper.findByUsername(username);
        //System.out.println("result"+result);
        if(result == null){
            throw new UserNotFoundException("用户数据不存在！");
        }
        String oldPassword = result.getPassword();
        if(!password.equals(oldPassword)){
            throw new PasswordNotMatchException("用户密码错误");
        }
        if(!role.equals(result.getRole())){
            throw new RoleNotException("用户角色选择错误");
        }
        User user = userMapper.findByUsername(username);
        return user;
    }

    @Override
    public void feedback(String title) {
        Feedback feedback = new Feedback();
        feedback.setContent(title);
        feedbackMapper.insert(feedback);
    }
}
