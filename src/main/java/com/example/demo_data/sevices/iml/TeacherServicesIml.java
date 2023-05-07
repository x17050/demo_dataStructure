package com.example.demo_data.sevices.iml;

import com.example.demo_data.entity.*;
import com.example.demo_data.mapper.*;
import com.example.demo_data.sevices.TeacherServices;
import com.example.demo_data.sevices.ex.TeacherNotFoundException;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TeacherServicesIml implements TeacherServices {
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private StudentTeacherMapper studentTeacherMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private LearningMaterialMapper learningMaterialMapper;
    @Autowired
    private HomeworkMapper homeworkMapper;
    @Autowired
    private StudentHomeworkMapper studentHomeworkMapper;
    @Autowired
    private FeedbackMapper feedbackMapper;
    @Autowired
    private DiscussionMapper discussionMapper;

    @Override
    public Student remain(String name, Integer processing, String ad) {
        studentMapper.updataAd(name,ad);
        return null;
    }
    @Override
    public List<Student> findStudentsByTeacherName(String teacherName) {
        Integer teacherId = teacherMapper.findIdByName(teacherName);
        if (teacherId == null) {
            throw new TeacherNotFoundException("老师不存在");
        }
        List<Integer>  studentIds = studentTeacherMapper.findByTeacherId(teacherId);
        if (studentIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<Student> students = new ArrayList<>();
        for (Integer studentId : studentIds) {
            Student student = studentMapper.findById(studentId);
            if (student != null) {
                students.add(student);
            }
        }
        return students;
    }
    @Override
    public void uploadLearningMaterial(String title, String description, MultipartFile file, String name) {
        LearningMaterial material = new LearningMaterial();
        try {
            String uploadDir = "C:\\Users\\lpc\\Desktop\\learning\\";
            String filePath = uploadDir + file.getOriginalFilename();
            File dest = new File(filePath);
            if (!dest.exists()) {
                dest.createNewFile();
            }
            material.setContent(file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Integer id = teacherMapper.findIdByName(name);
        material.setName(title);
        material.setType(description);
        material.setUploader_id(id);
        material.setUpload_time(LocalDateTime.now());
        learningMaterialMapper.save(material);
    }

    @Override
    public void setHome(String title, String content, LocalDateTime deadline, Integer score,String teacher_name) {
        Homework homework = new Homework();
        homework.setName(title);
        homework.setContent(content);
        homework.setDeadline(deadline);
        homework.setScore(score);
        Integer teacher_id = teacherMapper.findIdByName(teacher_name);
        homework.setTeacher_id(teacher_id);
        homeworkMapper.insect(homework);
    }

    @Override
    public List<Homework> findHomeworksByTeacherName(String username) {
        Integer teacher_id = teacherMapper.findIdByName(username);
        if (teacher_id == null) {
            throw new TeacherNotFoundException("老师不存在");
        }
        System.out.println("我运行了到这里"+teacher_id);
        List<Integer>  homework_id = homeworkMapper.findByTeacherId(teacher_id);
        if (homework_id.isEmpty()) {
            return new ArrayList<>();
        }
        List<Homework> homeworks = new ArrayList<>();
        for (Integer homework : homework_id) {
            Homework homework1 = homeworkMapper.findById(homework);
            if (homework1 != null) {
                homeworks.add(homework1);
            }
        }
        return homeworks;
    }

    @Override
    public List<StudentHomework> findStudentHomeworksByTeacherName(String name) {
        Integer teacher_id = teacherMapper.findIdByName(name);
        if (teacher_id == null) {
            throw new TeacherNotFoundException("老师不存在");
        }
        System.out.println("我运行了到这里"+teacher_id);
        List<Integer>  homework_id = homeworkMapper.findByTeacherId(teacher_id);
        if (homework_id.isEmpty()) {
            return new ArrayList<>();
        }
        List<StudentHomework> homeworks = new ArrayList<>();
        for (Integer homework : homework_id) {
            List<StudentHomework> studentHomeworkList = studentHomeworkMapper.findById(homework);
            if (studentHomeworkList != null && studentHomeworkList.size() > 0) {
                homeworks.addAll(studentHomeworkList);
            }
        }
        return homeworks;
    }

    @Override
    public void add_diss(String title, String content, String name) {
        Discussion discussion = new Discussion();
        discussion.setContent(content);
        discussion.setTitle(title);
        Integer teacher_id = teacherMapper.findIdByName(name);
        discussion.setPublisher_id(teacher_id);
        discussionMapper.insect(discussion);
    }
}

