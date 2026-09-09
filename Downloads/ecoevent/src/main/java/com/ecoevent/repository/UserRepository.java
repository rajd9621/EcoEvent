package com.ecoevent.repository;

import com.ecoevent.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByRole(String role);
    long countByRole(String role);
    List<User> findByEnabledTrueOrderByCreatedAtDesc();

    @Query("SELECT u FROM User u ORDER BY u.createdAt DESC")
    List<User> findAllOrderByCreatedAtDesc();
}
