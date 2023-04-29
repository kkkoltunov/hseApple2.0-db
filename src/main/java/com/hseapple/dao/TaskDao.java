package com.hseapple.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TaskDao extends JpaRepository<TaskEntity, Long> {
    @Transactional
    void deleteTaskById(Long id);

    List<TaskEntity> findAllByCourseIDAndIdGreaterThanEqual(Integer courseID, Long start);
}