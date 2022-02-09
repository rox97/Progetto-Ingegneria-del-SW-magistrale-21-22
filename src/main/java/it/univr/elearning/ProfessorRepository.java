package it.univr.elearning;

import org.springframework.data.repository.CrudRepository;

public interface ProfessorRepository extends CrudRepository<Professor,Long>{
    boolean existsByUserName(String username);
    Professor findByUserName(String username);


}
