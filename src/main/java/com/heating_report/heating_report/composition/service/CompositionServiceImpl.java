package com.heating_report.heating_report.composition.service;

import com.heating_report.heating_report.composition.dao.CompositionRepository;
import com.heating_report.heating_report.composition.dto.CompositionDto;
import com.heating_report.heating_report.composition.dto.CompositionInWorkDto;
import com.heating_report.heating_report.composition.dto.NewCompositionDto;
import com.heating_report.heating_report.composition.dto.UpdateCompositionDto;
import com.heating_report.heating_report.composition.dto.exceptions.CompositionNotFoundExceptions;
import com.heating_report.heating_report.composition.model.Composition;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Optional;

@CrossOrigin
@Service
@RequiredArgsConstructor
@Order(10)
public class CompositionServiceImpl implements CompositionService {

    @Autowired
    private final CompositionRepository compositionRepository;
    private final ModelMapper modelMapper;

    @Override
    public CompositionDto addComposition(NewCompositionDto dto) {
        // Проверяем, существует ли такая композиция
        Optional<Composition> existingComposition = compositionRepository.findByBookAndNumber(dto.getBook(), dto.getNumber());
        if (existingComposition.isPresent()) {
            throw new IllegalArgumentException("Composition with book '" + dto.getBook() +
                    "' and number '" + dto.getNumber() + "' already exists.");
        }
        // Если не существует — создаём новую
        Composition composition = modelMapper.map(dto, Composition.class);
        compositionRepository.save(composition);

        return modelMapper.map(composition, CompositionDto.class);
    }


    @Override
    public CompositionDto getCompositionById(String id) {
        Composition composition = compositionRepository.findById(id).orElseThrow(CompositionNotFoundExceptions::new);
        return modelMapper.map(composition, CompositionDto.class);
    }

    @Override
    public CompositionDto deleteCompositionById(String id) {
        Composition composition = compositionRepository.findById(id).orElseThrow(CompositionNotFoundExceptions::new);
        compositionRepository.delete(composition);
        return modelMapper.map(composition, CompositionDto.class);
    }

    @Override
    public CompositionDto updateComposition(UpdateCompositionDto dto) {
        try {
            Composition composition = compositionRepository.findById(dto.getId()).orElseThrow(CompositionNotFoundExceptions::new);
            if (dto.getName() != null) {
                composition.setName(dto.getName());
            }
            if (dto.getBook() != null) {
                composition.setBook(dto.getBook());
            }
            if (dto.getNumber() != null) {
                composition.setNumber(dto.getNumber());
            }
            if (dto.getTheme() != null) {
                composition.setTheme(dto.getTheme());
            }
            compositionRepository.save(composition);
            return modelMapper.map(composition, CompositionDto.class);
        } catch (CompositionNotFoundExceptions e) {
            // Если композиция не найдена, выбрасываем исключение с HTTP 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Composition not found", e);
        } catch (Exception e) {
            // Ловим все остальные исключения и возвращаем HTTP 400 с сообщением об ошибке
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request data", e);
        }
    }

    @Override
    public Iterable<CompositionDto> getAllCompositions() {
        return compositionRepository.findAll().stream()// Фильтруем по имени (без учета регистра)
                .map(composition -> modelMapper.map(composition, CompositionDto.class))
                .toList();
    }

    @Override
    public Iterable<CompositionDto> getCompositionByName(String name) {
        return compositionRepository.findAll().stream() // Получаем поток всех композиций
                .filter(composition -> composition.getName().equalsIgnoreCase(name)) // Фильтруем по имени (без учета регистра)
                .map(composition -> modelMapper.map(composition, CompositionDto.class))
                .toList();
    }

    @Override
    public Iterable<CompositionDto> getCompositionByTheme(String theme) {
        return compositionRepository.findAll().stream() // Получаем поток всех композиций
                .filter(composition -> composition.getTheme().equalsIgnoreCase(theme)) // Фильтруем по имени (без учета регистра)
                .map(composition -> modelMapper.map(composition, CompositionDto.class))
                .toList();
    }

    @Override
    public Iterable<CompositionDto> getCompositionByBook(String book) {
        return compositionRepository.findAll().stream() // Получаем поток всех композиций
                .filter(composition -> composition.getBook().equalsIgnoreCase(book)) // Фильтруем по имени (без учета регистра)
                .map(composition -> modelMapper.map(composition, CompositionDto.class))
                .toList();
    }

    @Override
    public Iterable<CompositionDto> getCompositionsByTime(Integer month, Integer year) {
        // Вычисляем начало и конец месяца
        LocalDate startOfMonth = LocalDate.of(year, month, 1); // Начало месяца
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth()); // Конец месяца

        return compositionRepository.findAll().stream() // Получаем поток всех композиций
                .filter(composition -> {
                    LocalDate lastPerformedDate = composition.getLastData(); // Предположим, что это поле существует
                    return lastPerformedDate != null // Проверяем, что дата не null
                            && !lastPerformedDate.isBefore(startOfMonth) // Дата не раньше начала месяца
                            && !lastPerformedDate.isAfter(endOfMonth); // Дата не позже конца месяца
                })
                .map(composition -> modelMapper.map(composition, CompositionDto.class)) // Преобразуем Composition в CompositionDto
                .toList(); // Преобразуем поток в список
    }


    @Override
    public Iterable<CompositionDto> getCompositionByWork() {
        return compositionRepository.findAll().stream() // Получаем поток всех композиций
                .filter(composition -> Boolean.TRUE.equals(composition.getInWork())) // Проверка на null и фильтрация по значению true
                .map(composition -> modelMapper.map(composition, CompositionDto.class))
                .toList();
    }

    @Override
    public CompositionDto addCompositionInWork(CompositionInWorkDto dto) {
        try {
            Composition composition = compositionRepository.findById(dto.getId()).orElseThrow(CompositionNotFoundExceptions::new);
            composition.setInWork(true);
            composition.setLastData(LocalDate.now());
            composition.setLastDirigent(dto.getLastDirigent());
            compositionRepository.save(composition);
            return modelMapper.map(composition, CompositionDto.class);
        } catch (CompositionNotFoundExceptions e) {
            // Если композиция не найдена, выбрасываем исключение с HTTP 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Composition not found", e);
        } catch (Exception e) {
            // Ловим все остальные исключения и возвращаем HTTP 400 с сообщением об ошибке
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request data", e);
        }
    }

    @Override
    public CompositionDto removeCompositionInWork(String id) {
        try {
            Composition composition = compositionRepository.findById(id).orElseThrow(CompositionNotFoundExceptions::new);
            composition.setInWork(false);
            composition.setLastData(LocalDate.now());
            compositionRepository.save(composition);
            return modelMapper.map(composition, CompositionDto.class);
        } catch (CompositionNotFoundExceptions e) {
            // Если композиция не найдена, выбрасываем исключение с HTTP 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Composition not found", e);
        } catch (Exception e) {
            // Ловим все остальные исключения и возвращаем HTTP 400 с сообщением об ошибке
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request data", e);
        }
    }

    @Override
    public Boolean addCompositionsFromCsv(MultipartFile file) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); // Разделитель - запятая

                if (values.length < 4) continue; // Пропускаем некорректные строки

                String name = values[0].replaceAll("^\"|\"$", "").trim();
                String book = values[1].replaceAll("^\"|\"$", "").trim();
                String number = values[2].replaceAll("^\"|\"$", "").trim();
                String theme = values[3].replaceAll("^\"|\"$", "").trim();

                // Проверяем, существует ли композиция
                Optional<Composition> existingComposition = compositionRepository.findByBookAndNumber(book, number);
                if (existingComposition.isPresent()) {
                    continue; // Пропускаем дубликаты
                }

                // Создаем новую сущность
                Composition composition = new Composition();
                composition.setName(name);
                composition.setBook(book);
                composition.setNumber(number);
                composition.setTheme(theme);
                composition.setLastData(LocalDate.now());
                composition.setInWork(false);
                composition.setLastDirigent(""); // По умолчанию пустое значение

                // Сохраняем в базу
                compositionRepository.save(composition);
            }
            return true;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error processing CSV file", e);
        }
    }

}
