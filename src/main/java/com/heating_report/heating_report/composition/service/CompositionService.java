package com.heating_report.heating_report.composition.service;

import com.heating_report.heating_report.composition.dto.CompositionDto;
import com.heating_report.heating_report.composition.dto.CompositionInWorkDto;
import com.heating_report.heating_report.composition.dto.NewCompositionDto;
import com.heating_report.heating_report.composition.dto.UpdateCompositionDto;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public interface CompositionService {
    CompositionDto addComposition(NewCompositionDto dto);
    CompositionDto getCompositionById(String id);
    CompositionDto deleteCompositionById(String id);
    CompositionDto updateComposition(UpdateCompositionDto dto);

    Iterable<CompositionDto> getAllCompositions();

    Iterable<CompositionDto> getCompositionByName(String name);
    Iterable<CompositionDto> getCompositionByTheme(String theme);
    Iterable<CompositionDto> getCompositionByBook(String book);
    Iterable<CompositionDto> getCompositionsByTime(Integer month, Integer year);
    Iterable<CompositionDto> getCompositionByWork();
    CompositionDto addCompositionInWork(CompositionInWorkDto dto);
    CompositionDto removeCompositionInWork(String id);

    Boolean addCompositionsFromCsv(MultipartFile file);
}
