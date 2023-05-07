package com.example.demo_data.entity;

import jdk.net.SocketFlow;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student_homework")
public class StudentHomework {
    @Id
    private Integer student_id;
    private Integer homework_id;
    private LocalDateTime submit_time;
    private String status;
    private BigDecimal score;
}
