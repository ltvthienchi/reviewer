package com.prj4.reviewer.service;

import com.prj4.reviewer.entity.Comment;
import com.prj4.reviewer.entity.Post;
import com.prj4.reviewer.reporsitory.CommentRepository;
import com.prj4.reviewer.reporsitory.ImageRepository;
import com.prj4.reviewer.reporsitory.PostRepository;
import com.prj4.reviewer.request.CommentRequest;
import com.prj4.reviewer.response.CommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.lang.Iterable.*;


@Service
public class PostService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    ImageRepository imageRepository;


    public List<Post> getAllPostByDtCreated() {
        return postRepository.findAllByOrderByDtCreatedDesc();
    }

    public List<Post> getAllPostByComId(String idCompany) {
        return postRepository.findByIdCompanyOrderByDtCreatedDesc(idCompany);
    }


    public void createPost(Post post) {
        postRepository.save(post);
    }

    public String getImagePostFromIdProduct(String idProduct) {
        String idImage = postRepository.findByIdProduct(idProduct).getIdImage();
        return imageRepository.findByIdImage(idImage).getImgPath();
    }

    public String getImagePost(String idImage) {
        return imageRepository.findByIdImage(idImage).getImgPath();
    }

}
