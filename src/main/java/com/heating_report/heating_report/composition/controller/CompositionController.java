package com.heating_report.heating_report.composition.controller;

import com.heating_report.heating_report.composition.dto.*;
import com.heating_report.heating_report.composition.service.CompositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

//@CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin
@RestController
@RequestMapping("/api/v1/compositions")
@RequiredArgsConstructor
public class CompositionController {
    @Autowired
    private final CompositionService compositionService;

    @PostMapping("")
    public CompositionDto addComposition(@RequestBody NewCompositionDto dto) {
        return compositionService.addComposition(dto);
    }

    @GetMapping("/{id}")
    public CompositionDto getCompositionById(@PathVariable String id) {
        return compositionService.getCompositionById(id);
    }

    @DeleteMapping("/{id}")
    public CompositionDto deleteCompositionById(@PathVariable String id) {
        return compositionService.deleteCompositionById(id);
    }

    @PutMapping("")
    public CompositionDto updateComposition(@RequestBody UpdateCompositionDto dto) {
        return compositionService.updateComposition(dto);
    }

    @GetMapping("")
    public Iterable<CompositionDto> getAllCompositions(){
        return compositionService.getAllCompositions();
    }

    @GetMapping("/names/{name}")
    public Iterable<CompositionDto> getCompositionByName(@PathVariable String name) {
        return compositionService.getCompositionByName(name);
    }

    @GetMapping("/themes/{theme}")
    public Iterable<CompositionDto> getCompositionByTheme(@PathVariable String theme) {
        return compositionService.getCompositionByTheme(theme);
    }

    @GetMapping("/books/{book}")
    public Iterable<CompositionDto> getCompositionByBook(@PathVariable String book) {
        return compositionService.getCompositionByBook(book);
    }

    @GetMapping("/clooks")
    public Iterable<CompositionDto> getCompositionByTime(CompositionsDateDto date) {
        return compositionService.getCompositionByTime(date.getBefore(), date.getAfter());
    }

    @GetMapping("/works")
    public Iterable<CompositionDto> getCompositionByWork() {
        return compositionService.getCompositionByWork();
    }

    @PostMapping("/works")
    public CompositionDto getCompositionInWork(@RequestBody CompositionInWorkDto dto){
        return compositionService.addCompositionInWork(dto);
    }

    @DeleteMapping("/works/{id}")
    public CompositionDto removeCompositionInWork(@PathVariable String id){
        return compositionService.removeCompositionInWork(id);
    }
}
