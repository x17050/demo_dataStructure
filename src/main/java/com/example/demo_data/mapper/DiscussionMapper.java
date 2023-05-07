package com.example.demo_data.mapper;

import com.example.demo_data.entity.Discussion;

import java.util.List;

public interface DiscussionMapper {
    void insect(Discussion discussion);
    List<Discussion> findy();
    void insert(Discussion discussion);
    void delete_comment(Integer id);
    String findContentById(Integer id);
    void editcomment(String content, String oldcontent);
    String findTitleById(Integer id);
    void delete_discussion(String title);
}
