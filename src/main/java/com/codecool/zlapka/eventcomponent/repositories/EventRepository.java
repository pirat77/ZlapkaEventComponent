package com.codecool.zlapka.eventcomponent.repositories;

import com.codecool.zlapka.eventcomponent.model.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

    @Query("select e from event e where e.idString = :idString")
    List<Event> findByStringId(@Param("idString") String idString);
    @Query("select e from event e where e.name = :name")
    List<Event> findByName(@Param("name") String name);
    @Query("select e from event e where e.category = :category")
    List<Event> findByCategory(@Param("category") String category);
    @Query("select e from event e where e.locationId = :locationId")
    List<Event> findEventsByLocationId(@Param("locationId") String locationId);
}
