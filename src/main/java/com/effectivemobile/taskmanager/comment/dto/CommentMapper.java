package com.effectivemobile.taskmanager.comment.dto;

import com.effectivemobile.taskmanager.comment.model.Comment;
import com.effectivemobile.taskmanager.task.dto.TaskMapper;
import com.effectivemobile.taskmanager.user.dto.UserMapper;

public class CommentMapper {

    public static CommentFullDto modelToFullDto(Comment comment) {

        return new CommentFullDto(

                comment.getId(),
                UserMapper.modelToShortDto(comment.getAuthor()),
                TaskMapper.modelToShortDto(comment.getTask()),
                comment.getCommentText()
        );
    }
}
