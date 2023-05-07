package com.example.demo_data.mapper;

import com.example.demo_data.entity.Student_Teacher;

import java.util.List;

public interface StudentTeacherMapper {
    Integer insert(Student_Teacher studentTeacher);
    Integer update(Student_Teacher studentTeacher);
    Student_Teacher findById(Integer id);
    List<Integer> findByTeacherId(Integer teacherId);
    Integer findTeacherByStudentId(Integer student_id);
}

