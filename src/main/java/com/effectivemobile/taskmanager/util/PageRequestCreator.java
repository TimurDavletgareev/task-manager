package com.effectivemobile.taskmanager.util;

import com.effectivemobile.taskmanager.error.exception.IncorrectRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageRequestCreator {

    public static PageRequest create(int from, int size, Sort sort) {

        PageRequest pageRequest;

        if (size > 0 && from >= 0) {
            int page = from / size;
            pageRequest = PageRequest.of(page, size, sort);
        } else {
            throw new IncorrectRequestException("- Page должен быть > 0, 'from' должен быть >= 0, " +
                    "'sort' должен быть корректным полем класса");
        }

        return pageRequest;
    }
}
