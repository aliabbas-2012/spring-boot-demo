package com.dev.demo.user.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.dev.demo.user.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email AND u.id <> :id")
    boolean existsByEmailAndIdNot(String email, Long id);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.phoneNumber = :phoneNumber AND u.id <> :id")
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.username = :username AND u.id <> :id")
    boolean existsByUsernameAndIdNot(String username, Long id);

    @Query("from User g left join fetch g.roles")
    List<User> fetchAllUsers();

}
