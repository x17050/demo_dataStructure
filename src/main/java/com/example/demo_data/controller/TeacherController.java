package com.example.demo_data.controller;

import com.example.demo_data.entity.*;
import com.example.demo_data.mapper.DiscussionMapper;
import com.example.demo_data.mapper.StudentHomeworkMapper;
import com.example.demo_data.sevices.TeacherServices;
import com.example.demo_data.sevices.ex.InsertException;
import com.example.demo_data.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("teacher")
public class TeacherController extends BaseController{
    @Autowired
    private TeacherServices teacherServices;
    @Autowired
    private StudentHomeworkMapper studentHomeworkMapper;
    @Autowired
    private DiscussionMapper discussionMapper;
    @RequestMapping("remain")
    public JsonResult<Student> remain(@RequestBody Map<String, Object> data) {
        String name = (String) data.get("name");
        Integer processing = (Integer) data.get("processing");
        String ad = (String) data.get("ad");
        System.out.println("提醒" + ad);
        System.out.println("学生姓名" + name);
        Student student = teacherServices.remain(name, processing, ad);
        return new JsonResult<>(OK, student);
    }
    @GetMapping("name")
    public JsonResult<String> getUser(HttpSession session) {
        String username = (String)session.getAttribute("username");
        List<Student> students = teacherServices.findStudentsByTeacherName(username);
        if (username != null) {
            return new JsonResult<String>(OK,username);
        }
        else{
            throw new InsertException("出错了");
        }

    }
    //优秀学生提取。
    @GetMapping("/list_student")
    public JsonResult<List<Student>> ListStudent(HttpSession session){
        String username = (String) session.getAttribute("username");
        List<Student> students = teacherServices.findStudentsByTeacherName(username);
        return new JsonResult<>(OK, students);
    }
    @PostMapping("/upload")
    public JsonResult<Void> upLoader(String title,String description,@RequestParam("file") MultipartFile file, HttpSession session) {
        String name = (String)session.getAttribute("username");
        teacherServices.uploadLearningMaterial(title,description,file,name);
        return new JsonResult<>(OK);
    }
    @GetMapping("/set")
    public JsonResult<Void> set(String title, String content, String deadline, Integer score, HttpSession session){
        LocalDateTime localDateTime = (LocalDateTime.parse(deadline, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        String teacher_name = (String) session.getAttribute("username");
        teacherServices.setHome(title,content,localDateTime,score,teacher_name);
        return new JsonResult<>(OK);
    }
    @GetMapping("/histroy")
    public JsonResult<List<Homework>> histroy(HttpSession session){
        String name = (String) session.getAttribute("username");
        List<Homework> homeworks = teacherServices.findHomeworksByTeacherName(name);
        return new JsonResult<>(OK, homeworks);
    }
    @GetMapping("/view")
    public JsonResult<List<StudentHomework>> view(HttpSession session){
        String name = (String) session.getAttribute("username");
        List<StudentHomework> studentHomeworks = teacherServices.findStudentHomeworksByTeacherName(name);
        return new JsonResult<>(OK,studentHomeworks);
    }
    @GetMapping("score")
    public JsonResult<Void> score(Integer score,Integer studentId,Integer homeworkId){
        System.out.println("成绩"+score);
        System.out.println("学生"+studentId);
        if(score!=null) {
            int intValue = score.intValue();
            BigDecimal scoreBig = BigDecimal.valueOf(intValue);
            StudentHomework studentHomework = new StudentHomework();
            studentHomework.setScore(scoreBig);
            studentHomework.setStudent_id(studentId);
            studentHomework.setHomework_id(homeworkId);
            System.out.println("score"+studentHomework.getScore());
            System.out.println("修改成果"+studentHomework.getStudent_id());
            studentHomeworkMapper.updata(studentHomework);
        }
        return new JsonResult<>(OK);
    }
    @GetMapping("/discussion_tea")
    public JsonResult<Discussion> discussion_t(String title,String content,HttpSession session){
        String name = (String) session.getAttribute("username");
        if (title != null && content != null && !title.isEmpty() && !content.isEmpty()) {
            teacherServices.add_diss(title, content, name);
            return new JsonResult<>(OK);
        } else {
            throw new InsertException("上传失败");
        }
    }
    @GetMapping("/view_dis")
    public JsonResult<List<Discussion>> view_dis(HttpSession session){
        String name = (String) session.getAttribute("username");
        List<Discussion> discussions = discussionMapper.findy();
        return new JsonResult<>(OK,discussions);
    }
    @GetMapping("/delete_discussion")
    public  JsonResult<Void> delete_discussion(Integer id){
        String title = discussionMapper.findTitleById(id);
        System.out.println("title"+title);
        discussionMapper.delete_discussion(title);
        return new JsonResult<>(OK);
    }
    @GetMapping("/edit_comment")
    public JsonResult<Void> edit_comment(Integer id,String content){
        String oldcontent = discussionMapper.findContentById(id);
        discussionMapper.editcomment(content,oldcontent);
        return new JsonResult<>(OK);
    }
    @GetMapping("/delete_comment")
    public JsonResult<Void> delete_comment(Integer id){
        discussionMapper.delete_comment(id);
        return new JsonResult<>(OK);
    }
}
