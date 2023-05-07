package com.example.demo_data.mapper;

import com.example.demo_data.entity.Student;

public interface StudentMapper {
    Integer insect(Student student);
    Student findById(Integer student_id);
    Integer findIdByName(String name);
    void updata(Integer studentId, Integer processing);
    Integer findProcessingByName(String name);
    void update(String name,String newpassword);
    void updataAd(String name, String ad);
}
