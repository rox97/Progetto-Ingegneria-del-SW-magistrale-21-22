package it.univr.elearning;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BookletRepository  extends CrudRepository<Booklet, Long> {

}