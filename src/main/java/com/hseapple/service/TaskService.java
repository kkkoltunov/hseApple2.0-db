package com.hseapple.service;

import com.hseapple.app.error.ExceptionMessage;
import com.hseapple.app.error.exception.BusinessException;
import com.hseapple.dao.TaskDao;
import com.hseapple.dao.TaskEntity;
import com.hseapple.dao.UserTaskDao;
import com.hseapple.dao.UserTaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    TaskDao taskDao;

    @Autowired
    UserTaskDao userTaskDao;

    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public void deleteTask(Long taskID) {
        taskDao.findById(taskID).orElseThrow(() -> new BusinessException(ExceptionMessage.OBJECT_ALREADY_DELETED));
        taskDao.deleteTaskById(taskID);
    }

    public List<TaskEntity> findTasks(Integer courseID, Long start) {
        return taskDao.findAllByCourseIDAndIdGreaterThanEqual(courseID, start);
    }

    public TaskEntity getTaskForCourse(Long taskID) {
        return taskDao.findById(taskID).orElseThrow(() -> new BusinessException(ExceptionMessage.OBJECT_NOT_FOUND));
    }

    public TaskEntity createTask(TaskEntity taskEntity) {
        taskEntity.setCreatedAt(LocalDateTime.now());
        taskEntity.setCreatedBy(taskEntity.getId());
        return taskDao.save(taskEntity);
    }

    public Iterable<UserTaskEntity> findAnswerTasks(Long taskID, Boolean state_answer, String form) {
        return userTaskDao.getListOfAnswers(taskID, state_answer, form);
    }

    public UserTaskEntity createAnswer(Long taskID, UserTaskEntity userTaskEntity) {
        userTaskEntity.setUserID(userTaskEntity.getId());
        userTaskEntity.setTaskID(taskID);
        userTaskEntity.setCreatedAt(LocalDateTime.now());
        userTaskEntity.setCreatedBy(userTaskEntity.getId());
        userTaskEntity.setStatus(true);
        return userTaskDao.save(userTaskEntity);
    }

    public TaskEntity updateTask(TaskEntity newTask, Long taskID) {
        TaskEntity task = taskDao.findById(taskID).orElseThrow(() -> new BusinessException(ExceptionMessage.OBJECT_NOT_FOUND));
        task.setCourseID(newTask.getCourseID());
        task.setForm(newTask.getForm());
        task.setTitle(newTask.getTitle());
        task.setDescription(newTask.getDescription());
        task.setTask_content(newTask.getTask_content());
        task.setDeadline(newTask.getDeadline());
        task.setStatus(newTask.isStatus());
        task.setUpdatedAt(LocalDateTime.now());
        task.setUpdatedBy(newTask.getId());
        taskDao.save(task);
        return task;
    }

    public UserTaskEntity updateUserTask(UserTaskEntity newUserTask) {
        UserTaskEntity userTask = userTaskDao.findByTaskIDAndUserID(newUserTask.getTaskID(), newUserTask.getUserID())
                .orElseThrow(() -> new BusinessException(ExceptionMessage.OBJECT_NOT_FOUND));
        userTask.setScore(newUserTask.getScore());
        userTask.setUpdatedBy(newUserTask.getId());
        userTask.setUpdatedAt(LocalDateTime.now());
        return userTask;
    }
}