package com.effectivemobile.taskmanager.task.repisotory;

import com.effectivemobile.taskmanager.task.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t " +
            "FROM Task AS t " +

            "WHERE ( " +
            "   (t.author.id = :authorId OR :authorId IS NULL) " +
            "   OR " +
            "   (t.executor.id = :executorId OR :executorId IS NULL) " +
            ") " +

            "AND ( " +
            "   (UPPER(t.title) LIKE UPPER(CONCAT('%', :textToSearch, '%')) OR :textToSearch IS NULL) " +
            "   OR " +
            "   (UPPER(t.description) LIKE UPPER(CONCAT('%', :textToSearch, '%')) OR :textToSearch IS NULL) " +
            ") " +

            "AND (status = :status OR :status IS NULL) " +
            "AND (priority = :priority OR :priority IS NULL)")
    Page<Task> findByParams(Long authorId,
                            Long executorId,
                            String textToSearch,
                            String status,
                            String priority,
                            Pageable pageable);
}
