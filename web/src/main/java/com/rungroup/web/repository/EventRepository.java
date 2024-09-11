package com.rungroup.web.repository;

import com.rungroup.web.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {


    @Query("SELECT e from Event e where e.name like concat('%', :query, '%') ")
    List<Event> searchEventByName(@Param("query") String query);
}
