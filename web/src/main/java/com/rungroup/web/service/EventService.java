package com.rungroup.web.service;


import com.rungroup.web.dto.EventDto;

import java.util.List;

public interface EventService {
    void createEvent(EventDto eventDto, Long clubId);

    List<EventDto> findEventByClubId(Long clubID);

    List<EventDto> findAllEvents();

    EventDto findEventById(Long eventID);

    void updateEvent(EventDto eventDto);

    void delete(long eventID);

    List<EventDto> searchEventsByName(String name);
}
