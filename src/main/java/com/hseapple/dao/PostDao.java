package com.hseapple.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostDao extends JpaRepository<PostEntity, Long> {

    List<PostEntity> findAllByCourseIDAndIdGreaterThanEqual(Integer courseID, Long start);

    void deleteTaskById(Long postID);
}