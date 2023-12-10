package com.effectivemobile.taskmanager.comment.repository;

import com.effectivemobile.taskmanager.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
