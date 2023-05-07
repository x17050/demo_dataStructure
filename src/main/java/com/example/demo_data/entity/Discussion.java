package com.example.demo_data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import javax.swing.text.AbstractDocument;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "discussion")
public class Discussion {
    @Id
    private Integer id;
    private String title;
    private String content;
    private Integer publisher_id;
    private Integer answer_id;
}
