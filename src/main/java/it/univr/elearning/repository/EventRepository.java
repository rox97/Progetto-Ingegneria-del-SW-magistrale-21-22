package it.univr.elearning.repository;

import it.univr.elearning.model.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {

}
