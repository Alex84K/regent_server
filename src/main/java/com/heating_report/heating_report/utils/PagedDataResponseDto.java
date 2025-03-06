package com.heating_report.heating_report.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagedDataResponseDto<T> {
    List<T> data;
    @JsonProperty("current_page")
    private long currentPage;
    @JsonProperty("total_elements")
    private long totalElements;
    @JsonProperty("total_pages")
    private long totalPages;
}
