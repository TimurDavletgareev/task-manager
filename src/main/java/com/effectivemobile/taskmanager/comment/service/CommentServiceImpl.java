package com.effectivemobile.taskmanager.comment.service;

import com.effectivemobile.taskmanager.comment.dto.CommentFullDto;
import com.effectivemobile.taskmanager.comment.dto.CommentMapper;
import com.effectivemobile.taskmanager.comment.dto.NewCommentDto;
import com.effectivemobile.taskmanager.comment.model.Comment;
import com.effectivemobile.taskmanager.comment.repository.CommentRepository;
import com.effectivemobile.taskmanager.error.exception.ConflictOnRequestException;
import com.effectivemobile.taskmanager.error.exception.NotFoundException;
import com.effectivemobile.taskmanager.task.model.Task;
import com.effectivemobile.taskmanager.task.repisotory.TaskRepository;
import com.effectivemobile.taskmanager.user.model.User;
import com.effectivemobile.taskmanager.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public CommentFullDto addComment(Long taskId, Long authorId, NewCommentDto newCommentDto) {

        log.info("-- Сохранение комментария пользователя с id={} к задаче с id={}",
                authorId, taskId);

        User author = userRepository.findById(authorId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("- Пользователь с id=%d не найден", authorId)));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException(String.format("- Задача с id=%d не найдена", taskId)));

        Comment comment = new Comment();

        comment.setAuthor(author);
        comment.setTask(task);
        comment.setCommentText(newCommentDto.getCommentText());

        CommentFullDto dtoToReturn = CommentMapper.modelToFullDto(commentRepository.save(comment));

        log.info("-- Комментарий пользователя с id={} к задаче с id={} сохранен", authorId, taskId);

        return dtoToReturn;
    }

    @Override
    @Transactional
    public void removeById(Long userId, Long commentId) {

        log.info("-- Удаление комментария с id={}", commentId);

        Comment commentToRemove = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(String.format("- Комментарий с id=%d не найден", commentId)));

        Long authorId = commentToRemove.getAuthor().getId();
        if (!userId.equals(authorId)) {
            throw new ConflictOnRequestException(String.format(
                    "- id пользователя %d не совпадает с id автора комментария %d, комментарий не удален",
                    userId, authorId));
        }

        taskRepository.deleteById(commentId);

        log.info(String.format("-- Комментарий с id=%d удален", commentId));
    }
}
