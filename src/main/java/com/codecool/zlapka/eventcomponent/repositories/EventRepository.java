package com.codecool.zlapka.eventcomponent.repositories;

import com.codecool.zlapka.eventcomponent.model.Category;
import com.codecool.zlapka.eventcomponent.model.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

    @Override
    Optional<Event> findById(Long id);
    List<Event> findByName(String name);
    List<Event> findByCategory(Category category);
}
