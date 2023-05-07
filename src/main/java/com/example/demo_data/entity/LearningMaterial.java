package com.example.demo_data.entity;

import javafx.scene.text.Text;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "learning_material")
public class LearningMaterial {
    @Id
    private Integer id;
    private String name;
    private String type;
    private String content;
    private Integer uploader_id;
    private LocalDateTime upload_time;
}
