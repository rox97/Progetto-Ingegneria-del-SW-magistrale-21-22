package it.univr.elearning.repository;

import it.univr.elearning.model.Professor;
import org.springframework.data.repository.CrudRepository;

public interface ProfessorRepository extends CrudRepository<Professor,Long>{
    boolean existsByUserName(String username);
    boolean existsByUserNameAndPassword(String username, String password);
    Professor findByUserName(String username);
    Professor findByUserNameAndPassword(String username, String password);



}
