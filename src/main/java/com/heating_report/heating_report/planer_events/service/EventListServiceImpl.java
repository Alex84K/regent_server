package com.heating_report.heating_report.planer_events.service;

import com.heating_report.heating_report.composition.dto.CompositionDto;
import com.heating_report.heating_report.planer_events.dao.EventsListRepository;
import com.heating_report.heating_report.planer_events.dto.EventRespDto;
import com.heating_report.heating_report.planer_events.dto.NewEventDto;
import com.heating_report.heating_report.planer_events.dto.exceptions.EventNotFoundException;
import com.heating_report.heating_report.planer_events.model.EventPlan;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.Optional;

@CrossOrigin
@Service
@RequiredArgsConstructor
@Order(10)
public class EventListServiceImpl implements EventListService{
    @Autowired
    private final EventsListRepository eventListRepository;
    private final ModelMapper modelMapper;

    @Override
    public Iterable<EventRespDto> getAllEvents() {
        return eventListRepository.findAll().stream()// Фильтруем по имени (без учета регистра)
                .map(eventPlan -> modelMapper.map(eventPlan, EventRespDto.class))
                .toList();
    }

    @Override
    public Iterable<EventRespDto> getAllEventsByMonth(Integer month, Integer year) {
        // Проверка валидности входных параметров
        if (month == null || year == null || month < 1 || month > 12) {
            throw new IllegalArgumentException("Invalid month or year parameters");
        }

        return eventListRepository.findAll().stream()
                .filter(event -> {
                    // Предполагаем, что у EventPlan есть поле date типа LocalDate или аналогичного
                    LocalDateTime eventDate = event.getStart(); // или другой метод получения даты
                    return eventDate != null
                            && eventDate.getMonthValue() == month
                            && eventDate.getYear() == year;
                })
                .map(event -> modelMapper.map(event, EventRespDto.class))
                .toList();
    }

    @Override
    public EventRespDto addEvent(NewEventDto event) {
        EventPlan eventPlan = modelMapper.map(event, EventPlan.class);
        eventListRepository.save(eventPlan);
        return modelMapper.map(eventPlan, EventRespDto.class);
    }

    @Override
    public EventRespDto getEvent(String eventId) {
        EventPlan eventPlan = eventListRepository.findEventById(eventId).orElseThrow(EventNotFoundException::new);
        return modelMapper.map(eventPlan, EventRespDto.class);
    }

    @Override
    public EventRespDto updateEvent(EventRespDto event) {
        EventPlan eventPlan = eventListRepository.findEventById(event.getId()).orElseThrow(EventNotFoundException::new);
        eventPlan.setTitle(event.getTitle());
        eventPlan.setStart(event.getStart());
        eventPlan.setBook(event.getBook());
        eventPlan.setNumber(event.getNumber());
        eventPlan.setRegent(event.getRegent());
        eventListRepository.save(eventPlan);
        return modelMapper.map(eventPlan, EventRespDto.class);
    }

    @Override
    public EventRespDto deleteEvent(String eventId) {
        EventPlan eventPlan = eventListRepository.findEventById(eventId).orElseThrow(EventNotFoundException::new);
        eventListRepository.deleteById(eventId);
        return modelMapper.map(eventPlan, EventRespDto.class);
    }
}
