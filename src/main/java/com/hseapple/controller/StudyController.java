package com.hseapple.controller;

import com.hseapple.dao.*;
import com.hseapple.service.CourseService;
import com.hseapple.service.SubjectService;
import com.hseapple.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@SecurityRequirement(name = "Authorization")
@Tag(description = "Api to manage study",
        name = "Study Resource")
public class StudyController {

    @Autowired
    CourseService courseService;

    @Autowired
    TaskService taskService;

    @Autowired
    SubjectService subjectService;

    @Operation(summary = "Get courses",
            description = "Provides all available courses. Access roles - TEACHER, ASSIST, STUDENT")
    @RequestMapping(value = "/subject/courses", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<CourseEntity> getCourses() {
        return courseService.findAllCourse();
    }

    @Operation(summary = "Get course requests",
            description = "Provides all available course requests. Access roles - TEACHER")
    @RequestMapping(value = "/subject/course/{courseID}/application/list", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<RequestEntity> getListRequests(@PathVariable(name = "courseID") Integer courseID, @RequestParam("approved") Boolean approved) {
        return courseService.findAllRequests(courseID, approved);
    }

    @Operation(summary = "Create course",
            description = "Creates new course. Access roles - TEACHER")
    @RequestMapping(value = "/subject/course", method = RequestMethod.POST)
    @ResponseBody
    public CourseEntity createCourse(@Valid @RequestBody CourseEntity courseEntity) {
        return courseService.createCourse(courseEntity);
    }

    @Operation(summary = "Update course",
            description = "Provides new updated course. Access roles - TEACHER")
    @RequestMapping(value = "/subject/course/{courseID}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> updateCourse(@RequestBody CourseEntity newCourse, @PathVariable("courseID") Integer courseID) {
        return courseService.updateCourse(newCourse, courseID);
    }

    @Operation(summary = "Delete course",
            description = "Delete course. Access roles - TEACHER")
    @DeleteMapping(value = "/subject/course/{courseID}")
    public ResponseEntity<?> deleteCourse(@PathVariable Integer courseID) {
        return courseService.deleteCourse(courseID);
    }

    @Operation(summary = "Get task for course",
            description = "Provides task for course")
    @RequestMapping(value = "/task/{taskID}", method = RequestMethod.GET)
    @ResponseBody
    public TaskEntity getTaskForCourse(@PathVariable("taskID") Long taskID) {
        return taskService.getTaskForCourse(taskID);
    }

    @Operation(summary = "Update task",
            description = "Provides new updated task. Access roles - TEACHER")
    @RequestMapping(value = "/task/{taskID}", method = RequestMethod.PUT)
    @ResponseBody
    public TaskEntity updateTask(@RequestBody TaskEntity newTask, @PathVariable("taskID") Long taskID) {
        return taskService.updateTask(newTask, taskID);
    }

    @Operation(summary = "Delete task",
            description = "Delete task. Access roles - TEACHER")
    @DeleteMapping(value = "task/{taskID}")
    public void deleteTask(@PathVariable Long taskID) {
        taskService.deleteTask(taskID);
    }

    @Operation(summary = "Get tasks for course",
            description = "Provides all available tasks for course")
    @RequestMapping(value = "/course/{courseID}/task", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<TaskEntity> getTasks(@PathVariable Integer courseID, @RequestParam("start") Long start) {
        return taskService.findTasks(courseID, start);
    }

    @Operation(summary = "Create task",
            description = "Creates new task. Access roles - TEACHER")
    @RequestMapping(value = "/course/task", method = RequestMethod.POST)
    @ResponseBody
    public TaskEntity createTask(@Valid @RequestBody TaskEntity taskEntity) {
        return taskService.createTask(taskEntity);
    }

    @Operation(summary = "Get answer tasks",
            description = "Provides all available student task answers. Access roles - TEACHER, ASSIST")
    @RequestMapping(value = "course/task/{taskID}", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<UserTaskEntity> getAnswerTasks(@PathVariable Long taskID,
                                                   @RequestParam("status") Boolean state_answer,
                                                   @RequestParam(value = "form", required = false) String form) {
        return taskService.findAnswerTasks(taskID, state_answer, form);
    }

    @Operation(summary = "Create answer",
            description = "Creates new answer. Access roles - TEACHER, ASSIST, STUDENT")
    @RequestMapping(value = "course/task/{taskID}/answer", method = RequestMethod.POST)
    @ResponseBody
    public UserTaskEntity createAnswer(@PathVariable("taskID") Long taskID, @Valid @RequestBody UserTaskEntity userTaskEntity) {
        return taskService.createAnswer(taskID, userTaskEntity);
    }

    @Operation(summary = "Put a student's grade on a task",
            description = "Provides new updated user task. Access roles - TEACHER, ASSIST")
    @RequestMapping(value = "/task/score", method = RequestMethod.PUT)
    @ResponseBody
    public UserTaskEntity updateUserTask(@RequestBody UserTaskEntity newUserTask) {
        return taskService.updateUserTask(newUserTask);
    }

    @Operation(summary = "Get subjects",
            description = "Provides all available subjects. Access roles - TEACHER, ASSIST, STUDENT")
    @RequestMapping(value = "/subjects", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<SubjectEntity> getSubjects() {
        return subjectService.findAllSubjects();
    }

    @Operation(summary = "Create subject",
            description = "Creates new subject. Access roles - TEACHER")
    @RequestMapping(value = "/subject", method = RequestMethod.POST)
    @ResponseBody
    public SubjectEntity createSubject(@Valid @RequestBody SubjectEntity subjectEntity) {
        return subjectService.createSubject(subjectEntity);
    }

    @Operation(summary = "Update subject",
            description = "Provides new updated subject. Access roles - TEACHER")
    @RequestMapping(value = "/subject/{subjectID}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> updateSubject(@RequestBody SubjectEntity newSubject, @PathVariable("subjectID") Long subjectID) {
        return subjectService.updateSubject(newSubject, subjectID);
    }

    @Operation(summary = "Delete subject",
            description = "Delete subject. Access roles - TEACHER")
    @DeleteMapping(value = "/subject/{subjectID}")
    public ResponseEntity<?> deleteSubject(@PathVariable Long subjectID) {
        return subjectService.deleteSubject(subjectID);
    }
}