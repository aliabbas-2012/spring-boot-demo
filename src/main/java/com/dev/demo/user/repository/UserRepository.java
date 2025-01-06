package com.dev.demo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.dev.demo.user.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
    public boolean existsByEmail(String email);
    public boolean existsByPhoneNumber(String phoneNumber);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email AND u.id <> :id")
    boolean existsByEmailAndIdNot(String email, Long id);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.phoneNumber = :phoneNumber AND u.id <> :id")
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);

}