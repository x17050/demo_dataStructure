package com.example.demo_data.controller;

import com.example.demo_data.entity.*;
import com.example.demo_data.mapper.*;
import com.example.demo_data.sevices.StudentServices;
import com.example.demo_data.sevices.ex.InsertException;
import com.example.demo_data.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.swing.plaf.PanelUI;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController extends BaseController{
    @Autowired
    private StudentServices studentServices;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private StudentHomeworkMapper studentHomeworkMapper;
    @Autowired
    private StudentTeacherMapper studentTeacherMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private DiscussionMapper discussionMapper;
    @Autowired
    private FeedbackMapper feedbackMapper;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("name")
    public JsonResult<String> getUser(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            return new JsonResult<String>(OK, username);
        } else {
            throw new InsertException("出错了");
        }
    }
    @GetMapping("/view_homework_histroy")
    public JsonResult<List<Homework>> view_homework(HttpSession session){
        String name = (String)session.getAttribute("username");
        List<Homework> homework = studentServices.findHomeworkByName(name);
        return new JsonResult<>(OK,homework);
    }
    @GetMapping("/view_homework_now")
    public JsonResult<List<Homework>> view_homework_now(HttpSession session){
        String name = (String)session.getAttribute("username");
        List<Homework> homework = studentServices.nofindHomeworkByName(name);
        return new JsonResult<>(OK,homework);
    }
    @GetMapping("/submit")
    public JsonResult<Void> submit(Integer homework_id,String content,HttpSession session){
        String name = (String)session.getAttribute("username");
        Integer studentId = studentMapper.findIdByName(name);
        StudentHomework studentHomework = new StudentHomework();
        studentHomework.setStudent_id(studentId);
        studentHomework.setHomework_id(homework_id);
        studentHomework.setStatus(content);
        studentHomeworkMapper.insect(studentHomework);
        return  new JsonResult<>(OK);
    }
    @GetMapping("/learn")
    public JsonResult<Student> learn(Integer processing,HttpSession session){
        System.out.println("进度"+processing);
        String name = (String) session.getAttribute("username");
        if(processing<100) {
            Integer studentId = studentMapper.findIdByName(name);
            Student process = studentServices.findProcessById(studentId, processing);
            return new JsonResult<>(OK, process);
        }
        else {
            Integer studentId = studentMapper.findIdByName(name);
            Student process = studentMapper.findById(studentId);
            return new JsonResult<>(OK,process);
        }
    }
    @GetMapping("loading")
    public JsonResult<List<LearningMaterial>> loading(HttpSession session){
        String name = (String) session.getAttribute("username");
        Integer studentId = studentMapper.findIdByName(name);
        Integer teacherId = studentTeacherMapper.findTeacherByStudentId(studentId);
        List<LearningMaterial> learningMaterials = studentServices.findLearnMaterialbyName(teacherId);
        return new JsonResult<>(OK,learningMaterials);
    }
    @GetMapping("allmeg")
    public  JsonResult<Student> allmeg(HttpSession session){
        String name = (String)session.getAttribute("username");
        Integer id = studentMapper.findIdByName(name);
        Student student = studentMapper.findById(id);
        return new JsonResult<>(OK,student);
    }
    @GetMapping("processing")
    public JsonResult<Integer> processing(HttpSession session){
        String name = (String)session.getAttribute("username");
        Integer studentprcessing = studentMapper.findProcessingByName(name);
        return new JsonResult<>(OK,studentprcessing);
    }
    @GetMapping("/teacher")
    public String teacherName(HttpSession session){
        String name = (String)session.getAttribute("username");
        Integer studentId = studentMapper.findIdByName(name);
        Integer teacher_id = studentTeacherMapper.findTeacherByStudentId(studentId);
        String teachername = teacherMapper.findNameById(teacher_id);
        return teachername;
    }

    @GetMapping("/disscussionstu")
    public JsonResult<List<Discussion>> view_dis(HttpSession session){
        String name = (String) session.getAttribute("username");
        List<Discussion> discussions = discussionMapper.findy();
        return new JsonResult<>(OK,discussions);
    }
    @GetMapping("/submitdis")
    public JsonResult<Void> submitdis(String content,String title,Integer publishId,HttpSession session){
        String name = (String)session.getAttribute("username");
        Integer studentId = studentMapper.findIdByName(name);
        Discussion discussion = new Discussion();
        discussion.setAnswer_id(studentId);
        discussion.setPublisher_id(publishId);
        discussion.setTitle(title);
        discussion.setContent(content);
        System.out.println("讨论内容"+content);
        discussionMapper.insert(discussion);
        return new JsonResult<>(OK);
    }
}
