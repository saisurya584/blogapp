package com.myblog.blogapp.services.impl;

import com.myblog.blogapp.entities.Comment;
import com.myblog.blogapp.entities.Post;
import com.myblog.blogapp.exceptions.ResourceNotFoundException;
import com.myblog.blogapp.payload.CommentDto;
import com.myblog.blogapp.repository.CommentRepository;
import com.myblog.blogapp.repository.PostRepository;
import com.myblog.blogapp.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImp implements CommentService {

    private CommentRepository commentrepo;
    private PostRepository postrepo;
    private ModelMapper mapper;

    public CommentServiceImp(CommentRepository commentrepo, PostRepository postrepo,ModelMapper mapper) {
        this.commentrepo = commentrepo;
        this.postrepo = postrepo;
        this.mapper=mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentdto) {
        Post post = postrepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("comment", "id", postId)
        );
        Comment comment = mapToComment(commentdto);
        comment.setPost(post);
        Comment comments = commentrepo.save(comment);
        CommentDto dto = mapToDto(comments);
        return dto;

    }

    Comment mapToComment(CommentDto commentdto) {
        Comment comment = mapper.map(commentdto, Comment.class);
//        Comment comment = new Comment();
//        comment.setBody(commentdto.getBody());
//        comment.setName(commentdto.getName());
//        comment.setEmail(commentdto.getEmail());
        return comment;
    }

    CommentDto mapToDto(Comment save) {
        CommentDto commentDto = mapper.map(save, CommentDto.class);
//        CommentDto commentdto = new CommentDto();
//        commentdto.setId(save.getId());
//        commentdto.setBody(save.getBody());
//        commentdto.setName(save.getName());
//        commentdto.setEmail(save.getEmail());
        return commentDto;
    }

    public List<CommentDto> getallComments(long postId) {

        List<Comment> comments = commentrepo.findByPostId(postId);
        return comments.stream().map(x -> mapToDto(x)).collect(Collectors.toList());
    }

    @Override
    public CommentDto updateComment(long postId, long id, CommentDto commentdto) {


          commentrepo.findById(postId).orElseThrow(
                  () -> new ResourceNotFoundException("POSTID", "id", postId)
           );
          Comment comment = commentrepo.findById(id).orElseThrow(
                  () -> new ResourceNotFoundException("comment", "id", id)
          );
          comment.setBody(commentdto.getBody());
          comment.setName(commentdto.getName());
          comment.setEmail(commentdto.getEmail());
        Comment save = commentrepo.save(comment);

        return mapToDto(save);

    }
   public void DeleteComment(long postId, long id)
   {
       commentrepo.findById(postId).orElseThrow(
               () -> new ResourceNotFoundException("POSTID", "id", postId)
       );
       Comment comment = commentrepo.findById(id).orElseThrow(
               () -> new ResourceNotFoundException("comment", "id", id)
       );
       commentrepo.deleteById(id);
   }
}
