package com.example.demo_data.mapper;

import com.example.demo_data.entity.Student;
import com.example.demo_data.entity.Teacher;
import com.example.demo_data.entity.User;

import java.util.List;

public interface TeacherMapper {
    Integer insect(Teacher teacher);
    List<Teacher> findAll();
    Integer findIdByName(String name);
    String findNameById(Integer teacher_id);

    void update(String name, String newpassword);
}
