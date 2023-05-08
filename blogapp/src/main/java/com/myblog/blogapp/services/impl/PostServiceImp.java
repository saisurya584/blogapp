package com.myblog.blogapp.services.impl;


import com.myblog.blogapp.entities.Post;
import com.myblog.blogapp.exceptions.ResourceNotFoundException;
import com.myblog.blogapp.payload.PostDto;
import com.myblog.blogapp.payload.PostResponse;
import com.myblog.blogapp.repository.PostRepository;
import com.myblog.blogapp.services.PostService;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;



import java.util.List;

import java.util.stream.Collectors;

@Service
public class PostServiceImp implements PostService {
    private PostRepository postrepo;
    private ModelMapper Mapper;

    public PostServiceImp(PostRepository postrepo,ModelMapper Mapper) {
        this.postrepo = postrepo;
        this.Mapper=Mapper;
    }


//    @Override
//    public List<PostDto> getAllPost() {
//
//       List<Post>all= postrepo.findAll();
//        return all.stream().map(save -> mapToDto(save)).collect(Collectors.toList());
//    }

    @Override
    public PostDto getById(long id) {
         Post post=postrepo.findById(id).orElseThrow(
                 ()->new ResourceNotFoundException("post","id",id)
         );

        PostDto postDto = mapToDto(post);
        return postDto;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post=mapToEntity(postDto);
        Post save = postrepo.save(post);
        PostDto dto= mapToDto(save);
        return dto;
    }
    public Post mapToEntity(PostDto postDto)
    {
        Post post = Mapper.map(postDto, Post.class);
//        Post post=new Post();
//      post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//      post.setContent(postDto.getContent());
        return post;
    }
    public PostDto mapToDto(Post save)
    {

        PostDto dto = Mapper.map(save, PostDto.class);
//        PostDto dto=new PostDto();
//        dto.setId(save.getId());
//        dto.setTitle(save.getTitle());
//        dto.setDescription(save.getDescription());
//        dto.setContent(save.getContent());
        return dto;
    }

    @Override
    public PostDto updatepost(PostDto postdto,long id) {
        Post post = postrepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", id)
        );
        post.setTitle(postdto.getTitle());
        post.setContent(postdto.getContent());
        post.setDescription(post.getDescription());
        Post save = postrepo.save(post);
        return  mapToDto(post);
    }

    @Override
    public void deletePost(long id) {
        postrepo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("post","id",id)
        );
        postrepo.deleteById(id);
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort orders = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNo,pageSize,orders);
        Page<Post> p = postrepo.findAll(pageable);
        List<Post> content = p.getContent();
        List<PostDto> contents = content.stream().map(save -> mapToDto(save)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(contents);
        postResponse.setPageNo(p.getNumber());
        postResponse.setPageSize(p.getSize());
        postResponse.setTotalPages(p.getTotalPages());
        postResponse.setTotalElements(p.getTotalElements());
        postResponse.setLast(p.isLast());
        return postResponse;
    }
}
