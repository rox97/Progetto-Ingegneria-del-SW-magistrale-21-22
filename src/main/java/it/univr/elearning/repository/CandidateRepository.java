package it.univr.elearning.repository;

import it.univr.elearning.model.Candidate;
import org.springframework.data.repository.CrudRepository;

public interface CandidateRepository extends CrudRepository<Candidate,Long> {
}
