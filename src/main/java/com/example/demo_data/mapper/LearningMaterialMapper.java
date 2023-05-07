package com.example.demo_data.mapper;

import com.example.demo_data.entity.LearningMaterial;

import java.util.List;

public interface LearningMaterialMapper {
    void save(LearningMaterial learningMaterial);
    List<LearningMaterial> findByUploaderId(Integer teacherId);
}
