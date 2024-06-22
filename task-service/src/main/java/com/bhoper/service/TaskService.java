package com.bhoper.service;

import com.bhoper.dto.TaskCreateRequest;
import com.bhoper.dto.TaskUpdateRequest;
import com.bhoper.exception.NoUpdateException;
import com.bhoper.exception.TaskWasNotFoundException;
import com.bhoper.model.TaskEntity;
import com.bhoper.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public List<TaskEntity> getAllTasks() {
        return taskRepository.findAll();
    }

    public TaskEntity saveTask(TaskCreateRequest taskCreateRequest) {
//        TaskEntity task = this.modelMapper.map(taskCreateRequest, TaskEntity.class);
        TaskEntity task = new TaskEntity(taskCreateRequest.title(), taskCreateRequest.description(),
                taskCreateRequest.completed(), taskCreateRequest.dueDate());
        return taskRepository.save(task);
    }

    @Transactional
    public TaskEntity updateTask(TaskUpdateRequest taskUpdateRequest) {
        TaskEntity task = this.taskRepository.findById(taskUpdateRequest.id()).orElseThrow(() ->
                new TaskWasNotFoundException("Task with %s id was not found".formatted(taskUpdateRequest.id())));
        boolean changes = false;
        if (!task.getTitle().equals(taskUpdateRequest.title())) {
            changes = true;
            task.setTitle(taskUpdateRequest.title());
        }
        if (!task.getDescription().equals(taskUpdateRequest.description())) {
            changes = true;
            task.setDescription(taskUpdateRequest.description());
        }
        if (task.isCompleted() != (taskUpdateRequest.completed())) {
            changes = true;
            task.setCompleted(taskUpdateRequest.completed());
        }
        if (!task.getDueDate().equals(taskUpdateRequest.dueDate())) {
            changes = true;
            task.setDueDate(taskUpdateRequest.dueDate());
        }

        if (!changes) throw new NoUpdateException("No changes were found");

        return task;
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

}
