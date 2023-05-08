package com.myblog.blogapp.controller;

import com.myblog.blogapp.payload.CommentDto;
import com.myblog.blogapp.services.CommentService;
import com.myblog.blogapp.services.impl.CommentServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService csi;
    public CommentController(CommentService csi)
    {
        this.csi=csi;
    }
    @PostMapping("/posts/{postId}/comment")
    public ResponseEntity<CommentDto> create(@PathVariable("postId") long postId,@RequestBody CommentDto commentdto)
    {
        CommentDto commentDto = csi.createComment(postId,commentdto);
        return new ResponseEntity<>(commentDto, HttpStatus.CREATED);
    }
    @GetMapping("posts/{postId}/comments")
    public List<CommentDto>getallcomments(@PathVariable("postId") long postId)
    {
        List<CommentDto> commentDtos = csi.getallComments(postId);
        return commentDtos;
    }
    @PutMapping("posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto>update(@PathVariable("postId") long postId,@PathVariable("id") long id,@RequestBody CommentDto commentdto)
    {
        return new ResponseEntity<>(csi.updateComment(postId,id,commentdto),HttpStatus.OK);
    }
     @DeleteMapping("posts/{postId}/comments/{id}")
    public ResponseEntity<String>Delete(@PathVariable("postId") long postId,@PathVariable("id") long id)
    {
        csi.DeleteComment(postId,id);
        return new ResponseEntity<>("deleted successfully",HttpStatus.OK);
    }

}
