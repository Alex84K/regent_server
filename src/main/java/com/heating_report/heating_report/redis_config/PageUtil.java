package com.heating_report.heating_report.redis_config;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PageUtil {

    public static <T> Page<T> convertListToPage(List<T> list, Pageable pageable) {
        // Определяем общее количество элементов
        long totalElements = list.size();

        // Создаем объект PageImpl, который является реализацией интерфейса Page
        return new PageImpl<>(list, pageable, totalElements);
    }
}