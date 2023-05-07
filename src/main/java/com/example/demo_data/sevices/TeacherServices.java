package com.example.demo_data.sevices;

import com.example.demo_data.entity.Homework;
import com.example.demo_data.entity.Student;
import com.example.demo_data.entity.StudentHomework;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

public interface TeacherServices {
    Student remain(String name,Integer processing,String ad);
    List<Student> findStudentsByTeacherName(String teacherName);
    void uploadLearningMaterial(String title, String description,MultipartFile file,String name);
    void setHome(String title, String content, LocalDateTime deadline, Integer score,String teacher_name);
    List<Homework> findHomeworksByTeacherName(String username);
    List<StudentHomework> findStudentHomeworksByTeacherName(String name);
    void add_diss(String title, String content, String name);

}
