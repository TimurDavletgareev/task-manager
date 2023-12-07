package com.effectivemobile.taskmanager.task.service.util;

import com.effectivemobile.taskmanager.error.exception.NotFoundException;
import org.springframework.data.domain.Sort;

public class TaskSorter {

    public static Sort createSort(String sortBy) {

        Sort sort = switch (sortBy) {
            case "title" -> Sort.by("title");
            case "executor" -> Sort.by("executor_id");
            case "status" -> Sort.by("status");
            case "priority" -> Sort.by("priority");
            default -> throw new NotFoundException("- Опция сортировки не найдена: " + sortBy);
        };

        return sort.and(Sort.by("id"));
    }
}
