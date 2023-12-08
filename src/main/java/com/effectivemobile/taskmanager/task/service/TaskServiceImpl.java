package com.effectivemobile.taskmanager.task.service;

import com.effectivemobile.taskmanager.error.exception.ConflictOnRequestException;
import com.effectivemobile.taskmanager.error.exception.NotFoundException;
import com.effectivemobile.taskmanager.task.dto.*;
import com.effectivemobile.taskmanager.task.model.Task;
import com.effectivemobile.taskmanager.task.model.TaskStatus;
import com.effectivemobile.taskmanager.task.repisotory.TaskRepository;
import com.effectivemobile.taskmanager.task.service.util.TaskParamsChecker;
import com.effectivemobile.taskmanager.task.service.util.TaskSorter;
import com.effectivemobile.taskmanager.user.model.User;
import com.effectivemobile.taskmanager.user.repository.UserRepository;
import com.effectivemobile.taskmanager.util.NullChecker;
import com.effectivemobile.taskmanager.util.PageRequestCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public TaskFullDto addTask(Long authorId, NewTaskDto newTaskDto) {

        log.info("-- Сохранение задачи:{}", newTaskDto);

        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException("- Автор не найден"));

        User executor = userRepository.findById(newTaskDto.getExecutorId())
                .orElseThrow(() -> new NotFoundException("- Исполнитель не найден"));

        String priority = newTaskDto.getPriority();
        if (TaskParamsChecker.isWrongPriority(priority)) {
            throw new ConflictOnRequestException(String.format("- Неверное значение приоритета: %s", priority));
        }

        Task taskToSave = new Task();
        taskToSave.setTitle(newTaskDto.getTitle());
        taskToSave.setDescription(newTaskDto.getDescription());
        taskToSave.setAuthor(author);
        taskToSave.setExecutor(executor);
        taskToSave.setStatus(TaskStatus.WAITING.toString());
        taskToSave.setPriority(priority);

        TaskFullDto dtoToReturn = TaskMapper.modelToFullDto(taskRepository.save(taskToSave));

        log.info("-- Задача сохранена: {}", dtoToReturn);

        return dtoToReturn;
    }

    @Override
    @Transactional
    public TaskFullDto updateTask(Long userId, Long taskId, TaskUpdateDto updateDto) {

        log.info("-- Обновление задачи: id={}, {}", taskId, updateDto);

        Task taskToUpdate = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException(String.format("- Задача с id=%d не найдена", taskId)));

        Long authorId = taskToUpdate.getAuthor().getId();
        if (!userId.equals(authorId)) {
            throw new ConflictOnRequestException(String.format(
                    "- id пользователя %d не совпадает с id автора задачи %d, задача не обновлена", userId, authorId));
        }

        String properStatus = TaskStatus.WAITING.toString();
        if (!taskToUpdate.getStatus().equals(properStatus)) {
            throw new ConflictOnRequestException(String.format(
                    "- Для обновления доступны только задачи со статусом %s, задача не обновлена", properStatus));
        }

        User executor = null;
        if (updateDto.getExecutorId() != null) {
            executor = userRepository.findById(updateDto.getExecutorId())
                    .orElseThrow(() -> new NotFoundException("- Исполнитель не найден"));
        }

        String priority = updateDto.getPriority();
        if (TaskParamsChecker.isWrongPriority(priority)) {
            throw new ConflictOnRequestException(String.format(
                    "- Неверное значение приоритета: %s, задача не обновлена", priority));
        }

        NullChecker.setIfNotNull(taskToUpdate::setTitle, updateDto.getTitle());
        NullChecker.setIfNotNull(taskToUpdate::setDescription, updateDto.getDescription());
        NullChecker.setIfNotNull(taskToUpdate::setExecutor, executor);
        NullChecker.setIfNotNull(taskToUpdate::setPriority, priority);

        TaskFullDto dtoToReturn = TaskMapper.modelToFullDto(taskRepository.save(taskToUpdate));

        log.info("-- Задача обновлена: {}", dtoToReturn);

        return dtoToReturn;
    }

    @Override
    @Transactional
    public TaskFullDto setTaskStatusByExecutor(Long userId, Long taskId, String newStatus) {

        log.info("-- Изменение статуса задачи с id={} на {}", taskId, newStatus);

        Task taskToUpdate = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException(String.format("- Задача с id=%d не найдена", taskId)));

        Long executorId = taskToUpdate.getExecutor().getId();
        if (!userId.equals(executorId)) {
            throw new ConflictOnRequestException(String.format(
                    "- id пользователя %d не совпадает с id исполнителя задачи %d, статус задачи не обновлен",
                    userId, executorId));
        }

        if (TaskParamsChecker.isWrongStatus(newStatus)) {
            throw new ConflictOnRequestException(String.format(
                    "- Неверное значение статуса: %s, статус задачи не обновлен", newStatus));
        }

        taskToUpdate.setStatus(newStatus);

        TaskFullDto dtoToReturn = TaskMapper.modelToFullDto(taskRepository.save(taskToUpdate));

        log.info("-- Статус задачи c id={} обновлен на: {}", taskId, newStatus);

        return dtoToReturn;
    }

    @Override
    public TaskFullDto getById(Long taskId) {

        log.info("-- Возвращение задачи с id={}", taskId);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException(String.format("- Задача с id=%d не найдена", taskId)));

        TaskFullDto dtoToReturn = TaskMapper.modelToFullDto(task);

        log.info("-- Задача возвращена: {}", dtoToReturn);

        return dtoToReturn;
    }

    @Override
    public List<TaskShortDto> getByAuthorId(Long authorId,
                                            String textToSearch,
                                            String status,
                                            String priority,
                                            int from, int size,
                                            String sortBy) {

        log.info("-- Возвращение задач автора с id={}", authorId);

        Sort sort = TaskSorter.createSort(sortBy);
        PageRequest pageRequest = PageRequestCreator.create(from, size, sort);

        TaskParamsChecker.checkFilters(textToSearch, status, priority);

        List<TaskShortDto> listToReturn =
                TaskMapper.modelToShortDto(taskRepository.findByParams(
                        authorId, null, textToSearch, status, priority, pageRequest));

        log.info("-- Список задач автора с id={} возвращен, его размер={}", authorId, listToReturn.size());

        return listToReturn;
    }

    @Override
    public List<TaskShortDto> getByExecutorId(Long executorId,
                                              String textToSearch,
                                              String status,
                                              String priority,
                                              int from, int size,
                                              String sortBy) {

        log.info("-- Возвращение задач исполнителя с id={}", executorId);

        Sort sort = TaskSorter.createSort(sortBy);
        PageRequest pageRequest = PageRequestCreator.create(from, size, sort);

        TaskParamsChecker.checkFilters(textToSearch, status, priority);

        List<TaskShortDto> listToReturn =
                TaskMapper.modelToShortDto(taskRepository.findByParams(
                        null, executorId, textToSearch, status, priority, pageRequest));

        log.info("-- Список задач исполнителя с id={} возвращен, его размер={}", executorId, listToReturn.size());

        return listToReturn;
    }

    @Override
    @Transactional
    public void removeById(Long userId, Long taskId) {

        log.info("-- Удаление задачи с id={}", taskId);

        Task taskToUpdate = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException(String.format("- Задача с id=%d не найдена", taskId)));

        Long authorId = taskToUpdate.getAuthor().getId();
        if (!userId.equals(authorId)) {
            throw new ConflictOnRequestException(String.format(
                    "- id пользователя %d не совпадает с id автора задачи %d, задача не удалена", userId, authorId));
        }

        taskRepository.deleteById(taskId);

        log.info(String.format("-- Задача с id=%d удалена", taskId));
    }
}
