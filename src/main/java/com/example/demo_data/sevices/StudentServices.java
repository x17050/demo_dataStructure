package com.example.demo_data.sevices;

import com.example.demo_data.entity.Homework;
import com.example.demo_data.entity.LearningMaterial;
import com.example.demo_data.entity.Student;

import java.util.List;

public interface StudentServices {
    List<Homework> findHomeworkByName(String name);
    List<Homework> nofindHomeworkByName(String name);
    Student findProcessById(Integer studentId,Integer prcessing);
    List<LearningMaterial> findLearnMaterialbyName(Integer teacherId);
}
