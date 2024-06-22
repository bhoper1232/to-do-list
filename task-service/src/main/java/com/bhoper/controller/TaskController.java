package com.bhoper.controller;

import com.bhoper.dto.TaskCreateRequest;
import com.bhoper.dto.TaskUpdateRequest;
import com.bhoper.exception.NoUpdateException;
import com.bhoper.model.TaskEntity;
import com.bhoper.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public List<TaskEntity> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping("create")
    public ResponseEntity<TaskEntity> saveTask(@RequestBody TaskCreateRequest taskCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.saveTask(taskCreateRequest));
    }

    @PatchMapping("edit")
    public ResponseEntity<TaskEntity> updateTask(@RequestBody TaskUpdateRequest taskUpdateRequest) {
        return ResponseEntity.ok(taskService.updateTask(taskUpdateRequest));
    }

    @DeleteMapping("delete/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable("taskId") Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NoUpdateException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoUpdateException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ProblemDetail
                        .forStatusAndDetail(HttpStatus.BAD_REQUEST, "No changes were found"));
    }
}
