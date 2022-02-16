package it.univr.elearning.repository;

import it.univr.elearning.model.Poll;
import org.springframework.data.repository.CrudRepository;

public interface PollRepository extends CrudRepository<Poll, Long> {

}
