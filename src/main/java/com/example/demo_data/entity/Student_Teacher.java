package com.example.demo_data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student_teacher")
public class Student_Teacher {
    @Id
    private Integer id;
    private Integer student_id;
    private Integer teacher_id;
}

