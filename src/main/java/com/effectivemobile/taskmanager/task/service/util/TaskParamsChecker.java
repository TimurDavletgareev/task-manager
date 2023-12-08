package com.effectivemobile.taskmanager.task.service.util;

import com.effectivemobile.taskmanager.error.exception.ConflictOnRequestException;
import com.effectivemobile.taskmanager.task.model.TaskPriority;
import com.effectivemobile.taskmanager.task.model.TaskStatus;

import java.util.Arrays;

public class TaskParamsChecker {

    public static boolean isWrongPriority(String priority) {

        return priority != null
                && !Arrays.stream(TaskPriority.values()).map(Enum::toString).toList().contains(priority);
    }

    public static boolean isWrongStatus(String status) {

        return status != null
                && !Arrays.stream(TaskStatus.values()).map(Enum::toString).toList().contains(status);
    }

    public static void checkFilters(String textToSearch,
                                    String status,
                                    String priority) {

        if (textToSearch != null && (textToSearch.isEmpty() || textToSearch.isBlank() || textToSearch.length() < 2)) {
            throw new ConflictOnRequestException(String.format(
                    "- Фильтра поиска по тексту '%s' слишком короткий или пустой, список задач не возвращен",
                    textToSearch));
        }
        if (isWrongPriority(priority)) {
            throw new ConflictOnRequestException(String.format(
                    "- Неверное значение фильтра приоритета: %s, список задач не возвращен", priority));
        }
        if (isWrongStatus(status)) {
            throw new ConflictOnRequestException(String.format(
                    "- Неверное значение фильтра статуса: %s, список задач не возвращен", status));
        }
    }
}
