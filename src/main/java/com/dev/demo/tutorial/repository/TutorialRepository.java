package com.dev.demo.tutorial.repository;

import java.util.Optional;
import com.dev.demo.tutorial.model.Tutorial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface TutorialRepository extends JpaRepository<Tutorial, Long> , JpaSpecificationExecutor<Tutorial> {
    boolean existsByTitle(String title);

    Optional<Tutorial> findByTitle(String title);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Tutorial u WHERE u.title = :email AND u.id <> :id")
    boolean existsByTitleAndIdNot(String email, Long id);

    @Query("SELECT u FROM Tutorial u WHERE u.title LIKE %:search% OR u.description LIKE %:search%")
    Page<Tutorial> fetchAllTutorials(Pageable pageable, @Param("search") String search);

}
