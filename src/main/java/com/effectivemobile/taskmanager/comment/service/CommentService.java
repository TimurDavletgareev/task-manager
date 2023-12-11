package com.effectivemobile.taskmanager.comment.service;

import com.effectivemobile.taskmanager.comment.dto.CommentFullDto;
import com.effectivemobile.taskmanager.comment.dto.NewCommentDto;

import java.util.List;

public interface CommentService {

    CommentFullDto addComment(Long taskId, Long authorId, NewCommentDto newCommentDto);

    List<CommentFullDto> getByTaskId(Long taskId, int from, int size);

    void removeById(Long userId, Long commentId);
}
