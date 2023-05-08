package com.myblog.blogapp.services;

import com.myblog.blogapp.payload.CommentDto;

import java.util.List;

public interface CommentService {

     CommentDto createComment(long postId,CommentDto commentdto);

      List<CommentDto>getallComments(long postId);

    CommentDto updateComment(long postId, long id, CommentDto commentdto);

    void DeleteComment(long postId, long id);
}
