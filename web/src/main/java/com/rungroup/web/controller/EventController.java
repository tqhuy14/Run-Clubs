package com.rungroup.web.controller;


import com.rungroup.web.dto.ClubDto;
import com.rungroup.web.dto.EventDto;
import com.rungroup.web.models.Event;
import com.rungroup.web.models.UserEntity;
import com.rungroup.web.security.SecurityUtil;
import com.rungroup.web.service.EventService;
import com.rungroup.web.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EventController {
    private EventService eventService;
    private UserService userService;

    @Autowired
    public EventController(EventService eventService, UserService userService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    @GetMapping("/events/{clubId}/new")
    public String createEventFrom(@PathVariable("clubId") Long clubId, Model model) {
        Event event = new Event();
        model.addAttribute("clubId", clubId);
        model.addAttribute("event", event);
        return "events-create";
    }

    @GetMapping("/events")
    public String listEvents(Model model) {
        List<EventDto> events = eventService.findAllEvents();
        model.addAttribute("events", events);
        return "events-list";
    }

    @GetMapping("/events/{eventID}")
    public String viewEvent(@PathVariable("eventID") Long eventID, Model model) {
        UserEntity user = new UserEntity();
        EventDto eventDto = eventService.findEventById(eventID);
        String email = SecurityUtil.getSessionUser();
        if(email != null) {
            user = userService.findByEmail(email);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("event", eventDto);
        return "event-detail";
    }

    @GetMapping("/events/{eventID}/edit")
    public String editEvent(@PathVariable long eventID, Model model) {
        EventDto eventDto = eventService.findEventById(eventID);
        model.addAttribute("event", eventDto);
        return "event-edit-form";
    }

    @PostMapping("/events/{eventID}/edit")
    public String updateEvent(@PathVariable("eventID") long eventID,@Valid @ModelAttribute("event") EventDto event,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("event", event);
            return "event-edit-form";
        }
        EventDto eventDto = eventService.findEventById(eventID);
        event.setId(eventID);
        event.setClub(eventDto.getClub());
        eventService.updateEvent(event);
        return "redirect:/events/" + eventID;
    }

    @PostMapping("/events/{clubId}")
    public String createEvent(@PathVariable("clubId") Long clubId,
                              @Valid @ModelAttribute("event") EventDto eventDto,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("event", eventDto);
            return "events-create";
        }
        eventService.createEvent(eventDto, clubId);
        return "redirect:/clubs/" + clubId;
    }

    @GetMapping("/events/{eventID}/delete")
    public String deleteEvent(@PathVariable long eventID) {
        EventDto eventDto = eventService.findEventById(eventID);
        long clubID = eventDto.getClub().getId();
        eventService.delete(eventID);
        return "redirect:/clubs/" + clubID;
    }

    @GetMapping("/events/search")
    public String searchEvents(@RequestParam("query") String name, Model model) {
        List<EventDto> eventsDtos = eventService.searchEventsByName(name);
        model.addAttribute("events", eventsDtos);
        return "events-list";
    }
}
