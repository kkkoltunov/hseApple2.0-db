package com.hseapple.service;

import com.hseapple.app.error.ExceptionMessage;
import com.hseapple.app.error.exception.BusinessException;
import com.hseapple.dao.PostDao;
import com.hseapple.dao.PostEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    @Autowired
    PostDao postDao;

    public PostService(PostDao postDao) {
        this.postDao = postDao;
    }

    public List<PostEntity> findAllPosts(Integer courseID, Long start) {
        return postDao.findAllByCourseIDAndIdGreaterThanEqual(courseID, start);
    }

    public PostEntity createPost(PostEntity postEntity) {
        postEntity.setCreatedAt(LocalDateTime.now());
        return postDao.save(postEntity);
    }

    public PostEntity updatePost(PostEntity newPost, Long postID) {
        PostEntity post = postDao.findById(postID).orElseThrow(() -> new BusinessException(ExceptionMessage.OBJECT_NOT_FOUND));
        post.setTitle(newPost.getTitle());
        post.setContent(newPost.getContent());
        post.setMediaLink(newPost.getMediaLink());
        post.setDocLink(newPost.getDocLink());
        post.setUpdatedAt(LocalDateTime.now());
        postDao.save(post);
        return post;
    }

    public void deletePost(Long postID) {
        postDao.findById(postID).orElseThrow(() -> new BusinessException(ExceptionMessage.OBJECT_ALREADY_DELETED));
        postDao.deleteTaskById(postID);
    }

}