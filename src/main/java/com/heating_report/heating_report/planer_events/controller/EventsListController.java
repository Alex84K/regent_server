package com.heating_report.heating_report.planer_events.controller;

import com.heating_report.heating_report.planer_events.dto.EventRespDto;
import com.heating_report.heating_report.planer_events.dto.NewEventDto;
import com.heating_report.heating_report.planer_events.service.EventListService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:5173")
//@CrossOrigin
@CrossOrigin(origins = "https://regent.shk.solutions") // Разрешить запросы с этого домена
@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventsListController  {
    @Autowired
    private final EventListService eventListService;

    @GetMapping("")
    public Iterable<EventRespDto> getAllEvents() {
        return eventListService.getAllEvents();
    }

    @GetMapping("/months/{month}/years/{year}")
    public Iterable<EventRespDto> getAllEventsByMonth(@PathVariable Integer month, @PathVariable Integer year) {
        return eventListService.getAllEventsByMonth(month, year);
    }

    @PostMapping("")
    public EventRespDto addEvent(@RequestBody NewEventDto event) {
        return eventListService.addEvent(event);
    }

    @GetMapping("/{eventId}")
    public EventRespDto getEvent(@PathVariable String eventId) {
        return eventListService.getEvent(eventId);
    }

    @PutMapping("")
    public EventRespDto updateEvent(@RequestBody EventRespDto event) {
        return eventListService.updateEvent(event);
    }

    @DeleteMapping("/{eventId}")
    public EventRespDto deleteEvent(@PathVariable String eventId) {
        return eventListService.deleteEvent(eventId);
    }
}
