package com.example.demo_data.mapper;

import com.example.demo_data.entity.StudentHomework;

import java.math.BigDecimal;
import java.util.List;

public interface StudentHomeworkMapper {
    List<StudentHomework> findById(Integer homework);
    void updata(StudentHomework studentHomework);
    List<Integer> findHomeworkByStudentId(Integer student_id);
    void insect(StudentHomework studentHomework);
}
