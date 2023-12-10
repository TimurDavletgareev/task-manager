package com.effectivemobile.taskmanager.comment.service;

import com.effectivemobile.taskmanager.comment.dto.CommentFullDto;
import com.effectivemobile.taskmanager.comment.dto.NewCommentDto;

public interface CommentService {

    CommentFullDto addComment(Long taskId, Long authorId, NewCommentDto newCommentDto);

    void removeById(Long userId, Long commentId);
}
