package com.effectivemobile.taskmanager.comment.dto;

import com.effectivemobile.taskmanager.comment.model.Comment;
import com.effectivemobile.taskmanager.task.dto.TaskMapper;
import com.effectivemobile.taskmanager.user.dto.UserMapper;

import java.util.ArrayList;
import java.util.List;

public class CommentMapper {

    public static CommentFullDto modelToFullDto(Comment comment) {

        return new CommentFullDto(

                comment.getId(),
                UserMapper.modelToShortDto(comment.getAuthor()),
                TaskMapper.modelToShortDto(comment.getTask()),
                comment.getCommentText()
        );
    }

    public static List<CommentFullDto> modelToFullDto(Iterable<Comment> comments) {

        List<CommentFullDto> listToReturn = new ArrayList<>();

        for (Comment comment : comments) {

            listToReturn.add(modelToFullDto(comment));
        }

        return listToReturn;
    }
}
