package com.myblog.blogapp.services;

import com.myblog.blogapp.payload.PostDto;
import com.myblog.blogapp.payload.PostResponse;

public interface PostService {


    PostDto createPost(PostDto postDto);

//    List<PostDto> getAllPost();

    PostDto getById(long id);

    PostDto updatepost(PostDto postdto,long id);

    void deletePost(long id);

    PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);
}
