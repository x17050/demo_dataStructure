package com.example.demo_data.sevices.iml;

import com.example.demo_data.entity.Homework;
import com.example.demo_data.entity.LearningMaterial;
import com.example.demo_data.entity.Student;
import com.example.demo_data.mapper.*;
import com.example.demo_data.sevices.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServicesIml implements StudentServices {
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
    public List<Homework> findHomeworkByName(String name) {
        Integer student_id = studentMapper.findIdByName(name);
        Integer teacher_id = studentTeacherMapper.findTeacherByStudentId(student_id);
        List<Integer> homework_id = homeworkMapper.findHomeworkByTeacherId(teacher_id);
        List<Integer> homework_id1 = studentHomeworkMapper.findHomeworkByStudentId(student_id);
        List<Integer> commonList = new ArrayList<>();
        for (Integer id : homework_id) {
            for (Integer id1 : homework_id1) {
                if (id.equals(id1)) {
                    commonList.add(id);
                    break;
                }
            }
        }
        List<Homework> homeworks = new ArrayList<>();
        for (Integer homework_tea : commonList) {
            Homework homework = homeworkMapper.findById(homework_tea);
            if (homework != null) {
                homeworks.add(homework);
            }
        }
        return homeworks;
    }

    @Override
    public List<Homework> nofindHomeworkByName(String name) {
        Integer studentId = studentMapper.findIdByName(name);
        Integer teacher_id = studentTeacherMapper.findTeacherByStudentId(studentId);
        List<Integer> homework_id = homeworkMapper.findHomeworkByTeacherId(teacher_id);
        List<Integer> homework_id1 = studentHomeworkMapper.findHomeworkByStudentId(studentId);
        List<Integer> commonList = new ArrayList<>();
        for (Integer id : homework_id) {
            if (!homework_id1.contains(id)) {
                commonList.add(id);
            }
        }
        List<Homework> homeworkList = new ArrayList<>();
        for (Integer homework_tea : commonList) {
            Homework homework = homeworkMapper.findById(homework_tea);
            if (homework != null) {
                homeworkList.add(homework);
            }
        }
        return homeworkList;
    }

    @Override
    public Student findProcessById(Integer studentId,Integer processing) {
        studentMapper.updata(studentId,processing);
        Student student = studentMapper.findById(studentId);
        return student;
    }

    @Override
    public List<LearningMaterial> findLearnMaterialbyName(Integer teacherId) {
        List<LearningMaterial> learningMaterial = learningMaterialMapper.findByUploaderId(teacherId);
        System.out.println("x:"+learningMaterial);
        return learningMaterial;
    }
}
