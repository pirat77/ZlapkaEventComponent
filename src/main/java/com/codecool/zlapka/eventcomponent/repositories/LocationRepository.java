package com.codecool.zlapka.eventcomponent.repositories;

import com.codecool.zlapka.eventcomponent.model.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {

}
