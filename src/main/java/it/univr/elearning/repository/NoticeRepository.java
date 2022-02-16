package it.univr.elearning.repository;

import it.univr.elearning.model.Notice;
import org.springframework.data.repository.CrudRepository;

public interface NoticeRepository extends CrudRepository<Notice,Long> {
}
