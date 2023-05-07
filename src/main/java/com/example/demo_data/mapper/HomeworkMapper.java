package com.example.demo_data.mapper;

import com.example.demo_data.entity.Homework;
import com.example.demo_data.entity.Teacher;

import java.util.List;

public interface HomeworkMapper {
    List<Integer> findByTeacherId(Integer teacher_id);
    Integer insect(Homework homework);
    Homework findById(Integer homework_id);
    List<Integer> findHomeworkByTeacherId(Integer teacher_id);
}
