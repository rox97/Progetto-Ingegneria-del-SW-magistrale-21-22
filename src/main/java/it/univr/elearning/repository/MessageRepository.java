package it.univr.elearning.repository;

import it.univr.elearning.model.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends CrudRepository<Message, Long> {
    Optional<Message> findByCourseName(String courseName);
    List<Message> findByCourse_id(Long id);

}
