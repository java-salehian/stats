package com.example.stats.controller;

import com.example.stats.dto.EventRequest;
import com.example.stats.service.EventService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/process")
    public void processEvents(@RequestBody EventRequest request) {
        eventService.processEvents(request.getImpressions(), request.getClicks());
    }
}
