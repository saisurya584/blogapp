package com.myblog.blogapp.controller;

import com.myblog.blogapp.payload.PostDto;
import com.myblog.blogapp.payload.PostResponse;
import com.myblog.blogapp.services.PostService;
import com.myblog.blogapp.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postser;

    public PostController(PostService postser){
        this.postser=postser;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Object>create(@Valid @RequestBody PostDto postdto, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
       return  new ResponseEntity<>(postser.createPost(postdto), HttpStatus.CREATED);
    }


//    @GetMapping
//    public List<PostDto>Getall()
//    {
//        List<PostDto> allPost = postser.getAllPost();
//
//        return allPost;
//
//    }
    @GetMapping ("/{id}")
    public ResponseEntity<PostDto>delete(@PathVariable("id") long id )
    {
        PostDto dto = postser.getById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto>update(@RequestBody PostDto postdto,@PathVariable("id") long id)
    {
        PostDto updatepost = postser.updatepost(postdto, id);
        return new ResponseEntity<>(updatepost,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteRecord(@PathVariable("id") long id)
    {
        postser.deletePost(id);
        return new ResponseEntity<>("post is deleted successfully",HttpStatus.OK);
    }
    @GetMapping
    public PostResponse getAllPosts(@RequestParam(value="pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false )int   pageNo,
                                    @RequestParam(value="pageSize",defaultValue =AppConstants.DEFAULT_PAGE_SIZE,required = false)int pageSize,
                                    @RequestParam(value ="sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
                                    @RequestParam(value="sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir
                                   ) {

        return postser.getAllPost(pageNo,pageSize,sortBy,sortDir);

    }
}
