package com.example.demo_data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "homework")
public class Homework {
    @Id
    private Integer id;
    private String name;
    private String content;
    private LocalDateTime deadline;
    private Integer score;
    private Integer teacher_id;
}
